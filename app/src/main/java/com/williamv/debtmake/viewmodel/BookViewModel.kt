// 📂 文件路径: com.williamv.debtmake.viewmodel/BookViewModel.kt
package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamv.debtmake.data.repository.BookRepository
import com.williamv.debtmake.model.Book
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * BookViewModel：用于管理账本数据与 UI 的连接
 * 调用 BookRepository 完成账本的增删改查
 */
class BookViewModel(private val repository: BookRepository) : ViewModel() {

    // 所有账本数据流，以 StateFlow 形式暴露给 UI
    val allBooks: StateFlow<List<Book>> = repository.getAllBooks()
        .map { it.sortedByDescending { book -> book.id } } // 可选排序：按 id 降序
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 添加新账本
    fun addBook(book: Book) {
        viewModelScope.launch {
            repository.insertBook(book)
        }
    }

    // 更新账本
    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    // 删除账本
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }
}
