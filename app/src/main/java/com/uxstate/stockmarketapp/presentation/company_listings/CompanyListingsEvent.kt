package com.uxstate.stockmarketapp.presentation.company_listings

sealed class CompanyListingsEvent {

    object Refresh: CompanyListingsEvent()
    data class OnSearchQueryChange(val query:String): CompanyListingsEvent()
    object ClearText: CompanyListingsEvent()
}