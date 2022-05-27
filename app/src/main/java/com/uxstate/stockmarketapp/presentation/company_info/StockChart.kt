package com.uxstate.stockmarketapp.presentation.company_info

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import com.uxstate.stockmarketapp.domain.model.IntradayInfo
import kotlin.math.round
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

    val upperValue = remember(infos) {
        //find the highest value from infos list

        //returns max of the close value or null (close is double)
        (infos.maxOfOrNull { it.close }
                //add one as integer are rounded to floor
                ?.plus(1))?.roundToInt() ?: 0
    }


    val lowerValue = remember(infos) {

        //for lower value we need the lowest value (as rounded to the floor the better)
        infos.minOfOrNull { it.close }
                ?.toInt() ?: 0
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

            /*we use density so that the paint text will
            * be the same as other text sizes for this
            * composable*/
            textSize = density.run { 12.sp.toPx() }
        }
    }

    //Canvas Composable - provides drawing area to draw anything

    Canvas(modifier = modifier) {


        val spacePerHour = (size.width - spacing) / infos.size


        (0 until (infos.size - 1) step 2).forEach { i ->

            val info = infos[i]
            val hour = info.date.hour

            //Use native canvas as Jetpack Canvas doesn't support text
            drawContext.canvas.nativeCanvas.apply {
                //get access to native canvas fxns
                drawText(hour.toString(), spacing + spacePerHour * i, size.height - 5, textPaint)
            }
        }

        /*calculate priceStep by computing the difference divided by
       5 numbers that we want to show*/

        val priceStep = (upperValue - lowerValue) / 5f

        (0..5).forEach { i ->


            drawContext.canvas.nativeCanvas.apply {


                drawText(
                        round((lowerValue + i * priceStep)).toString(),
                        30f,
                        size.height - spacing - (i * size.height / 5),
                        textPaint
                )
            }
        }


        //GRAPH

        //paths uses curves

        val strokePath = Path().apply {

            val height = size.height
            //infos.indices returns a range 0..info.size -1
            for (i in infos.indices) {

                val info = infos[i]

                //checks array out of bounds exception
                val nextInfo = infos.getOrNull(i + 1) ?: infos.last()
                /*check how far we are from zero and divide that by
                 length of y-axis - map the intervalue to a number
                  between zero & one*/
                val leftRatio = (info.close - lowerValue) / (upperValue - lowerValue)
                val rightRatio = (nextInfo.close - lowerValue) / (upperValue - lowerValue)

                //get the first 2 coordinates
                val x1 = spacing + (spacePerHour * i)
                val y1 = height - spacing - (leftRatio * height).toFloat()

                val x2 = spacing + (i +1) *spacePerHour
                val y2 = height - spacing - (rightRatio *height).toFloat()

                //check if we are at the first coordinate and move to x1,y1
                if (i == 0) {

                    moveTo(x = x1, y = y1)
                }

                //after that we start drawing a line using quadratic Bezier
quadraticBezierTo(x1 = x1, y1 = y1, x2 = (x2 - x1/2f), y2 = (y2 - y1)/2f)
            }
        }
    }
}