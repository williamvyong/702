package com.williamv.debtmake.data.repository

import android.content.Context
import com.williamv.debtmake.database.DatabaseProvider
import com.williamv.debtmake.model.Transaction

/**
 * TransactionRepository
 * 账本扩展交易仓库
 */
class TransactionRepository(context: Context) {
    private val transactionDao = DatabaseProvider.getInstance(context).transactionDao()

    fun getTransactionsForBook(bookId: Long): List<Transaction> = transactionDao.getTransactionsForBook(bookId)

    fun getTransactionsForContact(bookId: Long, contactId: Long): List<Transaction> =
        transactionDao.getTransactionsForContact(bookId, contactId)

    fun getTransactionById(id: Long): Transaction? = transactionDao.getTransactionById(id)

    fun insertTransaction(transaction: Transaction): Long = transactionDao.insertTransaction(transaction)

    fun updateTransaction(transaction: Transaction) = transactionDao.updateTransaction(transaction)

    fun deleteTransaction(transactionId: Long) {
        val transaction = transactionDao.getTransactionById(transactionId)
        transaction?.let { transactionDao.deleteTransaction(it) }
    }
}
