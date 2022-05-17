package com.uxstate.stockmarketapp.presentation.company_listings

import androidx.lifecycle.ViewModel
import com.uxstate.stockmarketapp.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CompanyListingsViewModel @Inject constructor(repository: StockRepository) : ViewModel() {
}
