package com.williamv.debtmake.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.williamv.debtmake.database.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    selectedBook: Book?,
    onBookClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = selectedBook?.name ?: "No Book Selected")
                },
                actions = {
                    TextButton(onClick = onBookClick) {
                        Text("Change")
                    }
                }
            )
        }
    ) { padding ->
        Text(
            text = "Welcome to ${selectedBook?.name ?: "DebtMate"}!",
            modifier = Modifier.padding(padding)
        )
    }
}