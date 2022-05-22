package com.uxstate.stockmarketapp.data.repository

import com.uxstate.stockmarketapp.data.csv.CSVParser
import com.uxstate.stockmarketapp.data.local.CompanyListingDatabase
import com.uxstate.stockmarketapp.data.mapper.toCompanyListing
import com.uxstate.stockmarketapp.data.mapper.toCompanyListingEntity
import com.uxstate.stockmarketapp.data.remote.StockAPI
import com.uxstate.stockmarketapp.domain.model.CompanyInfo
import com.uxstate.stockmarketapp.domain.model.CompanyListing
import com.uxstate.stockmarketapp.domain.model.IntradayInfo
import com.uxstate.stockmarketapp.domain.repository.StockRepository
import com.uxstate.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


//force single instance of our repository impl for the entire app
@Singleton
class StockRepositoryImpl
@Inject constructor(
    private val api: StockAPI,
    db: CompanyListingDatabase,
        //we depend on abstraction
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<CompanyListing>,

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


            /* check if we actually need the API Call - if we don't
             swipe to refresh the we have up to date listings and we
            don't need an API call*/

            //check if the db is empty, query.isBlank matches db query & returns every db entry
            val isDbEmpty = localListing.isEmpty() && query.isBlank()

            //check if we should load from Cache

            //!isDbEmpty means the database is already populated
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote


            /* the very first time we will load from the API,
            * the subsequent time we will load if we swipe to refresh*/

            if (shouldJustLoadFromCache) {

                //had already requested the local cache
                emit(Resource.Loading(isLoading = false))

                //therefore we return control to flow
                return@flow
            }


            /*Make API Call using try-catch as things could go wrong
            * we wrap this in remoteListings*/

            val remoteListings = try {
                val remoteListing = api.getListings()

                //returns a list of listings from the input stream
                companyListingParser.parse(remoteListing.byteStream())

                //parsing issue, no internet connection
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not load data"))

                //return null indicating no data
                null
                //invalid http response, incomplete response
            } catch (e: HttpException) {

                e.printStackTrace()
                emit(Resource.Error("Could not load data"))

                //return null indicating no data
                null
            }

            /*After making an API Call we need to insert the listings
            * into the cache*/

            //null check for listings

            remoteListings?.let { listings ->

                //delete database
                dao.clearListings()

                //insert updated listings
                dao.insertCompanyListings(remoteListings.map { it.toCompanyListingEntity() })

                //One Single Source of truth - we ensure data comes for db


                emit(
                        Resource.Success(
                                //if query is empty all listings match and are all returned
                                data = dao.searchCompanyListings("")
                                        .map { it.toCompanyListing() })
                )

                //discontinue loading
                emit(Resource.Loading(false))


            }

        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {



        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load intraday info")

        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load intraday info")

        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        TODO("Not yet implemented")
    }
}


