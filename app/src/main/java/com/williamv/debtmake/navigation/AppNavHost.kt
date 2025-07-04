package com.williamv.debtmake.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import com.williamv.debtmake.model.Book
import com.williamv.debtmake.ui.login.LoginScreen
import com.williamv.debtmake.ui.book.AddBookScreen
import com.williamv.debtmake.ui.book.BookListScreen
import com.williamv.debtmake.ui.home.HomeScreen

@Composable
fun AppNavHost(
    navController: NavController,
    isLoggedIn: Boolean,
    books: List<Book>,
    selectedBookId: Long?,
    onBookClick: (Book) -> Unit,
    onAddBook: (String, String, Uri?) -> Unit,
    onBookSelected: (Book) -> Unit,
    onBack: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    // 如果已登录则进入 Home，否则进入 Login
    val startDestination = if (isLoggedIn) "home" else "login"
    val selectedBook = books.find { it.id == selectedBookId } ?: Book(name = "Master Book")

    NavHost(navController = navController, startDestination = startDestination) {
        // 登录页
        composable("login") {
            LoginScreen(
                onLogin = {
                    onLoginSuccess()
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onForgotPassword = { /* TODO */ },
                onSignUp = { /* TODO */ },
                onSkip = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // 账本列表
        composable("book_list") {
            BookListScreen(
                books = books,
                selectedBookId = selectedBookId,
                onBookClick = onBookClick,
                onAddBook = { navController.navigate("add_book") },
                onBookSelected = onBookSelected,
                onBack = onBack,
                navController = navController
            )
        }

        // 添加账本
        composable("add_book") {
            AddBookScreen(
                onAddBook = onAddBook,
                onBack = onBack
            )
        }

        // 主界面
        composable("home") {
            HomeScreen(
                navController = navController,
                selectedBook = selectedBook,
                onBookClick = {
                    navController.navigate("book_list")
                }
            )
        }
    }
}
