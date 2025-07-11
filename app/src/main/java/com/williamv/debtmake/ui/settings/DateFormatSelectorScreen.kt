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

data class DateFormatOption(val formatName: String, val example: String)

val dateFormats = listOf(
    DateFormatOption("yyyy-MM-dd", "2025-07-14"),
    DateFormatOption("dd-MM-yyyy", "14-07-2025"),
    DateFormatOption("MM-dd-yyyy", "07-14-2025"),
    DateFormatOption("dd/MM/yyyy", "14/07/2025"),
    DateFormatOption("MM/dd/yyyy", "07/14/2025"),
    DateFormatOption("yyyy/MM/dd", "2025/07/14"),
    DateFormatOption("dd MMM yyyy", "14 Jul 2025"),
    DateFormatOption("MMM dd, yyyy", "Jul 14, 2025")
    // 如需补充格式直接加
)

@Composable
fun DateFormatSelectorScreen(
    navController: NavController,
    onFormatSelected: (DateFormatOption) -> Unit
) {
    var query by remember { mutableStateOf("") }

    val filtered = dateFormats.filter {
        it.formatName.contains(query, ignoreCase = true) ||
                it.example.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Date Format", fontSize = 22.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search date format or example") }
            )

            Divider()

            LazyColumn {
                items(filtered) { fmt ->
                    DateFormatItem(fmt) {
                        onFormatSelected(fmt)
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

@Composable
fun DateFormatItem(fmt: DateFormatOption, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(fmt.formatName, fontWeight = FontWeight.Medium, fontSize = 16.sp)
        Text(fmt.example, color = MaterialTheme.colors.primary, fontSize = 16.sp)
    }
}
