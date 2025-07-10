package com.williamv.debtmake.ui.book

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.williamv.debtmake.R
import com.williamv.debtmake.model.Book
import com.williamv.debtmake.viewmodel.BookViewModel

@Composable
fun SelectBookScreen(
    onBack: () -> Unit,
    onSelected: (Book) -> Unit,
    bookViewModel: BookViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // 顶部主色（可自定义或跟随主题色）
    val mainColor = Color(0xFF1976D2)

    // 账本列表
    val bookList by bookViewModel.books.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = mainColor,
                contentColor = Color.White,
                elevation = 0.dp,
                title = {
                    Text(
                        "Select book",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F8FB))
                .padding(padding)
        ) {
            items(bookList) { book ->
                BookListItem(
                    book = book,
                    onClick = { onSelected(book) }
                )
            }
        }
    }
}

@Composable
private fun BookListItem(
    book: Book,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val painter = if (book.imageUri.isNullOrBlank()) {
            painterResource(id = R.drawable.ic_book_default) // 用你项目里账本默认图
        } else {
            rememberAsyncImagePainter(book.imageUri)
        }
        Image(
            painter = painter,
            contentDescription = "Book Icon",
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(0xFFE2E2E2))
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(book.name, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            if (!book.description.isNullOrBlank()) {
                Text(book.description!!, color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}
