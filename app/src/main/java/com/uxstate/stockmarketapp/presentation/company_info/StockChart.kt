package com.uxstate.stockmarketapp.presentation.company_info

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
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

        //for lower value we need the lowest value (as rounded to the floor the better)
        infos.minOfOrNull { it.close }?.toInt() ?:0
    }

    //Density to help us change font sp to px
    val density = LocalDensity.current

    val textPaint = remember(density) {

        //use android.graphics.Paint to draw text on the screen
        Paint().apply {

            color = android.graphics.Color.WHITE

            //default is top-left
            textAlign = Paint.Align.CENTER

            //returns the result of executing the lambda
            textSize = density.run {12.sp.toPx()}
        }
    }

    //Canvas Composable - provides drawing are to draw anything

    Canvas(modifier = modifier){


        val spacePerHour = (size.width - spacing)/infos.size
    }
}