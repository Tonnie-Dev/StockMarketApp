package com.uxstate.stockmarketapp.presentation.company_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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



            }
        }
    }

}