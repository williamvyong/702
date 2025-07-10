package com.williamv.debtmake.data.repository

import com.williamv.debtmake.database.dao.BookDao
import com.williamv.debtmake.model.Book
import kotlinx.coroutines.flow.Flow

/**
 * BookRepository 用于对账本数据进行数据库封装，供 ViewModel 层调用
 */
class BookRepository(private val bookDao: BookDao) {

    /** 获取所有账本（Flow 监听） */
    fun getAllBooks(): Flow<List<Book>> = bookDao.getAllBooks()

    /** 根据ID获取账本，可能为null */
    suspend fun getBookById(bookId: Long): Book? = bookDao.getBookById(bookId)

    /** 新增账本，返回主键ID */
    suspend fun insertBook(book: Book): Long = bookDao.insertBook(book)

    /** 更新账本 */
    suspend fun updateBook(book: Book) = bookDao.updateBook(book)

    /** 删除账本（根据主键ID） */
    suspend fun deleteBook(bookId: Long) = bookDao.deleteBookById(bookId)
}
