package com.uxstate.stockmarketapp.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.uxstate.stockmarketapp.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


@HiltViewModel
class CompanyListingsViewModel @Inject constructor(repository: StockRepository) : ViewModel() {

    var state by mutableStateOf(CompanyListingsState())
        private set

    private val _uiEvent = Channel<CompanyListingsEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()



    fun onEvent(event: CompanyListingsEvent){


        when (event){


            is CompanyListingsEvent.Refresh -> {}
            is CompanyListingsEvent.OnSearchQueryChange -> {}
        }
    }
}
