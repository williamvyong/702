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
 * BookViewModel：账本数据的 UI 状态和逻辑桥梁
 */
class BookViewModel(private val repository: BookRepository) : ViewModel() {

    // 账本列表 StateFlow
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    init {
        loadBooks()
    }

    /** 加载账本列表 */
    fun loadBooks() {
        viewModelScope.launch {
            repository.getAllBooks().collect { list ->
                _books.value = list
            }
        }
    }

    /** 新增账本 */
    fun insertBook(book: Book) {
        viewModelScope.launch {
            repository.insertBook(book)
            loadBooks()
        }
    }

    /** 更新账本 */
    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBook(book)
            loadBooks()
        }
    }

    /** 删除账本（传主键ID） */
    fun deleteBook(bookId: Long) {
        viewModelScope.launch {
            repository.deleteBook(bookId)
            loadBooks()
        }
    }

    /** 根据ID获取账本，可空类型 */
    suspend fun getBookById(bookId: Long): Book? {
        return repository.getBookById(bookId)
    }
}
