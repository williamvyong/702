package com.williamv.debtmake.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.williamv.debtmake.ui.book.BookListScreen
import com.williamv.debtmake.ui.book.AddBookScreen
import com.williamv.debtmake.ui.login.LoginScreen
import com.williamv.debtmake.ui.login.SignUpScreen
import com.williamv.debtmake.ui.login.ForgotPasswordScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = "login"
) {
    NavHost(navController = navController, startDestination = startDestination) {

        // 登录页面
        composable("login") {
            LoginScreen(
                onLogin = { email, password ->
                    // 登录成功跳转账本列表
                    navController.navigate("bookList") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onSignUp = {
                    navController.navigate("signup")
                },
                onForgotPassword = {
                    navController.navigate("forgotPassword")
                },
                onSkip = {
                    // 跳过登录 - 调试或游客模式
                    navController.navigate("bookList") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // 注册页面
        composable("signup") {
            SignUpScreen(
                onBack = {
                    navController.popBackStack()
                },
                onSuccess = {
                    navController.navigate("login") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            )
        }

        // 忘记密码页面
        composable("forgotPassword") {
            ForgotPasswordScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // 账本列表页面
        composable("bookList") {
            BookListScreen(
                onAddBook = {
                    navController.navigate("addBook")
                },
                onSelectBook = { bookId ->
                    // TODO: 跳转账本详情页（未定义）
                }
            )
        }

        // 添加账本页面
        composable("addBook") {
            AddBookScreen(
                onBack = {
                    navController.popBackStack()
                },
                onBookSaved = {
                    navController.popBackStack()
                }
            )
        }
    }
}
