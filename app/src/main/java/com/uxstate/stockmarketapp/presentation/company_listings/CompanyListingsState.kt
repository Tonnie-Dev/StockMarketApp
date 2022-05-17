package com.uxstate.stockmarketapp.presentation.company_listings

import com.uxstate.stockmarketapp.domain.model.CompanyListing

data class CompanyListingsState(
    val companies: List<CompanyListing>,
    val isLoading: Boolean,
    val isRefreshing: Boolean,
    val searchQuery: String = ""
)
