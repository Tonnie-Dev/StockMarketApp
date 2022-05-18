package com.uxstate.stockmarketapp.presentation.company_listings

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CompanyItem(name: String, exchange: String, symbol: String, modifier: Modifier = Modifier) {


    Row(
        modifier = modifier
                .fillMaxWidth()
                .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column() {
            Text(text = name, style = MaterialTheme.typography.body1)
            Text(text = symbol, fontStyle = FontStyle.Italic)
        }

        Column() {

            Text(text = exchange, style = MaterialTheme.typography.body1)
        }
    }

}

@Preview(name = "CompanyItemPreview")
@Composable
fun CompanyItemPreview() {
    CompanyItem(name = "Tesla", exchange = "NYSE", symbol = "TES")
}