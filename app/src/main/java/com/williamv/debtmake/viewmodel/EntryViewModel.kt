package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamv.debtmake.data.repository.EntryRepository
import com.williamv.debtmake.model.Entry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * EntryViewModel 用于 UI 层与 EntryRepository 的交互
 */
class EntryViewModel(private val repository: EntryRepository) : ViewModel() {

    // 当前选中的账本 ID
    private val _selectedBookId = MutableStateFlow<Long?>(null)
    val selectedBookId: StateFlow<Long?> = _selectedBookId.asStateFlow()

    // 当前选中的联系人 ID（用于过滤）
    private val _selectedContactId = MutableStateFlow<Long?>(null)
    val selectedContactId: StateFlow<Long?> = _selectedContactId.asStateFlow()

    // 当前账本下的所有条目（可被 UI 层观察）
    val entries: StateFlow<List<Entry>> = selectedBookId
        .filterNotNull()
        .flatMapLatest { bookId ->
            repository.getEntriesForBook(bookId)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * 设置当前账本 ID
     */
    fun setSelectedBookId(bookId: Long) {
        _selectedBookId.value = bookId
    }

    /**
     * 设置当前联系人 ID
     */
    fun setSelectedContactId(contactId: Long) {
        _selectedContactId.value = contactId
    }

    /**
     * 获取指定联系人在账本下的 Entry（用于详情页）
     */
    fun getEntriesForContact(bookId: Long, contactId: Long): Flow<List<Entry>> {
        return repository.getEntriesForContact(bookId, contactId)
    }

    /**
     * 插入新条目
     */
    fun insertEntry(entry: Entry) = viewModelScope.launch {
        repository.insertEntry(entry)
    }

    /**
     * 更新已有条目
     */
    fun updateEntry(entry: Entry) = viewModelScope.launch {
        repository.updateEntry(entry)
    }

    /**
     * 删除条目
     */
    fun deleteEntry(entry: Entry) = viewModelScope.launch {
        repository.deleteEntry(entry)
    }
}
