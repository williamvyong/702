package com.williamv.debtmake.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Currency(val country: String, val code: String, val symbol: String)

// 示例货币列表，完整的可以后续补充
val currencies = listOf(
    Currency("Malaysia", "MYR", "RM"),
    Currency("United States", "USD", "$"),
    Currency("Eurozone", "EUR", "€"),
    Currency("Singapore", "SGD", "S$"),
    Currency("Japan", "JPY", "¥"),
    Currency("China", "CNY", "¥"),
    Currency("United Kingdom", "GBP", "£"),
    Currency("Australia", "AUD", "A$"),
    Currency("Canada", "CAD", "CA$"),
    Currency("Hong Kong", "HKD", "HK$"),
    Currency("Switzerland", "CHF", "CHF"),
    Currency("South Korea", "KRW", "₩"),
    Currency("Taiwan", "TWD", "NT$"),
    Currency("Thailand", "THB", "฿"),
    Currency("Indonesia", "IDR", "Rp"),
    Currency("Vietnam", "VND", "₫"),
    Currency("India", "INR", "₹"),
    Currency("Russia", "RUB", "₽"),
    Currency("Brazil", "BRL", "R$"),
    Currency("Mexico", "MXN", "Mex$")
    // ...其他国家的货币
)

@Composable
fun CurrencySelectorScreen(
    navController: NavController,
    onCurrencySelected: (Currency) -> Unit
) {
    var query by remember { mutableStateOf("") }

    val filteredCurrencies = currencies.filter {
        it.country.contains(query, true) || it.code.contains(query, true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Currency", fontSize = 22.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Search Box
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search by country or code") }
            )

            Divider()

            LazyColumn {
                items(filteredCurrencies) { currency ->
                    CurrencyItem(currency) {
                        onCurrencySelected(currency)
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyItem(currency: Currency, onClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(horizontal = 16.dp, vertical = 10.dp)) {
        Text(currency.country, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(currency.code, color = MaterialTheme.colors.primary)
            Text(currency.symbol)
        }
    }
}
