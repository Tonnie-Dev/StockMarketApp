package com.uxstate.stockmarketapp.data.remote


import com.uxstate.stockmarketapp.data.remote.dto.CompanyInfoDTO
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockAPI {
/*A one-shot stream from the origin server to
 the client application with the raw bytes of
 the response body*/


    @GET("query?function=LISTING_STATUS")
//default apiKey to API_KEY, return CSV bytes
    suspend fun getListings(@Query("apikey") apiKey: String = API_KEY): ResponseBody



    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(

        @Query("symbol") symbol: String, @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody // we return a response body to get a byte stream



    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): CompanyInfoDTO

    companion object {

        const val API_KEY = "EWFVFPOUVDKBT16Q"
        const val BASE_URL = "https://www.alphavantage.co"
    }
}