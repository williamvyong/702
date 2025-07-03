package com.williamv.debtmake.navigation

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.williamv.debtmake.database.AppDatabase
import com.williamv.debtmake.database.Book
import com.williamv.debtmake.ui.book.AddBookScreen
import com.williamv.debtmake.ui.book.BookListScreen
import com.williamv.debtmake.ui.home.HomeScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    context: Context
) {
    val database = remember { AppDatabase.getInstance(context) }
    val bookDao = remember { database.bookDao() }

    var books by remember { mutableStateOf<List<Book>>(emptyList()) }
    var selectedBookId by remember { mutableStateOf<Long?>(null) }

    // Initial fetch
    LaunchedEffect(Unit) {
        books = bookDao.getAllBooks().first()
        selectedBookId = books.firstOrNull()?.id
    }

    val selectedBook = books.find { it.id == selectedBookId }

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                navController = navController,
                selectedBook = selectedBook,
                onBookClick = { navController.navigate("book_list") }
            )
        }

        composable("book_list") {
            BookListScreen(
                books = books,
                selectedBookId = selectedBookId,
                onBookClick = {
                    selectedBookId = it.id
                    navController.popBackStack()
                },
                navController = navController
            )
        }

        composable("addBook") {
            AddBookScreen(
                onAddBook = { title, description, imageUri ->
                    val newBook = Book(
                        name = title,
                        description = description,
                        iconUri = imageUri?.toString() ?: "",
                        updatedAt = System.currentTimeMillis()
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        bookDao.insert(newBook)
                        books = bookDao.getAllBooks().first()
                        selectedBookId = books.lastOrNull()?.id
                    }
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
