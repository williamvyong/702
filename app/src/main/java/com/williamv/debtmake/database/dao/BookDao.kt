package com.williamv.debtmake.database.dao

import androidx.room.*
import com.williamv.debtmake.model.Book
import kotlinx.coroutines.flow.Flow

/**
 * BookDao：账本表操作接口
 */
@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY updatedAt DESC")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE id = :bookId LIMIT 1")
    suspend fun getBookById(bookId: Long): Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book): Long

    @Update
    suspend fun updateBook(book: Book)

    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteBookById(bookId: Long)
}
