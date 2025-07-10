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
 * TransactionViewModel：交易记录的 UI 状态和逻辑桥梁
 */
class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {

    // 当前账本下所有交易
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    /** 加载账本下所有交易 */
    fun loadTransactionsForBook(bookId: Long) {
        viewModelScope.launch {
            repository.getTransactionsForBook(bookId).collect { list ->
                _transactions.value = list
            }
        }
    }

    /** 加载账本下某联系人的所有交易 */
    fun loadTransactionsForContact(bookId: Long, contactId: Long) {
        viewModelScope.launch {
            repository.getTransactionsForContact(bookId, contactId).collect { list ->
                _transactions.value = list
            }
        }
    }

    /** 新增交易 */
    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.insertTransaction(transaction)
            loadTransactionsForBook(transaction.bookId)
        }
    }

    /** 更新交易 */
    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.updateTransaction(transaction)
            loadTransactionsForBook(transaction.bookId)
        }
    }

    /** 删除交易（传主键ID） */
    fun deleteTransaction(transactionId: Long, bookId: Long) {
        viewModelScope.launch {
            repository.deleteTransaction(transactionId)
            loadTransactionsForBook(bookId)
        }
    }

    /** 获取单条交易（可空类型） */
    suspend fun getTransactionById(transactionId: Long): Transaction? {
        return repository.getTransactionById(transactionId)
    }
}
