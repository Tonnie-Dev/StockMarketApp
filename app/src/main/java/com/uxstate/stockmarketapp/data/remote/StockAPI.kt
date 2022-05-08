package com.uxstate.stockmarketapp.data.remote


import androidx.compose.runtime.Composable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockAPI {

@GET("query?function=LISTING_STATUS")
suspend fun getListings(@Query("apikey") apikey:String):ResponseBody
companion object {

    const val API_KEY = "EWFVFPOUVDKBT16Q"
    const val BASE_URL = "https://www.alphavantage.co"
}
}