package com.williamv.debtmake.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.williamv.debtmake.model.Transaction

/**
 * TransactionDao
 * 账本扩展交易接口
 */
@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE bookId = :bookId")
    fun getTransactionsForBook(bookId: Long): List<Transaction>

    @Query("SELECT * FROM transactions WHERE bookId = :bookId AND contactId = :contactId")
    fun getTransactionsForContact(bookId: Long, contactId: Long): List<Transaction>

    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getTransactionById(id: Long): Transaction?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: Transaction): Long

    @Update
    fun updateTransaction(transaction: Transaction)

    @Delete
    fun deleteTransaction(transaction: Transaction)
}
