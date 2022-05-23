package com.uxstate.stockmarketapp.presentation.company_info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.uxstate.stockmarketapp.domain.model.IntradayInfo

@Composable
fun StockChart(
    info: List<IntradayInfo>,
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Green
) {

    val spacing = 100f
    var transparentGraphColor = remember { graphColor.copy(alpha = .5f) }
}