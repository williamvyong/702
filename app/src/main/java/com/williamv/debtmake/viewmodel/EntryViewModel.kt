package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.data.repository.EntryRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EntryViewModel(
    private val entryRepository: EntryRepository = EntryRepository()
) : ViewModel() {

    // 活跃账目（未归档）缓存
    private val _entryMap = MutableStateFlow<Map<Pair<Long, Long>, List<Entry>>>(emptyMap())
    fun getEntriesForContactInBook(bookId: Long, contactId: Long): StateFlow<List<Entry>> {
        val key = Pair(bookId, contactId)
        return _entryMap.map { it[key] ?: emptyList() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    }

    // 已归档账目（Paid off）缓存
    private val _paidoffMap = MutableStateFlow<Map<Pair<Long, Long>, List<Entry>>>(emptyMap())
    fun getPaidoffEntriesForContactInBook(bookId: Long, contactId: Long): StateFlow<List<Entry>> {
        val key = Pair(bookId, contactId)
        return _paidoffMap.map { it[key] ?: emptyList() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    }

    // 加载 Active
    fun loadEntries(bookId: Long, contactId: Long) {
        viewModelScope.launch {
            val list = entryRepository.getEntriesForContactInBook(bookId, contactId)
            _entryMap.update { it + (Pair(bookId, contactId) to list) }
        }
    }

    // 加载 Paid off
    fun loadPaidoffEntries(bookId: Long, contactId: Long) {
        viewModelScope.launch {
            val list = entryRepository.getPaidoffEntriesForContactInBook(bookId, contactId)
            _paidoffMap.update { it + (Pair(bookId, contactId) to list) }
        }
    }

    // 其它插入/更新/删除/collectPartial/payoutPartial/checkPaidoff 逻辑同前
    // 每次 insert/delete/update/archivePaidoff 记得重新 loadEntries 和 loadPaidoffEntries

    // 示例插入
    fun insertEntry(entry: Entry) {
        viewModelScope.launch {
            entryRepository.insertEntry(entry)
            loadEntries(entry.bookId, entry.contactId)
            loadPaidoffEntries(entry.bookId, entry.contactId)
        }
    }

    // ... 其它代码同前 ...
}
