package com.uxstate.stockmarketapp.data.repository

import com.uxstate.stockmarketapp.data.local.CompanyListingDatabase
import com.uxstate.stockmarketapp.data.mapper.toCompanyListing
import com.uxstate.stockmarketapp.data.remote.StockAPI
import com.uxstate.stockmarketapp.domain.model.CompanyListing
import com.uxstate.stockmarketapp.domain.repository.StockRepository
import com.uxstate.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


//force single instance of our repository for the entire app
@Singleton
class StockRepositoryImpl
@Inject constructor(
    val api: StockAPI,
    val db: CompanyListingDatabase
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {

        //return a flow builder
        return flow {

//inside a flow builder we have access to emit

            //ABOUT TO START DATABASE QUERY

            //emit a loading resource
            emit(Resource.Loading(isLoading = true))


            //QUERYING DB
            //obtain the list of companies from db
            val localListing = dao.searchCompanyListings(query = query)

            /*at this point we have successfully loaded the cache, if
            * the database is empty then localListing will be an
            * empty list*/

            // emit a success resource which is ready to be shown in the UI
            emit(Resource.Success(data = localListing.map { it.toCompanyListing() }))



            /* check if we actually need at the API Call - if we don't
            * swip to refresh the we have up to date listings and we
            * don't need an API call*/

            //check if the db is empty
            val isDbEmpty = localListing.isEmpty() && query.isBlank()

            //check if we should load from Cache

            //!isDbEmpty means the database is already populated
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote


           /* the very first time we will load from the API,
           * the subsequent time we will load if we swipe to refresh*/

            if(shouldJustLoadFromCache){

                //had already requested the local cache
                emit(Resource.Loading(isLoading =false))

                //therefore we return control to flow
                return@flow
            }


            //







        }
    }
}