package com.williamv.debtmake.data.repository

import android.content.Context
import com.williamv.debtmake.database.DatabaseProvider
import com.williamv.debtmake.model.Contact

/**
 * ContactRepository
 * 联系人数据仓库
 */
class ContactRepository(context: Context) {
    private val contactDao = DatabaseProvider.getInstance(context).contactDao()

    fun getContactsForBook(bookId: Long): List<Contact> = contactDao.getContactsForBook(bookId)

    fun getContactById(id: Long): Contact? = contactDao.getContactById(id)

    fun insertContact(contact: Contact): Long = contactDao.insertContact(contact)

    fun updateContact(contact: Contact) = contactDao.updateContact(contact)

    fun deleteContact(contactId: Long) {
        val contact = contactDao.getContactById(contactId)
        contact?.let { contactDao.deleteContact(it) }
    }
}
