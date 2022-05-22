package com.uxstate.stockmarketapp.presentation.company_info

import com.uxstate.stockmarketapp.domain.model.CompanyInfo
import com.uxstate.stockmarketapp.domain.model.IntradayInfo

data class CompanyInfoState(
    val intradayInfo: List<IntradayInfo> = emptyList(),
        //null at first due to delay
    val companyInfo: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
