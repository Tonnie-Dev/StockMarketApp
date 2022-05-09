package com.uxstate.stockmarketapp.data.mapper

import com.uxstate.stockmarketapp.data.local.CompanyListingEntity
import com.uxstate.stockmarketapp.domain.model.CompanyListing


//map entity to model
fun CompanyListingEntity.toCompanyListing(): CompanyListing {


    return CompanyListing(
        symbol = this.symbol,
        name = this.name,
        exchange = this.exchange
    )


}


//map from model to entity
fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {

    return CompanyListingEntity(
        symbol = this.symbol,
        name = this.name,
        exchange = this.exchange,
        id = null
    )
}