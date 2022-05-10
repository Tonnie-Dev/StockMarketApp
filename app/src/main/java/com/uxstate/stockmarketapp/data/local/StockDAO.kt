package com.uxstate.stockmarketapp.data.local

import androidx.room.*

/*defines how we want to interact with the local db -
insert, delete or queries*/

@Dao
interface StockDAO {

    //insert listings when we get them from the csv file

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(companyListingEntities: List<CompanyListingEntity>)

    //clear listings when there is an update
    @Query("DELETE FROM companylistingentity")
    suspend fun clearListings()


    //query for searching on the db

   @Query("""
       
       SELECT * FROM companylistingentity
       WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
       UPPER(:query) == symbol
   """)
    suspend fun searchCompanyListings(query:String):List<CompanyListingEntity>
}