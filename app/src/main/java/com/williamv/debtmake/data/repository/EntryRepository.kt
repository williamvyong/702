package com.williamv.debtmake.data.repository

import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.data.dao.EntryDao

/**
 * 账目流水数据仓库，负责所有账目流转、归档、增删查等业务
 */
class EntryRepository(
    private val entryDao: EntryDao = EntryDao.getInstance()
) {
    /**
     * 查询账本下某联系人的所有未归档流水（Active）
     */
    suspend fun getEntriesForContactInBook(bookId: Long, contactId: Long): List<Entry> {
        return entryDao.getEntriesForContactInBook(bookId, contactId)
    }

    /**
     * 查询账本下某联系人的所有已归档流水（Paid off）
     */
    suspend fun getPaidoffEntriesForContactInBook(bookId: Long, contactId: Long): List<Entry> {
        return entryDao.getPaidoffEntriesForContactInBook(bookId, contactId)
    }

    /**
     * 新增账目流水
     */
    suspend fun insertEntry(entry: Entry) {
        entryDao.insertEntry(entry)
    }

    /**
     * 更新账目
     */
    suspend fun updateEntry(entry: Entry) {
        entryDao.updateEntry(entry)
    }

    /**
     * 删除账目
     */
    suspend fun deleteEntry(entry: Entry) {
        entryDao.deleteEntry(entry)
    }

    /**
     * 归档所有已平仓流水为 Paidoff（isPaidoff 字段置1）
     */
    suspend fun archivePaidoff(bookId: Long, contactId: Long) {
        entryDao.archivePaidoff(bookId, contactId)
    }
}
