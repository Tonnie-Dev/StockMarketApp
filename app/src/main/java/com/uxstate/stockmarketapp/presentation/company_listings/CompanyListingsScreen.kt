package com.uxstate.stockmarketapp.presentation.company_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
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
                onValueChange = {

                    //send an event to ViewModel
                    viewModel.onEvent(CompanyListingsEvent.OnSearchQueryChange(it))
                },
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                placeholder = { Text(text = "Search...") },
                maxLines = 1,
                singleLine = true,
        )



        SwipeRefresh(state = swipeRefreshState,

                //send an event to the ViewModel
                onRefresh = {

                    viewModel.onEvent(CompanyListingsEvent.Refresh)
                }) {


            LazyColumn(content = {


                items(state.companies.size) { i ->
                    val company = state.companies[i]
                    CompanyItem(company = company, modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                // TODO: Navigate to Detail Screen
                            }.padding(16.dp))

                    //if not at the last item we show a divider
                    if (i < state.companies.size) {

                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            })


        }
    }

}