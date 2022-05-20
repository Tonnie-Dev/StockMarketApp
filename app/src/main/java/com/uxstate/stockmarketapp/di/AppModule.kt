package com.uxstate.stockmarketapp.di

import android.content.Context
import androidx.room.Room
import com.uxstate.stockmarketapp.data.csv.CVSParser
import com.uxstate.stockmarketapp.data.local.CompanyListingDatabase
import com.uxstate.stockmarketapp.data.remote.StockAPI
import com.uxstate.stockmarketapp.data.repository.StockRepositoryImpl
import com.uxstate.stockmarketapp.domain.model.CompanyListing
import com.uxstate.stockmarketapp.domain.repository.StockRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    //provide API
    fun provideStockAPI(): StockAPI {

        return Retrofit.Builder()
                .baseUrl(StockAPI.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create()
    }

    //provide Database
    fun provideDatabase(@ApplicationContext context: Context): CompanyListingDatabase {


        return Room.databaseBuilder(context, CompanyListingDatabase::class.java, "Company Listing")
                .build()
    }



    //provider Repository
    fun provideStockRepository(
        api: StockAPI,
        db: CompanyListingDatabase,
        parser: CVSParser<CompanyListing>
    ): StockRepository {

        return StockRepositoryImpl(api = api, db = db, companyListingParser = parser)
    }


}