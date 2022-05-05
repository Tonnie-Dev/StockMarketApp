package com.uxstate.stockmarketapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface StockAPI {


    @GET()
    suspend fun getListings( @Query("apikey") apiKey:String)

    companion object {

        const val API_KEY = "EWFVFPOUVDKBT16Q"
        const val BASE_URL = "https:/alphavantage.co"
    }

}