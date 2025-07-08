package com.williamv.debtmake.data.repository

import com.williamv.debtmake.database.dao.BookDao
import com.williamv.debtmake.model.Book
import kotlinx.coroutines.flow.Flow

/**
 * BookRepository 用于封装 BookDao 的数据库操作，供 ViewModel 调用。
 */
class BookRepository(private val bookDao: BookDao) {

    /**
     * 获取所有账本数据（支持 Flow 观察数据变动）
     */
    fun getAllBooks(): Flow<List<Book>> {
        return bookDao.getAllBooks()
    }

    /**
     * 根据 ID 获取指定账本
     */
    suspend fun getBookById(bookId: Long): Book? {
        return bookDao.getBookById(bookId)
    }

    /**
     * 插入新账本
     */
    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    /**
     * 更新现有账本
     */
    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    /**
     * 删除账本
     */
    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }
}
