// 📂 文件路径: com.williamv.debtmake.data.repository/BookRepository.kt
package com.williamv.debtmake.data.repository

import com.williamv.debtmake.database.dao.BookDao
import com.williamv.debtmake.model.Book
import kotlinx.coroutines.flow.Flow

/**
 * BookRepository 用于封装 BookDao 的操作逻辑
 * 提供对账本（Book）的插入、更新、删除、查询功能
 */
class BookRepository(private val bookDao: BookDao) {

    // 插入一个新账本
    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    // 更新账本
    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    // 删除账本
    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }

    // 获取所有账本，作为 Flow 列表实时观察数据变化
    fun getAllBooks(): Flow<List<Book>> {
        return bookDao.getAllBooks()
    }

    // 根据 ID 获取单个账本
    suspend fun getBookById(bookId: Long): Book? {
        return bookDao.getBookById(bookId)
    }
}
