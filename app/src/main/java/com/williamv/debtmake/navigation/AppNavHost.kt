package com.williamv.debtmake.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.williamv.debtmake.model.Book
import com.williamv.debtmake.model.Contact
import com.williamv.debtmake.ui.book.BookDetailScreen
import com.williamv.debtmake.ui.book.SelectBookScreen
import com.williamv.debtmake.ui.contact.SelectContactScreen
import com.williamv.debtmake.ui.entry.AddTransactionScreen
import com.williamv.debtmake.ui.settings.CurrencySelectorScreen
import com.williamv.debtmake.ui.settings.SettingsScreen
import com.williamv.debtmake.util.RecentBookStore
import com.williamv.debtmake.viewmodel.BookViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    bookViewModel: BookViewModel = viewModel()
) {
    val context = LocalContext.current
    var initialBookId by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // 启动时读取最近账本 id
    LaunchedEffect(Unit) {
        val books = bookViewModel.books.value
        val recentBookId = withContext(Dispatchers.IO) {
            RecentBookStore.getRecentBookId(context)
        }
        initialBookId = recentBookId?.takeIf { id -> books.any { it.id == id } }
            ?: books.firstOrNull()?.id
        isLoading = false
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (initialBookId != null) {
        NavHost(
            navController = navController,
            startDestination = "bookDetail/{bookId}"
        ) {
            composable(
                route = "bookDetail/{bookId}",
                arguments = listOf(navArgument("bookId") { type = NavType.StringType })
            ) { backStackEntry ->
                val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                val books = bookViewModel.books.value
                val book = books.firstOrNull { it.id == bookId }
                if (book != null) {
                    BookDetailScreen(
                        book = book,
                        onBack = { navController.popBackStack() },
                        onAddCollect = { navController.navigate("addTransaction") },
                        onAddPayout = { navController.navigate("addTransaction") },
                        onMore = { /* 更多操作 */ }
                    )
                } else {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }

            composable("addTransaction") { backStackEntry ->
                val savedStateHandle = backStackEntry.savedStateHandle
                val selectedContact = savedStateHandle.get<Contact>("selectedContact")
                val selectedBook = savedStateHandle.get<Book>("selectedBook")
                AddTransactionScreen(
                    navController = navController,
                    onBack = { navController.popBackStack() },
                    onSaved = { navController.popBackStack() },
                    selectedContact = selectedContact,
                    selectedBook = selectedBook,
                    onClearContact = { savedStateHandle.remove<Contact>("selectedContact") },
                    onClearBook = { savedStateHandle.remove<Book>("selectedBook") }
                )
            }

            composable("selectContact") {
                SelectContactScreen(
                    onBack = { navController.popBackStack() },
                    onSelected = { contact ->
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("selectedContact", contact)
                        navController.popBackStack()
                    }
                )
            }

            composable("selectBook") {
                SelectBookScreen(
                    onBack = { navController.popBackStack() },
                    onSelected = { book ->
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("selectedBook", book)
                        navController.popBackStack()
                    }
                )
            }

            composable("settings") {
                SettingsScreen(
                    onBack = { navController.popBackStack() },
                    navController = navController
                )
            }

            composable("currency_selector") {
                CurrencySelectorScreen(
                    navController = navController,
                    onCurrencySelected = { currency ->
                        // 存储到 DataStore 或 SharedPreferences
                    }
                )
            }
        }
    }
}
