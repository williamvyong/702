package com.williamv.debtmake.data.repository

import com.williamv.debtmake.database.dao.EntryDao
import com.williamv.debtmake.model.Entry
import kotlinx.coroutines.flow.Flow

/**
 * EntryRepository 是 Entry 数据的仓库类，封装了所有对 EntryDao 的调用逻辑。
 */
class EntryRepository(private val entryDao: EntryDao) {

    /**
     * 插入新的 Entry 条目
     */
    suspend fun insertEntry(entry: Entry) {
        entryDao.insertEntry(entry)
    }

    /**
     * 更新已有的 Entry
     */
    suspend fun updateEntry(entry: Entry) {
        entryDao.updateEntry(entry)
    }

    /**
     * 删除指定 Entry
     */
    suspend fun deleteEntry(entry: Entry) {
        entryDao.deleteEntry(entry)
    }

    /**
     * 获取某账本下的所有 Entry（使用 Flow 实现实时 UI 数据同步）
     */
    fun getEntriesForBook(bookId: Long): Flow<List<Entry>> {
        return entryDao.getEntriesForBook(bookId)
    }

    /**
     * 获取某联系人在指定账本下的所有 Entry（用于详情页）
     */
    fun getEntriesForContact(bookId: Long, contactId: Long): Flow<List<Entry>> {
        return entryDao.getEntriesForContact(bookId, contactId)
    }

    /**
     * 根据 ID 获取某条 Entry
     */
    suspend fun getEntryById(entryId: Long): Entry? {
        return entryDao.getEntryById(entryId)
    }
}
