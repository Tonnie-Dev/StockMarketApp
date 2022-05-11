package com.uxstate.stockmarketapp.domain.repository

import com.uxstate.stockmarketapp.domain.model.CompanyListing
import com.uxstate.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow


/**/

interface StockRepository {
/* Because of the Cache we use a Flow wrapped in a Success-Failure
Resource class to emit multiple results e.g. Loading, API and local Cache*/
    suspend fun getCompanyListings(fetchFromRemote:Boolean, query:String): Flow<Resource<List<CompanyListing>>>
}