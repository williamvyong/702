package com.williamv.debtmake.data.repository

import com.williamv.debtmake.database.dao.EntryDao
import com.williamv.debtmake.model.Entry
import kotlinx.coroutines.flow.Flow

/**
 * Repository 类用于封装所有与账本条目（Entry）相关的数据操作逻辑。
 */
class EntryRepository(private val entryDao: EntryDao) {

    /** 插入条目 */
    suspend fun insertEntry(entry: Entry) {
        entryDao.insertEntry(entry)
    }

    /** 更新条目 */
    suspend fun updateEntry(entry: Entry) {
        entryDao.updateEntry(entry)
    }

    /** 删除条目 */
    suspend fun deleteEntry(entry: Entry) {
        entryDao.deleteEntry(entry)
    }

    /** 根据账本 ID 查询所有条目 */
    fun getEntriesForBook(bookId: Long): Flow<List<Entry>> {
        return entryDao.getEntriesForBook(bookId)
    }

    /** 根据账本和联系人 ID 查询所有条目 */
    fun getEntriesForContactInBook(bookId: Long, contactId: Long): Flow<List<Entry>> {
        return entryDao.getEntriesForContactInBook(bookId, contactId)
    }

    /** 查询所有条目（所有账本） */
    fun getAllEntries(): Flow<List<Entry>> {
        return entryDao.getAllEntries()
    }

    /** 根据 ID 查询条目 */
    suspend fun getEntryById(entryId: Long): Entry? {
        return entryDao.getEntryById(entryId)
    }
}
