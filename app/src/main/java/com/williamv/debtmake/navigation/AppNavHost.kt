package com.williamv.debtmake.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.williamv.debtmake.ui.auth.LoginScreen
import com.williamv.debtmake.ui.home.HomeScreen

/**
 * 全局导航入口
 */
@Composable
fun AppNavHost(controller: NavHostController = rememberNavController()) {
    NavHost(
        navController = controller,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLogin = { email, password ->
                    // 登录成功，跳转 home
                    controller.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onForgotPassword = {
                    // 跳转找回密码（假设你之后有这个界面）
                    controller.navigate("forgot_password")
                },
                onSignUp = {
                    // 跳转注册
                    controller.navigate("home")
                },
                onSkip = {
                    // 跳过登录，直接进主页
                    controller.navigate("home")
                }
            )
        }

        composable("home") {
            HomeScreen()
        }

        // 你可以补上 forgot_password、signup 之类 route
        // composable("forgot_password") { ForgotPasswordScreen() }
        // composable("signup") { SignUpScreen() }
    }
}