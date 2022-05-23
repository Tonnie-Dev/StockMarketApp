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
            /*we use async to run the coroutines independent of each other instead of
            running the Coroutines sequentially*/

            //use async coroutine builder to return a result of Deferred<T>
            val companyInfoResult = async { repository.getCompanyInfo(symbol) }
            val intradayInfoResult = async { repository.getIntradayInfo(symbol) }

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

        }
    }
}