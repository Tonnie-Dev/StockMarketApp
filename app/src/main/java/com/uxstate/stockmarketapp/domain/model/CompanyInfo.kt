package com.uxstate.stockmarketapp.domain.model

/*Add this class so that we don't have Moshi stuff in the Domain Layer*/
data class CompanyInfo(
    val name: String,
    val symbol: String,
    val description: String,
    val country: String,
    val industry: String
)