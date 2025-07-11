// 文件路径：app/src/main/java/com/williamv/debtmake/ui/book/SelectBookScreen.kt
package com.williamv.debtmake.ui.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.williamv.debtmake.model.Book

/**
 * 账本选择页面
 * @param books 账本列表
 * @param onSelected 选中账本回调
 */
@ExperimentalMaterial3Api
@Composable
fun SelectBookScreen(
    books: List<Book>,
    onSelected: (Book) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Book") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(books) { book ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .clickable { onSelected(book) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = book.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        if (!book.description.isNullOrEmpty()) {
                            Text(
                                text = book.description ?: "",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
