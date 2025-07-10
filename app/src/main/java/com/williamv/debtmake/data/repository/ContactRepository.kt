package com.williamv.debtmake.data.repository

import com.williamv.debtmake.database.dao.ContactDao
import com.williamv.debtmake.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * ContactRepository 用于联系人数据的数据库封装
 */
class ContactRepository(private val contactDao: ContactDao) {

    /** 获取指定账本下所有联系人 */
    fun getContactsForBook(bookId: Long): Flow<List<Contact>> = contactDao.getContactsForBook(bookId)

    /** 获取所有联系人 */
    fun getAllContacts(): Flow<List<Contact>> = contactDao.getAllContacts()

    /** 插入联系人，返回主键ID */
    suspend fun insertContact(contact: Contact): Long = contactDao.insertContact(contact)

    /** 更新联系人 */
    suspend fun updateContact(contact: Contact) = contactDao.updateContact(contact)

    /** 删除联系人（根据主键ID） */
    suspend fun deleteContact(contactId: Long) = contactDao.deleteContactById(contactId)

    /** 获取单个联系人（可空） */
    suspend fun getContactById(contactId: Long): Contact? = contactDao.getContactById(contactId)
}
