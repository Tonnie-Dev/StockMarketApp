package com.uxstate.stockmarketapp.di

import com.uxstate.stockmarketapp.data.csv.CSVParser
import com.uxstate.stockmarketapp.data.csv.CompanyListingParser
import com.uxstate.stockmarketapp.data.csv.IntradayInfoParser
import com.uxstate.stockmarketapp.data.repository.StockRepositoryImpl
import com.uxstate.stockmarketapp.domain.model.CompanyListing
import com.uxstate.stockmarketapp.domain.model.IntradayInfo
import com.uxstate.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
//We create a separate module to bind the interface to its implementation
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds //used for 1-to-1 interface-implementation mapping
    @Singleton
    abstract fun bindCompanyListingParser(

        //takes the implementation as single parameter
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds //used for 1-to-1 interface-implementation mapping
    @Singleton
    abstract fun bindStockRepository(

            //takes the implementation as parameter
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository

    @Binds
    @Singleton
    abstract fun bindsIntradayInfoParser(intradayInfoParser: IntradayInfoParser):CSVParser<IntradayInfo>
}