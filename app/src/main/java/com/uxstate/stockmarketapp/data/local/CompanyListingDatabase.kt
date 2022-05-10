package com.uxstate.stockmarketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CompanyListingEntity::class], version = 1)
abstract class CompanyListingDatabase: RoomDatabase() {

    abstract val dao:StockDAO
}