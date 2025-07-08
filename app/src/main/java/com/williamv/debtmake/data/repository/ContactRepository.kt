package com.williamv.debtmake.data.repository

import com.williamv.debtmake.database.dao.ContactDao
import com.williamv.debtmake.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * ContactRepository 用于封装 ContactDao 的数据库操作，供 ViewModel 调用。
 */
class ContactRepository(private val contactDao: ContactDao) {

    /**
     * 获取指定账本的所有联系人
     */
    fun getContactsForBook(bookId: Long): Flow<List<Contact>> {
        return contactDao.getContactsForBook(bookId)
    }

    /**
     * 获取所有联系人（不指定账本）
     */
    fun getAllContacts(): Flow<List<Contact>> {
        return contactDao.getAllContacts()
    }

    /**
     * 插入联系人
     */
    suspend fun insertContact(contact: Contact) {
        contactDao.insertContact(contact)
    }

    /**
     * 更新联系人
     */
    suspend fun updateContact(contact: Contact) {
        contactDao.updateContact(contact)
    }

    /**
     * 删除联系人
     */
    suspend fun deleteContact(contactId: Long) {
        contactDao.deleteContactById(contactId)
    }

    /**
     * 获取单个联系人
     */
    suspend fun getContactById(contactId: Long): Contact? {
        return contactDao.getContactById(contactId)
    }
}
