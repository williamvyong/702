package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.williamv.debtmake.data.repository.ContactRepository
import com.williamv.debtmake.model.Contact
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ContactViewModel 提供联系人数据给 UI 层
 */
class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    // 所有联系人（会随数据库变化自动更新）
    val allContacts: StateFlow<List<Contact>> = repository.getAllContacts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * 插入新联系人
     */
    fun insertContact(contact: Contact) = viewModelScope.launch {
        repository.insertContact(contact)
    }

    /**
     * 更新联系人信息
     */
    fun updateContact(contact: Contact) = viewModelScope.launch {
        repository.updateContact(contact)
    }

    /**
     * 删除联系人
     */
    fun deleteContact(contact: Contact) = viewModelScope.launch {
        repository.deleteContact(contact)
    }

    /**
     * 根据 ID 获取联系人
     */
    fun getContactById(id: Long): Flow<Contact?> {
        return repository.getContactById(id)
    }
}
