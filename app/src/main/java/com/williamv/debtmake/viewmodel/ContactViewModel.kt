package com.williamv.debtmake.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.williamv.debtmake.data.repository.ContactRepository
import com.williamv.debtmake.model.Contact

/**
 * ContactViewModel
 * 管理 Contact 数据逻辑
 */
class ContactViewModel(context: Context) : ViewModel() {
    private val contactRepository = ContactRepository(context)

    fun getContactsForBook(bookId: Long): List<Contact> = contactRepository.getContactsForBook(bookId)

    fun getContactById(id: Long): Contact? = contactRepository.getContactById(id)

    fun insertContact(contact: Contact): Long = contactRepository.insertContact(contact)

    fun updateContact(contact: Contact) = contactRepository.updateContact(contact)

    fun deleteContact(contactId: Long) = contactRepository.deleteContact(contactId)
}
