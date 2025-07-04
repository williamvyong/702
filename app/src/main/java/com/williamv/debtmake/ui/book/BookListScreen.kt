// 📂 文件路径: com.williamv.debtmake.ui.book/BookListScreen.kt
package com.williamv.debtmake.ui.book

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.williamv.debtmake.R
import com.williamv.debtmake.model.Book
import com.williamv.debtmake.viewmodel.BookViewModel

/**
 * BookListScreen：展示所有账本列表
 * @param onSelectBook 点击账本后触发，传入 bookId 和 bookName
 * @param onAddBook 点击添加新账本按钮时触发
 */
@Composable
fun BookListScreen(
    onSelectBook: (Long, String) -> Unit,
    onAddBook: () -> Unit,
    bookViewModel: BookViewModel = viewModel()
) {
    val books by bookViewModel.allBooks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Book") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddBook) {
                Icon(painterResource(id = R.drawable.ic_add), contentDescription = "Add Book")
            }
        }
    ) { paddingValues ->
        if (books.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No books available")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(books) { book ->
                    BookListItem(book = book, onSelect = { onSelectBook(book.id, book.name) })
                }
            }
        }
    }
}

/**
 * BookListItem：单个账本卡片
 */
@Composable
fun BookListItem(
    book: Book,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onSelect() },
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 圆形图标（支持本地默认图或自定义图）
            val painter = if (book.iconUri != null) {
                rememberImagePainter(data = Uri.parse(book.iconUri))
            } else {
                painterResource(id = R.drawable.ic_book_default) // 默认图标
            }

            Image(
                painter = painter,
                contentDescription = "Book Icon",
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )

            Column {
                Text(text = book.name, style = MaterialTheme.typography.h6)
                if (!book.description.isNullOrEmpty()) {
                    Text(
                        text = book.description,
                        style = MaterialTheme.typography.body2,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
