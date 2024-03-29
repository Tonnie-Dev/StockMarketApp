package com.uxstate.stockmarketapp.data.mapper

import com.uxstate.stockmarketapp.data.remote.dto.CompanyInfoDTO
import com.uxstate.stockmarketapp.data.remote.dto.IntradayInfoDTO
import com.uxstate.stockmarketapp.domain.model.CompanyInfo
import com.uxstate.stockmarketapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import java.util.*

//DTO to domain
fun IntradayInfoDTO.toIntradayInfo(): IntradayInfo {

    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(this.timeStamp, formatter)
    return IntradayInfo(date = localDateTime, close = this.close)
}


//DTO to domain
fun CompanyInfoDTO.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
            name = this.name ?: "",
            symbol = this.symbol ?: "",
            description = this.description ?: "",
            country = this.country ?: "",
            industry = this.industry ?: ""
    )
}

