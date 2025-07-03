package com.williamv.debtmake.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.williamv.debtmake.ui.auth.ForgotPasswordScreen
import com.williamv.debtmake.ui.auth.LoginScreen
import com.williamv.debtmake.ui.auth.SignUpScreen
import com.williamv.debtmake.ui.book.AddBookScreen
import com.williamv.debtmake.ui.book.Book
import com.williamv.debtmake.ui.book.BookListScreen
import com.williamv.debtmake.ui.home.HomeScreen

@Composable
fun AppNavHost(controller: NavHostController = rememberNavController()) {
    val mockBooks = remember {
        mutableStateListOf(
            Book(1, "Personal", "Track personal debts"),
            Book(2, "Group Trip", "Expenses for Langkawi trip")
        )
    }

    var selectedBook by remember { mutableStateOf<Book?>(mockBooks.firstOrNull()) }

    NavHost(
        navController = controller,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLogin = { _, _ ->
                    controller.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onForgotPassword = { controller.navigate("forgot_password") },
                onSignUp = { controller.navigate("signup") },
                onSkip = { controller.navigate("home") }
            )
        }

        composable("signup") {
            SignUpScreen(
                onRegister = { _, _, _ ->
                    controller.navigate("home") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onGoToLogin = { controller.navigate("login") },
                onSkip = { controller.navigate("home") }
            )
        }

        composable("forgot_password") {
            ForgotPasswordScreen(
                onResetRequest = { _ ->
                    controller.navigate("login") {
                        popUpTo("forgot_password") { inclusive = true }
                    }
                },
                onGoToLogin = { controller.navigate("login") },
                onSkip = { controller.navigate("home") }
            )
        }

        composable("home") {
            HomeScreen(
                navController = controller,
                selectedBook = selectedBook
            )
        }

        composable("book_list") {
            BookListScreen(
                books = mockBooks,
                currentBookId = selectedBook?.id ?: -1L,
                onSelectBook = {
                    selectedBook = it
                    controller.popBackStack()
                },
                onAddBook = {
                    controller.navigate("add_book")
                },
                onBack = {
                    controller.popBackStack()
                }
            )
        }

        composable("add_book") {
            AddBookScreen(
                onAddBook = { title, desc, uri ->
                    val newBook = Book(mockBooks.size.toLong() + 1, title, desc)
                    mockBooks.add(newBook)
                    selectedBook = newBook
                    controller.popBackStack()
                },
                onBack = {
                    controller.popBackStack()
                }
            )
        }
    }
}