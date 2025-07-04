// ✅ 文件路径：app/src/main/java/com/williamv/debtmake/navigation/AppNavHost.kt
// ✅ 文件类型：Kotlin Composable 文件
// ✅ 功能说明：应用的导航主机，定义了所有页面的路由导航逻辑

package com.williamv.debtmake.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.williamv.debtmake.model.Book
import com.williamv.debtmake.ui.login.LoginScreen
import com.williamv.debtmake.ui.book.AddBookScreen
import com.williamv.debtmake.ui.book.BookListScreen

@Composable
fun AppNavHost(
    navController: NavHostController, // 导航控制器，由 MainActivity 提供
    isLoggedIn: Boolean,             // 是否已登录，控制初始页面
    onLogin: () -> Unit,            // 登录成功回调（设置登录状态）
    onBookClick: (Book) -> Unit,    // 点击某个账本的回调（跳转或打开账本详情）
    onBookSaved: (String, String, Uri?) -> Unit // 添加账本时回调
) {
    // 设置导航主机，startDestination 根据是否登录决定起始页
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "book_list" else "login"
    ) {

        // 登录页面
        composable("login") {
            LoginScreen(onLogin = onLogin)
        }

        // 账本列表页面
        composable("book_list") {
            BookListScreen(
                onBookClick = onBookClick,
                onAddBook = {
                    navController.navigate("add_book")
                }
            )
        }

        // 添加账本页面
        composable("add_book") {
            AddBookScreen(
                onBookSaved = onBookSaved,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
