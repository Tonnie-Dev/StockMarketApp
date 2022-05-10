package com.uxstate.stockmarketapp.domain.repository

import com.uxstate.stockmarketapp.domain.model.CompanyListing

interface StockRepository {

    suspend fun getCompanyListings(): List<CompanyListing>
}