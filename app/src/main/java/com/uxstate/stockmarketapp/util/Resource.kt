package com.uxstate.stockmarketapp.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    //Success case - we have a normal generic class
    class Success<T>(data:T?):Resource<T>(data)


    //Error case - we have a normal generic class
    class  Error<T>( message: String, data: T? = null):Resource<T>(data, message)

    /*We attach a nullable data to the Error case as we can
    * still return some data from the database cache*/


    class Loading<T>(val isLoading:Boolean = false):Resource<T>(null)
}
