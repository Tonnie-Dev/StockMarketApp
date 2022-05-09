package com.uxstate.stockmarketapp.data.mapper

import com.uxstate.stockmarketapp.data.local.CompanyListingEntity
import com.uxstate.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing():CompanyListing{


    return CompanyListing(symbol = this.symbol, name = this.name, exchange = this.exchange)


}