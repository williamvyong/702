package com.williamv.debtmake.data.repository

import com.williamv.debtmake.database.dao.TransactionDao
import com.williamv.debtmake.model.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * Repository 类用于封装所有与交易（Transaction）相关的数据操作逻辑。
 */
class TransactionRepository(private val transactionDao: TransactionDao) {

    /** 插入一条交易记录 */
    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    /** 更新一条交易记录 */
    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }

    /** 删除一条交易记录 */
    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }

    /** 根据账本 ID 查询所有交易 */
    fun getTransactionsForBook(bookId: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsForBook(bookId)
    }

    /** 根据账本 ID 和联系人 ID 查询交易 */
    fun getTransactionsForContact(bookId: Long, contactId: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsForContact(bookId, contactId)
    }

    /** 查询所有交易（不区分账本） */
    fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions()
    }

    /** 根据交易 ID 查询单条记录 */
    suspend fun getTransactionById(transactionId: Long): Transaction? {
        return transactionDao.getTransactionById(transactionId)
    }
}
