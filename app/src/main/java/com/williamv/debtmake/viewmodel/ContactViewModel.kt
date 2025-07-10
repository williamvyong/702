package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamv.debtmake.model.Contact
import com.williamv.debtmake.data.repository.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactViewModel(
    private val contactRepository: ContactRepository = ContactRepository()
) : ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts

    private val _recentContacts = MutableStateFlow<List<Contact>>(emptyList())
    val recentContacts: StateFlow<List<Contact>> = _recentContacts

    // 内存维护最近联系人 id 列表（可用数据库实现持久化）
    private val recentContactMap = mutableMapOf<Long, MutableList<Long>>() // bookId -> contactIdList

    fun loadContactsForBook(bookId: Long) {
        viewModelScope.launch {
            _contacts.value = contactRepository.getContactsForBook(bookId)
        }
    }

    fun loadRecentContacts(bookId: Long) {
        val ids = recentContactMap[bookId]?.toList() ?: emptyList()
        val all = _contacts.value
        _recentContacts.value = ids.mapNotNull { id -> all.find { it.id == id } }
    }

    fun addRecentContact(bookId: Long, contact: Contact) {
        val list = recentContactMap.getOrPut(bookId) { mutableListOf() }
        list.removeAll { it == contact.id }
        list.add(0, contact.id)
        if (list.size > 10) list.removeLast()
        loadRecentContacts(bookId) // 重新刷新recentContacts
    }

    // 其它如插入、编辑、删除联系人逻辑略...
}
