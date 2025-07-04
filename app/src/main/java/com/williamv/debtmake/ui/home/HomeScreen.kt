// 🔵 HomeScreen.kt
package com.williamv.debtmake.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.williamv.debtmake.model.Book

@Composable
fun HomeScreen(
    navController: NavController,
    selectedBook: Book,
    // ✅ 🔧 新增参数：onBookClick
    onBookClick: () -> Unit
) {
    // 🧪 这里只是简单展示，后续你会加入实际内容
    // 当你点 example/book title 时，就调用这个：
    // onBookClick() 会跳回 BookListScreen
}
