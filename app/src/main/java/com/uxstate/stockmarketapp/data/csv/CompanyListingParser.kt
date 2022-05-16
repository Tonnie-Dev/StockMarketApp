package com.uxstate.stockmarketapp.data.csv

import com.uxstate.stockmarketapp.domain.model.CompanyListing
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton


/*We need an inject annotation in front of the constructor
* so that dagger hilt knows how to create a CompanyListing
* object an provide it for other dependencies
*
* We use singleton annotation to force one single instance
* of this class for the entire app*/
@Singleton
class CompanyListingParser @Inject constructor():CVSParser {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        TODO("Not yet implemented")
    }
}