package com.williamv.debtmake.ui.book

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.williamv.debtmake.R

// 假设 Book 数据模型
data class Book(val id: Long, val title: String, val description: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    books: List<Book>,
    currentBookId: Long = 1L,
    onSelectBook: (Book) -> Unit,
    onAddBook: () -> Unit,
    onBack: () -> Unit = {}
) {
    val brandPurple = Color(0xFF6B39E1)

    // 设置状态栏颜色
    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    SideEffect {
        window?.statusBarColor = brandPurple.toArgb()
        WindowCompat.getInsetsController(window!!, view).isAppearanceLightStatusBars = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Select book")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Close, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(books) { book ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectBook(book) }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = book.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = book.description,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            if (book.id == currentBookId) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Color(0xFF2196F3), shape = CircleShape)
                                )
                            }
                        }
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }
                }
            }

            // Add new book section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onAddBook() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("+", color = brandPurple, fontSize = 24.sp, modifier = Modifier.padding(end = 8.dp))
                Text("ADD NEW BOOK", color = brandPurple, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}