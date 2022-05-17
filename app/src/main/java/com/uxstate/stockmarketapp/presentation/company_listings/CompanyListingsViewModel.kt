package com.uxstate.stockmarketapp.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxstate.stockmarketapp.domain.repository.StockRepository
import com.uxstate.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyListingsViewModel @Inject constructor(val repository: StockRepository) : ViewModel() {

    var state by mutableStateOf(CompanyListingsState())
        private set

    private val _uiEvent = Channel<CompanyListingsEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: CompanyListingsEvent) {


        when (event) {


            is CompanyListingsEvent.Refresh -> {

                getCompanyListings(fetchFromRemote = true)
            }
            is CompanyListingsEvent.OnSearchQueryChange -> {}
        }
    }

    //Called on Swipe to Refresh data
    fun getCompanyListings(
        query: String = state.searchQuery,
        fetchFromRemote: Boolean = false
    ) {

        //launch Coroutine

        viewModelScope.launch {


            repository.getCompanyListings(
                query = query,
                fetchFromRemote = fetchFromRemote
            )
                    .collect {

                            result ->
                        when (result) {

                            is Resource.Loading -> {

                                state = state.copy(isLoading = result.isLoading)
                            }
                            is Resource.Success -> {

                              result.data?.let {
                                  state = state.copy(companies = it)
                              }
                            }
                            is Resource.Error -> Unit
                        }
                    }
        }


    }
}
