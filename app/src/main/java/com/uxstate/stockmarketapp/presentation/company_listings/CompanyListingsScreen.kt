package com.uxstate.stockmarketapp.presentation.company_listings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination(start = true)
@Composable
fun CompanyListingsScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListingsViewModel = hiltViewModel()
) {

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)
    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
                value = viewModel.state.searchQuery,
                onValueChange = { viewModel.onEvent(CompanyListingsEvent.OnSearchQueryChange(it)) },
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                placeholder = { Text(text = "Search...")},
                maxLines = 1,
                singleLine = true,
                
                
                )


    }

}