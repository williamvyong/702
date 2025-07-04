package com.williamv.debtmake.navigation

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.williamv.debtmake.ui.auth.LoginScreen
import com.williamv.debtmake.ui.book.AddBookScreen
import com.williamv.debtmake.ui.book.BookListScreen
import com.williamv.debtmake.ui.home.HomeScreen

@Composable
fun AppNavHost(
    isLoggedIn: Boolean,
    context: Context
) {
    val navController = rememberNavController()
    var loggedInState by remember { mutableStateOf(isLoggedIn) }

    NavHost(
        navController = navController,
        startDestination = if (loggedInState) "home" else "login"
    ) {
        composable("login") {
            LoginScreen(
                onLogin = { email, password ->
                    // 这里只是演示，不做实际验证
                    context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean("is_logged_in", true)
                        .apply()

                    loggedInState = true
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

        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("bookList") {
            BookListScreen(
                onAddBook = { navController.navigate("addBook") },
                onBack = { navController.popBackStack() },
                onBookSelected = { /* TODO */ }
            )
        }

        composable("addBook") {
            AddBookScreen(
                onBookSaved = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
