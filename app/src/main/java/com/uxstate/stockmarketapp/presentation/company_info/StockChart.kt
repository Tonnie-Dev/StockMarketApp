package com.uxstate.stockmarketapp.presentation.company_info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.uxstate.stockmarketapp.domain.model.IntradayInfo
import kotlin.math.roundToInt

@Composable
fun StockChart(
    infos: List<IntradayInfo>,
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Green
) {

    val spacing = 100f

    //cache values with remember so that they are not re-calculated
    var transparentGraphColor = remember { graphColor.copy(alpha = .5f) }

    var upperValue =  remember(infos) {
        //find the highest value from infos list

        //returns max of the close value or null
        (infos.maxOfOrNull { it.close }
                //add one as integer are rounded to floor
                ?.plus(1))?.roundToInt() ?: 0
    }


    val lowerValue = remember (infos){

        infos.minOfOrNull { it.close }?.toInt() ?:0
    }
}