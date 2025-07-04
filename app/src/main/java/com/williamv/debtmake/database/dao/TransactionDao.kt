package com.williamv.debtmake.database.dao

import androidx.room.*
import com.williamv.debtmake.model.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * Transaction 数据访问接口（DAO）
 * 提供插入、查询、更新、删除等功能
 */
@Dao
interface TransactionDao {

    /**
     * 插入一笔新交易
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    /**
     * 更新一笔已存在的交易
     */
    @Update
    suspend fun updateTransaction(transaction: Transaction)

    /**
     * 删除一笔交易
     */
    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    /**
     * 获取指定账本（bookId）中所有交易，按时间倒序排列
     */
    @Query("SELECT * FROM transactions WHERE bookId = :bookId ORDER BY timestamp DESC")
    fun getTransactionsForBook(bookId: Long): Flow<List<Transaction>>

    /**
     * 获取某个账本中指定联系人的所有交易
     */
    @Query("SELECT * FROM transactions WHERE bookId = :bookId AND contactId = :contactId ORDER BY timestamp DESC")
    fun getTransactionsForContact(bookId: Long, contactId: Long): Flow<List<Transaction>>

    /**
     * 获取所有交易记录（主账本使用）
     */
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    /**
     * 根据 ID 获取特定交易
     */
    @Query("SELECT * FROM transactions WHERE id = :transactionId LIMIT 1")
    suspend fun getTransactionById(transactionId: Long): Transaction?
}
