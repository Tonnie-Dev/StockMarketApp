package com.uxstate.stockmarketapp.di

import android.content.Context
import androidx.room.Room
import com.uxstate.stockmarketapp.data.local.CompanyListingDatabase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    //provide Database

    fun provideDatabase(@ApplicationContext context: Context): CompanyListingDatabase {


        return Room.databaseBuilder(context, CompanyListingDatabase::class.java, "Company Listing")
                .build()
    }
}