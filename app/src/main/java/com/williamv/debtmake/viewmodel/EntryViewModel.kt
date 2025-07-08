package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamv.debtmake.data.repository.EntryRepository
import com.williamv.debtmake.model.Entry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * EntryViewModel 负责管理每个账本下与联系人的交易堆栈（分账记录）
 * 支持获取、插入、更新、删除、收款操作
 */
class EntryViewModel(private val repository: EntryRepository) : ViewModel() {

    // 当前账本和联系人的 Entry 列表状态
    private val _entries = MutableStateFlow<List<Entry>>(emptyList())
    val entries: StateFlow<List<Entry>> = _entries.asStateFlow()

    // 获取某个账本中某个联系人的所有 entry 记录
    fun loadEntries(bookId: Long, contactId: Long) {
        viewModelScope.launch {
            repository.getEntriesForContactInBook(bookId, contactId).collect { list ->
                _entries.value = list
            }
        }
    }

    // 插入新分账记录
    fun insertEntry(entry: Entry) {
        viewModelScope.launch {
            repository.insertEntry(entry)
            loadEntries(entry.bookId, entry.contactId)
        }
    }

    // 更新 entry（例如备注或金额）
    fun updateEntry(entry: Entry) {
        viewModelScope.launch {
            repository.updateEntry(entry)
            loadEntries(entry.bookId, entry.contactId)
        }
    }

    // 删除某条 entry
    fun deleteEntry(entry: Entry) {
        viewModelScope.launch {
            repository.deleteEntry(entry)
            loadEntries(entry.bookId, entry.contactId)
        }
    }

    // 提供直接获取 Flow 的方法，便于界面 collectAsState
    fun getEntriesForContactInBook(bookId: Long, contactId: Long) =
        repository.getEntriesForContactInBook(bookId, contactId)

    // 单独获取 entry
    suspend fun getEntryById(entryId: Long): Entry? {
        return repository.getEntryById(entryId)
    }
}
