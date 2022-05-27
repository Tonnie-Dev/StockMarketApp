package com.uxstate.stockmarketapp.presentation.company_info

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination


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

}