package com.williamv.debtmake.database.dao

import androidx.room.*
import com.williamv.debtmake.model.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * TransactionDao：交易表操作接口
 */
@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction): Long

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    suspend fun deleteTransactionById(transactionId: Long)

    @Query("SELECT * FROM transactions WHERE bookId = :bookId ORDER BY timestamp DESC")
    fun getTransactionsForBook(bookId: Long): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE bookId = :bookId AND contactId = :contactId ORDER BY timestamp DESC")
    fun getTransactionsForContact(bookId: Long, contactId: Long): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE id = :transactionId LIMIT 1")
    suspend fun getTransactionById(transactionId: Long): Transaction?
}
