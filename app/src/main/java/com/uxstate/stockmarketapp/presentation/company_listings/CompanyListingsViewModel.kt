package com.uxstate.stockmarketapp.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uxstate.stockmarketapp.domain.repository.StockRepository
import com.uxstate.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyListingsViewModel @Inject constructor(val repository: StockRepository) : ViewModel() {

    var state by mutableStateOf(CompanyListingsState())
        private set
    private var job:Job? = null
    private val _uiEvent = Channel<CompanyListingsEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: CompanyListingsEvent) {



        when (event) {


            is CompanyListingsEvent.Refresh -> {

                //if we refresh we force remote fetching

                getCompanyListings(fetchFromRemote = true)
            }
            //called every time there a character is changed
            is CompanyListingsEvent.OnSearchQueryChange -> {

                //update search query
                state = state.copy(searchQuery = event.query)

                //stop currently running job
                 job?.cancel()

                //start a new job

                job = viewModelScope.launch {

                    //delay job by half a second to decrease API Calls

                    delay(500)


                    //called only after a delay of 500 ms
                    getCompanyListings()
                }


            }
        }
    }

    //Called on Swipe to Refresh data
    private fun getCompanyListings(
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
