package com.williamv.debtmake.database.repository

import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.database.dao.EntryDao
import kotlinx.coroutines.flow.Flow

/**
 * EntryRepository 类用于封装所有对 Entry 实体的数据库操作，
 * 包括新增、查询、更新、删除等操作。View 层可通过调用此类的方法来访问数据。
 */
class EntryRepository(private val entryDao: EntryDao) {

    /**
     * 获取指定账本的所有条目（支持实时监听 Flow）。
     * @param bookId Long 类型账本 ID
     * @return Flow<List<Entry>> 实时更新的条目列表
     */
    fun getEntriesForBook(bookId: Long): Flow<List<Entry>> {
        return entryDao.getEntriesForBook(bookId)
    }

    /**
     * 插入一个 Entry 条目到数据库中。
     * @param entry 要插入的 Entry 对象
     */
    suspend fun insertEntry(entry: Entry) {
        entryDao.insert(entry)
    }

    /**
     * 更新一个已存在的 Entry。
     * @param entry 要更新的 Entry 对象
     */
    suspend fun updateEntry(entry: Entry) {
        entryDao.update(entry)
    }

    /**
     * 根据 entryId 删除指定条目。
     * @param entryId 要删除的条目 ID
     */
    suspend fun deleteEntryById(entryId: Long) {
        entryDao.deleteEntryById(entryId)
    }

    /**
     * 获取指定联系人在某个账本中的所有 Entry 记录。
     * @param contactId 联系人 ID
     * @param bookId 账本 ID
     * @return Flow<List<Entry>>
     */
    fun getEntriesForContactInBook(contactId: Long, bookId: Long): Flow<List<Entry>> {
        return entryDao.getEntriesForContactInBook(contactId, bookId)
    }

    /**
     * 获取所有 Entry（用于 Master Book）
     */
    fun getAllEntries(): Flow<List<Entry>> {
        return entryDao.getAllEntries()
    }

    /**
     * 通过 ID 获取单个 Entry
     */
    fun getEntryById(entryId: Long): Flow<Entry?> {
        return entryDao.getEntryById(entryId)
    }
}
