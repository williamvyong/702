package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamv.debtmake.data.repository.ContactRepository
import com.williamv.debtmake.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ContactViewModel 管理联系人列表的状态和数据库交互逻辑
 * - 支持根据 bookId 获取联系人列表
 * - 支持新增、更新、删除联系人
 */
class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    // 当前联系人列表的状态
    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    // 加载某个账本的所有联系人
    fun loadContactsForBook(bookId: Long) {
        viewModelScope.launch {
            repository.getContactsForBook(bookId).collect { contactList ->
                _contacts.value = contactList
            }
        }
    }

    // 插入联系人
    fun insertContact(contact: Contact) {
        viewModelScope.launch {
            repository.insertContact(contact)
            loadContactsForBook(contact.bookId)
        }
    }

    // 更新联系人
    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            repository.updateContact(contact)
            loadContactsForBook(contact.bookId)
        }
    }

    // 删除联系人
    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            repository.deleteContact(contact.id)
            loadContactsForBook(contact.bookId)
        }
    }

    // 获取指定 ID 的联系人（用于编辑）
    suspend fun getContactById(contactId: Long): Contact? {
        return repository.getContactById(contactId)
    }
}
