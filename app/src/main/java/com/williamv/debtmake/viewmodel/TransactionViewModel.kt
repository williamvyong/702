// 📂 文件路径: com.williamv.debtmake.viewmodel/TransactionViewModel.kt
package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamv.debtmake.data.repository.TransactionRepository
import com.williamv.debtmake.model.Transaction
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * TransactionViewModel 用于管理交易（Transaction）数据和 UI 状态
 * 注入 TransactionRepository 操作数据库
 */
class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {

    // 所有交易记录，提供 UI 实时更新
    val allTransactions: StateFlow<List<Transaction>> = repository.getAllTransactions()
        .map { it.sortedByDescending { tx -> tx.date } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // 插入新交易
    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.insertTransaction(transaction)
        }
    }

    // 更新交易
    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.updateTransaction(transaction)
        }
    }

    // 删除交易
    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }

    // 获取某账本下的所有交易
    fun getTransactionsForBook(bookId: Long): StateFlow<List<Transaction>> {
        return repository.getTransactionsForBook(bookId)
            .map { it.sortedByDescending { tx -> tx.date } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    // 获取指定联系人在指定账本的交易
    fun getTransactionsForContact(bookId: Long, contactId: Long): StateFlow<List<Transaction>> {
        return repository.getTransactionsForContact(bookId, contactId)
            .map { it.sortedByDescending { tx -> tx.date } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }
}
