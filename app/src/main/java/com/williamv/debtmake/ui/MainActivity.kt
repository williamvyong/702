package com.williamv.debtmake.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.williamv.debtmake.database.AppDatabase
import com.williamv.debtmake.model.Book
import com.williamv.debtmake.navigation.AppNavHost
import com.williamv.debtmake.ui.theme.DebtMakeTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// 定义 DataStore 实例，用于存储是否登录状态
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 获取上下文 + DataStore 引用
        val context = this
        val dataStore = context.dataStore
        val isLoggedInKey = booleanPreferencesKey("is_logged_in")

        // 设置 Compose 内容
        setContent {
            DebtMakeTheme {
                val navController = rememberNavController() // 创建 NavController 控制导航
                var isLoggedIn by remember { mutableStateOf<Boolean?>(null) } // 登录状态控制

                // 在启动时检查是否已登录
                LaunchedEffect(Unit) {
                    val prefs = dataStore.data.first()
                    isLoggedIn = prefs[isLoggedInKey] ?: false
                }

                // 获取数据库引用
                val database = AppDatabase.getInstance(context)
                val bookDao = database.bookDao()

                // 导航图渲染：只在 isLoggedIn 确定后再执行
                isLoggedIn?.let {
                    AppNavHost(
                        navController = navController,
                        bookDao = bookDao,
                        isLoggedIn = it,
                        onLogin = {
                            // 登录成功后更新 DataStore 标志并跳转 BookList
                            context.lifecycleScope.launch {
                                dataStore.edit { prefs ->
                                    prefs[isLoggedInKey] = true
                                }
                            }
                            navController.navigate("bookList") {
                                popUpTo("login") { inclusive = true } // 移除登录页
                            }
                        },
                        onLogout = {
                            // 登出后清除状态并跳转登录页
                            context.lifecycleScope.launch {
                                dataStore.edit { prefs ->
                                    prefs[isLoggedInKey] = false
                                }
                            }
                            navController.navigate("login") {
                                popUpTo("bookList") { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}