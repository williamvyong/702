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
 * BookViewModel 用于管理账本（Book）相关的 UI 状态与逻辑
 * 通过依赖注入 BookRepository 来操作数据层
 */
class BookViewModel(private val repository: BookRepository) : ViewModel() {

    // 所有账本列表，转换为 StateFlow 以便 UI 实时观察变化
    val allBooks: StateFlow<List<Book>> = repository.getAllBooks()
        .map { books -> books.sortedBy { it.name } } // 可选排序
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 插入账本
    fun insertBook(book: Book) {
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

    // 获取指定 ID 的账本（可在需要时调用）
    suspend fun getBookById(bookId: Long): Book? {
        return repository.getBookById(bookId)
    }
}
