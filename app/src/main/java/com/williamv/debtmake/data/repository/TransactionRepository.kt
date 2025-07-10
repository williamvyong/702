package com.williamv.debtmake.data.repository

import com.williamv.debtmake.database.dao.TransactionDao
import com.williamv.debtmake.model.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * TransactionRepository 用于交易记录的数据库封装
 */
class TransactionRepository(private val transactionDao: TransactionDao) {

    /** 插入交易，返回主键ID */
    suspend fun insertTransaction(transaction: Transaction): Long = transactionDao.insertTransaction(transaction)

    /** 更新交易 */
    suspend fun updateTransaction(transaction: Transaction) = transactionDao.updateTransaction(transaction)

    /** 删除交易（根据主键ID） */
    suspend fun deleteTransaction(transactionId: Long) = transactionDao.deleteTransactionById(transactionId)

    /** 获取账本下所有交易 */
    fun getTransactionsForBook(bookId: Long): Flow<List<Transaction>> = transactionDao.getTransactionsForBook(bookId)

    /** 获取账本下指定联系人的所有交易 */
    fun getTransactionsForContact(bookId: Long, contactId: Long): Flow<List<Transaction>> =
        transactionDao.getTransactionsForContact(bookId, contactId)

    /** 获取所有交易 */
    fun getAllTransactions(): Flow<List<Transaction>> = transactionDao.getAllTransactions()

    /** 根据ID获取单条交易（可空） */
    suspend fun getTransactionById(transactionId: Long): Transaction? = transactionDao.getTransactionById(transactionId)
}
