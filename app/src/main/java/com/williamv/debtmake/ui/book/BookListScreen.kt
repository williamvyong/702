package com.williamv.debtmake.ui.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.williamv.debtmake.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    books: List<Book>,
    selectedBookId: Long?,
    onBookClick: (Book) -> Unit,
    onAddBook: () -> Unit,
    onBack: () -> Unit,
    onBookSelected: (Book) -> Unit,
    navController: NavController
)
 {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Book") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("add_book")
            }) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            books.forEach { book ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onBookSelected(book)
                            onBookClick(book)
                            navController.navigate("home")
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = book.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (book.id == selectedBookId) Color.Magenta else Color.Black
                    )
                }
            }
        }
    }
}
