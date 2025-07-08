package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamv.debtmake.data.repository.TransactionRepository
import com.williamv.debtmake.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * TransactionViewModel 负责交易记录的 UI 状态管理和数据库操作
 * - 支持按账本或联系人查询交易
 * - 支持添加、更新、删除交易
 * - 支持部分/全部收款逻辑
 */
class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {

    // 当前账本下所有交易
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    // 加载某账本的所有交易
    fun loadTransactionsForBook(bookId: Long) {
        viewModelScope.launch {
            repository.getTransactionsForBook(bookId).collect { list ->
                _transactions.value = list
            }
        }
    }

    // 加载某联系人在某账本下的所有交易
    fun loadTransactionsForContact(bookId: Long, contactId: Long) {
        viewModelScope.launch {
            repository.getTransactionsForContact(bookId, contactId).collect { list ->
                _transactions.value = list
            }
        }
    }

    // 插入交易
    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.insertTransaction(transaction)
            loadTransactionsForBook(transaction.bookId)
        }
    }

    // 更新交易
    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.updateTransaction(transaction)
            loadTransactionsForBook(transaction.bookId)
        }
    }

    // 删除交易
    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
            loadTransactionsForBook(transaction.bookId)
        }
    }

    // 执行收款（更新 paidAmount 字段）
    fun collectAmount(transactionId: Long, collectedAmount: Double) {
        viewModelScope.launch {
            val transaction = repository.getTransactionById(transactionId)
            val newPaidAmount = (transaction.paidAmount ?: 0.0) + collectedAmount
            val updated = transaction.copy(paidAmount = newPaidAmount)
            repository.updateTransaction(updated)
            loadTransactionsForBook(updated.bookId)
        }
    }

    // 获取指定 ID 的交易（用于 Collect 页面）
    suspend fun getTransactionById(transactionId: Long): Transaction? {
        return repository.getTransactionById(transactionId)
    }
}
