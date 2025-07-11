package com.williamv.debtmake.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.williamv.debtmake.model.Book

@Dao
interface BookDao {

    @Query("SELECT * FROM books ORDER BY createdAt DESC")
    fun getAllBooks(): List<Book>

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBookById(id: Long): Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book)

    // 你可以添加 update/delete 方法
}
