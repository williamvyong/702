package com.williamv.debtmake.ui.book

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.williamv.debtmake.R
import com.williamv.debtmake.model.Book
import com.williamv.debtmake.viewmodel.BookViewModel

/**
 * 账本列表主页，支持添加新账本、跳转账本详情。
 */
@Composable
fun BookListScreen(
    onBookClick: (bookId: String) -> Unit,
    onAddBook: () -> Unit,
    bookViewModel: BookViewModel = viewModel()
) {
    val books by bookViewModel.books.collectAsState(initial = emptyList())
    val mainColor = Color(0xFF1976D2)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Select Book",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                backgroundColor = mainColor,
                contentColor = Color.White
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddBook,
                backgroundColor = mainColor
            ) {
                Icon(painterResource(id = R.drawable.ic_add), contentDescription = "Add Book")
            }
        }
    ) { padding ->
        if (books.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No books available.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF7F8FB))
                    .padding(padding)
            ) {
                items(books) { book ->
                    BookListItem(
                        book = book,
                        onClick = { onBookClick(book.id) }
                    )
                }
            }
        }
        // 底部新增账本按钮
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 22.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = onAddBook,
                colors = ButtonDefaults.buttonColors(backgroundColor = mainColor),
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxWidth(0.92f)
                    .height(46.dp)
            ) {
                Text("ADD NEW BOOK", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)
            }
        }
    }
}

/**
 * 单条账本卡片
 */
@Composable
fun BookListItem(
    book: Book,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 7.dp)
            .clickable { onClick() },
        elevation = 5.dp,
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = if (!book.iconUri.isNullOrBlank()) {
                rememberAsyncImagePainter(model = Uri.parse(book.iconUri))
            } else {
                painterResource(id = R.drawable.ic_book_default)
            }
            Image(
                painter = painter,
                contentDescription = "Book Icon",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(
                    text = book.name,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (book.description.isNotBlank()) {
                    Text(
                        text = book.description,
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
