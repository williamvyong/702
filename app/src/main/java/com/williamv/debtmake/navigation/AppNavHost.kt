package com.williamv.debtmake.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.williamv.debtmake.ui.book.BookDetailScreen
import com.williamv.debtmake.ui.book.BookListScreen
import com.williamv.debtmake.ui.book.EditBookScreen
import com.williamv.debtmake.ui.book.AddBookScreen
import com.williamv.debtmake.ui.contact.SelectContactScreen
import com.williamv.debtmake.ui.contact.ContactDetailScreen
import com.williamv.debtmake.ui.entry.AddTransactionScreen
import com.williamv.debtmake.ui.entry.EntryStackScreen
import com.williamv.debtmake.ui.entry.EntryListScreen
import com.williamv.debtmake.viewmodel.ContactViewModel
import com.williamv.debtmake.viewmodel.EntryViewModel
import com.williamv.debtmake.viewmodel.BookViewModel

/**
 * 全局导航主入口
 * @param bookViewModel 账本ViewModel
 * @param entryViewModel 账目流水ViewModel
 * @param contactViewModel 联系人ViewModel
 * @param startDestination 初始页面路由
 */
@Composable
fun AppNavHost(
    bookViewModel: BookViewModel,
    entryViewModel: EntryViewModel,
    contactViewModel: ContactViewModel,
    startDestination: String = "bookList"
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 账本列表
        composable("bookList") {
            BookListScreen(
                onBookClick = { bookId ->
                    navController.navigate("bookDetail/$bookId")
                },
                onAddBook = { navController.navigate("addBook") },
                bookViewModel = bookViewModel
            )
        }
        // 添加账本
        composable("addBook") {
            AddBookScreen(
                onBookSaved = { navController.popBackStack() },
                bookViewModel = bookViewModel
            )
        }
        // 编辑账本
        composable(
            route = "editBook/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.LongType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getLong("bookId") ?: 0L
            EditBookScreen(
                bookId = bookId,
                onEditSuccess = { navController.popBackStack() },
                onBack = { navController.popBackStack() },
                bookViewModel = bookViewModel
            )
        }
        // 账本详情
        composable(
            route = "bookDetail/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.LongType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getLong("bookId") ?: 0L
            BookDetailScreen(
                bookId = bookId,
                entryViewModel = entryViewModel,
                contactViewModel = contactViewModel,
                onBack = { navController.popBackStack() },
                onAddCollect = { navController.navigate("addTransaction/$bookId/collect") },
                onAddPayout = { navController.navigate("addTransaction/$bookId/payout") },
                onSelectContact = { navController.navigate("selectContact/$bookId") }
            )
        }
        // 新增账目
        composable(
            route = "addTransaction/{bookId}/{type}",
            arguments = listOf(
                navArgument("bookId") { type = NavType.LongType },
                navArgument("type") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getLong("bookId") ?: 0L
            val type = backStackEntry.arguments?.getString("type") ?: "collect"
            AddTransactionScreen(
                bookId = bookId,
                entryType = type,
                entryViewModel = entryViewModel,
                contactViewModel = contactViewModel,
                onSaved = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
        // 某账本下某联系人的流水详情
        composable(
            route = "entryStack/{bookId}/{contactId}",
            arguments = listOf(
                navArgument("bookId") { type = NavType.LongType },
                navArgument("contactId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getLong("bookId") ?: 0L
            val contactId = backStackEntry.arguments?.getLong("contactId") ?: 0L
            EntryStackScreen(
                bookId = bookId,
                contactId = contactId,
                entryViewModel = entryViewModel,
                contactViewModel = contactViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        // 所有账本流水总览
        composable("entryList") {
            EntryListScreen(
                entryViewModel = entryViewModel,
                onEntryClick = { bookId, contactId ->
                    navController.navigate("entryStack/$bookId/$contactId")
                }
            )
        }
        // 联系人选择页面
        composable(
            route = "selectContact/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.LongType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getLong("bookId") ?: 0L
            SelectContactScreen(
                bookId = bookId,
                contactViewModel = contactViewModel,
                onContactSelected = { contactId ->
                    navController.navigate("entryStack/$bookId/$contactId")
                },
                onBack = { navController.popBackStack() }
            )
        }
        // 联系人详情页面
        composable(
            route = "contactDetail/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.LongType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getLong("contactId") ?: 0L
            ContactDetailScreen(
                contactId = contactId,
                contactViewModel = contactViewModel,
                entryViewModel = entryViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
