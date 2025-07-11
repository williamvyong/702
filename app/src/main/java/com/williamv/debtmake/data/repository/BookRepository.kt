package com.williamv.debtmake.data.repository

import android.content.Context
import com.williamv.debtmake.database.DatabaseProvider
import com.williamv.debtmake.model.Book

/**
 * BookRepository
 * 账本数据仓库，封装所有账本数据库操作
 */
class BookRepository(context: Context) {
    private val bookDao = DatabaseProvider.getInstance(context).bookDao()

    fun getAllBooks(): List<Book> = bookDao.getAllBooks()

    fun getBookById(id: Long): Book? = bookDao.getBookById(id)

    fun insertBook(book: Book): Long = bookDao.insertBook(book) // 注意返回Long类型

    fun updateBook(book: Book) = bookDao.updateBook(book)

    fun deleteBook(book: Book) = bookDao.deleteBook(book)
}
