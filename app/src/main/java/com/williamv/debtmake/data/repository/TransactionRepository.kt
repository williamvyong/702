package com.williamv.debtmake.data.repository

import com.williamv.debtmake.database.dao.TransactionDao
import com.williamv.debtmake.model.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * TransactionRepository 是用于操作交易数据表（Transaction）的中间层，
 * 负责将 ViewModel 与 DAO 分离，便于维护与测试。
 */
class TransactionRepository(private val transactionDao: TransactionDao) {

    /**
     * 获取某个账本下的所有交易数据
     * @param bookId Long - 指定账本的ID
     * @return Flow<List<Transaction>> - 响应式交易列表
     */
    fun getTransactionsForBook(bookId: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsForBook(bookId)
    }

    /**
     * 根据联系人 ID 获取该联系人的所有交易（可用于详情页）
     */
    fun getTransactionsForContact(bookId: Long, contactId: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsForContact(bookId, contactId)
    }

    /**
     * 插入一笔交易记录
     */
    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    /**
     * 删除一笔交易记录
     */
    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }

    /**
     * 更新一笔交易记录
     */
    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }
}
