package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamv.debtmake.data.repository.BookRepository
import com.williamv.debtmake.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * BookViewModel 用于提供账本的 UI 状态与操作逻辑
 * - 所有 Book 列表操作（新增、更新、删除）都从这里调用
 */
class BookViewModel(private val repository: BookRepository) : ViewModel() {

    // 账本列表状态
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    init {
        loadBooks()
    }

    // 加载账本列表
    fun loadBooks() {
        viewModelScope.launch {
            repository.getAllBooks().collect { bookList ->
                _books.value = bookList
            }
        }
    }

    // 插入账本
    fun insertBook(book: Book) {
        viewModelScope.launch {
            repository.insertBook(book)
            loadBooks()
        }
    }

    // 更新账本
    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBook(book)
            loadBooks()
        }
    }

    // 删除账本
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
            loadBooks()
        }
    }

    // 通过 ID 获取账本（通常用于编辑）
    suspend fun getBookById(bookId: Long): Book? {
        return repository.getBookById(bookId)
    }
}
