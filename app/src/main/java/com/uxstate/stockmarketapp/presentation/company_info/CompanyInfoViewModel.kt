package com.uxstate.stockmarketapp.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxstate.stockmarketapp.domain.repository.StockRepository
import com.uxstate.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {

    //initialize Company Info State
    var state by mutableStateOf(CompanyInfoState())


    init {
        viewModelScope.launch {

            //get symbol parameter from SavedStateHandle
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            //set the state to loading

            state = state.copy(isLoading = true)

            //make api calls - 2 network calls
            /*we use async to run the coroutines concurrently independent
            of each other instead of  running the Coroutines sequentially
            return a result at the same time using .await call*/

            //use async coroutine builder to return a result of Deferred<T>
            val companyInfoResult = async { repository.getCompanyInfo(symbol) }
            val intradayInfoResult = async { repository.getIntradayInfo(symbol) }

            //Map state to repository.getCompanyInfo result
            when (val result = companyInfoResult.await()) {

                is Resource.Success -> {

                    state = state.copy(companyInfo = result.data, isLoading = false, error = null)
                }
                is Resource.Error -> {

                    state =
                        state.copy(error = result.message, isLoading = false, companyInfo = null)
                }
                else -> Unit
            }

            //Map state to repository.getIntradayInfo result

            when (val result = intradayInfoResult.await()) {

                is Resource.Success -> {

                    state = state.copy(
                            intradayInfo = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                    )
                }
                is Resource.Error -> {

                    state = state.copy(error = result.message, isLoading = false)
                }
                else -> Unit
            }


        }
    }
}