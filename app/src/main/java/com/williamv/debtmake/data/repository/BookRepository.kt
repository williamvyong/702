// 📂 文件路径: com.williamv.debtmake.data.repository/BookRepository.kt
package com.williamv.debtmake.data.repository

import com.williamv.debtmake.database.dao.BookDao
import com.williamv.debtmake.model.Book
import kotlinx.coroutines.flow.Flow

/**
 * BookRepository：封装 BookDao，用于处理账本（Book）相关数据库操作
 */
class BookRepository(private val bookDao: BookDao) {

    // 获取所有账本
    fun getAllBooks(): Flow<List<Book>> = bookDao.getAllBooks()

    // 获取指定 ID 的账本
    fun getBookById(bookId: Long): Flow<Book?> = bookDao.getBookById(bookId)

    // 插入账本
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
}
