package com.williamv.debtmake.data.repository

import com.williamv.debtmake.database.dao.ContactDao
import com.williamv.debtmake.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * ContactRepository 负责处理与 ContactDao 的交互
 * 提供插入、更新、删除、查询联系人的方法
 */
class ContactRepository(private val contactDao: ContactDao) {

    /**
     * 插入新联系人
     */
    suspend fun insertContact(contact: Contact) {
        contactDao.insertContact(contact)
    }

    /**
     * 更新已有联系人信息
     */
    suspend fun updateContact(contact: Contact) {
        contactDao.updateContact(contact)
    }

    /**
     * 删除联系人
     */
    suspend fun deleteContact(contact: Contact) {
        contactDao.deleteContact(contact)
    }

    /**
     * 查询某账本下所有联系人（Flow 实时监听）
     */
    fun getContactsForBook(bookId: Long): Flow<List<Contact>> {
        return contactDao.getContactsForBook(bookId)
    }

    /**
     * 通过 ID 获取某个联系人
     */
    suspend fun getContactById(contactId: Long): Contact? {
        return contactDao.getContactById(contactId)
    }

    /**
     * 获取所有联系人（如果以后支持 Master Book 模式）
     */
    fun getAllContacts(): Flow<List<Contact>> {
        return contactDao.getAllContacts()
    }
}
