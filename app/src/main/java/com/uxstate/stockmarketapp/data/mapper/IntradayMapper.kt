package com.uxstate.stockmarketapp.data.mapper

import com.uxstate.stockmarketapp.data.remote.dto.IntradayInfoDTO
import com.uxstate.stockmarketapp.domain.model.IntradayInfo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntradayInfoDTO.toIntradayInfo(): IntradayInfo {

    val pattern = "yyyy-MM-dd HH:mm:ss"
   val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
   val localDateTime = LocalDateTime.parse(this.timeStamp, formatter)
    return IntradayInfo(date = localDateTime,close = this.close)
}


fun IntradayInfo.toIntradayDTO():IntradayInfoDTO{

    return IntradayInfoDTO(timeStamp = this.date.toString(), close = this.close)
}