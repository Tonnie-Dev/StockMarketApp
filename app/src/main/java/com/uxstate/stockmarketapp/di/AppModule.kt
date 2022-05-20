package com.uxstate.stockmarketapp.di

import android.app.Application
import androidx.room.Room
import com.uxstate.stockmarketapp.data.csv.CVSParser
import com.uxstate.stockmarketapp.data.local.CompanyListingDatabase
import com.uxstate.stockmarketapp.data.remote.StockAPI
import com.uxstate.stockmarketapp.data.repository.StockRepositoryImpl
import com.uxstate.stockmarketapp.domain.model.CompanyListing
import com.uxstate.stockmarketapp.domain.repository.StockRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module // Tells hilt this is a module
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

    //provide Database with an application instance
    fun provideStockDatabase(app: Application): CompanyListingDatabase {

        return Room.databaseBuilder(
                app,
                CompanyListingDatabase::class.java,
                "stockdb"
        )
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