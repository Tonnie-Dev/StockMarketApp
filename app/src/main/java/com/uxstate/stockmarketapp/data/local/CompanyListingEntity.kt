package com.uxstate.stockmarketapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class CompanyListingEntity(
    val symbol: String,
    val name: String,
    val exchange: String,
    @PrimaryKey
    val id:Int? = null
)
