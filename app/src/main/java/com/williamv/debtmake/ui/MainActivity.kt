package com.williamv.debtmake.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import com.williamv.debtmake.database.AppDatabase
import com.williamv.debtmake.model.Book
import com.williamv.debtmake.navigation.AppNavHost
import com.williamv.debtmake.ui.theme.DebtMakeTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// DataStore 创建扩展
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DebtMakeTheme {
                val context = LocalContext.current
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()

                // 记住登录状态
                var isLoggedIn by remember { mutableStateOf<Boolean?>(null) }

                // 第一次启动时读取登录状态
                LaunchedEffect(Unit) {
                    val prefs = context.dataStore.data.first()
                    isLoggedIn = prefs[booleanPreferencesKey("is_logged_in")] ?: false
                }

                // 当登录成功后调用这个函数来保存状态
                fun markLoggedIn() {
                    scope.launch {
                        context.dataStore.edit { prefs ->
                            prefs[booleanPreferencesKey("is_logged_in")] = true
                        }
                        isLoggedIn = true
                    }
                }

                // 初始化数据库和 books 数据
                val db = AppDatabase.getInstance(applicationContext)
                val bookDao = db.bookDao()
                val books by produceState(initialValue = emptyList<Book>(), producer = {
                    value = bookDao.getAllBooks().first()
                })
                var selectedBookId by remember { mutableStateOf<Long?>(null) }

                // 默认选择第一个账本
                LaunchedEffect(books) {
                    if (books.isNotEmpty() && selectedBookId == null) {
                        selectedBookId = books.first().id
                    }
                }

                // 启动导航
                if (isLoggedIn != null) {
                    AppNavHost(
                        navController = navController,
                        isLoggedIn = isLoggedIn == true,
                        books = books,
                        selectedBookId = selectedBookId,
                        onBookClick = { selectedBookId = it.id },
                        onAddBook = { title, desc, uri ->
                            val newBook = Book(name = title, description = desc, imageUri = uri?.toString())
                            scope.launch {
                                bookDao.insertBook(newBook)
                            }
                        },
                        onBookSelected = { selectedBookId = it.id },
                        onBack = { navController.popBackStack() },
                        onLoginSuccess = { markLoggedIn() }
                    )
                }
            }
        }
    }
}
