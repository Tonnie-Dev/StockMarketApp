package com.uxstate.stockmarketapp.presentation.company_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.uxstate.stockmarketapp.presentation.ui.theme.DarkBlue


/*We need t pass the symbol here but we don't need it on this
* screen but we need to pass it so that the Navigation Library
* actually puts it as a navigation argument. It will do so
* automatically for the normal primitive types or parcelables
* so that we the acess to the symbol in the ViewModel */
@Composable
@Destination
fun CompanyInfoScreen(symbol: String, viewModel: CompanyInfoViewModel = hiltViewModel()) {


    //get a reference to state from the ViewModel

    val state = viewModel.state

    //check if there is an error and if not show the chart

    if (state.error == null) {

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBlue)
                        .padding(16.dp)
        ) {

            //the company info will be non-null as soon as it is loaded from the api

            state.companyInfo?.let {

                company ->

                //Title
                Text(
                        text = company.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))


                //symbol
                Text(
                        text = company.symbol,
                        fontStyle = FontStyle.Italic,
                        fontSize = 14.sp,

                        modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))


                Divider(
                        modifier = Modifier
                                .fillMaxWidth()

                )

                Spacer(modifier = Modifier.height(8.dp))

                //Industry Text

                Text(
                        text = "Industry: ${company.industry}",
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))


                //Country
                Text(
                        text = "Country: ${company.country}",
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))


                Divider(
                        modifier = Modifier
                                .fillMaxWidth()

                )

                Spacer(modifier = Modifier.height(8.dp))

                //Description

                Text(
                        text = company.description,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth()
                )

                //check we have entries on the intraday info we show the chart
                if (state.intradayInfo.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Market Summary")
                    Spacer(modifier = Modifier.height(36.dp))

                    /*We need to set the Canvas' height because each canvas
                    * needs to have a fixed area to draw and that you can't
                    * work well with a dynamic size*/
                    StockChart(
                            infos = state.intradayInfo,
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .align(CenterHorizontally)
                    )

                }
            }
        }
    }


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {


        if (state.isLoading) {


            CircularProgressIndicator()
        } else if (state.error != null) {

            Text(text = state.error, color = MaterialTheme.colors.error)
        }
    }
}