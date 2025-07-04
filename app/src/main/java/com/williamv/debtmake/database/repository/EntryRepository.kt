package com.williamv.debtmake.database.repository

import com.williamv.debtmake.database.dao.EntryDao
import com.williamv.debtmake.model.Entry
import kotlinx.coroutines.flow.Flow

class EntryRepository(private val entryDao: EntryDao) {

    // 获取某个账本下的所有 Entry 记录（Flow，用于实时监听）
    fun getAllEntriesForBook(bookId: Long): Flow<List<Entry>> {
        return entryDao.getAllEntriesForBook(bookId)
    }

    // 获取某个账本下特定联系人的 Entry 记录（用于 EntryStack 页面）
    fun getEntriesForContactInBook(bookId: Long, contactId: Long): Flow<List<Entry>> {
        return entryDao.getEntriesForContactInBook(bookId, contactId)
    }

    // 插入一条新的 Entry 记录（例如添加新交易）
    suspend fun insertEntry(entry: Entry) {
        entryDao.insert(entry)
    }

    // 更新一条 Entry（例如修改已收金额或标记为已结清）
    suspend fun updateEntry(entry: Entry) {
        entryDao.update(entry)
    }

    // 根据 ID 删除一条 Entry 记录
    suspend fun deleteEntry(entry: Entry) {
        entryDao.delete(entry)
    }
}
