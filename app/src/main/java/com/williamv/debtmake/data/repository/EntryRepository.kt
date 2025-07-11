package com.williamv.debtmake.data.repository

import android.content.Context
import com.williamv.debtmake.database.DatabaseProvider
import com.williamv.debtmake.model.Entry

/**
 * EntryRepository
 * 账目流水数据仓库
 */
class EntryRepository(context: Context) {
    private val entryDao = DatabaseProvider.getInstance(context).entryDao()

    // 查询所有账本下所有流水
    fun getAllEntries(): List<Entry> = entryDao.getAllEntries()

    // 查询某账本下所有流水
    fun getEntriesForBook(bookId: Long): List<Entry> = entryDao.getEntriesForBook(bookId)

    // 查询某账本下某联系人所有流水
    fun getEntriesForContactInBook(bookId: Long, contactId: Long): List<Entry> =
        entryDao.getEntriesForContactInBook(bookId, contactId)

    fun getEntryById(id: Long): Entry? = entryDao.getEntryById(id)

    fun insertEntry(entry: Entry): Long = entryDao.insertEntry(entry)

    fun updateEntry(entry: Entry) = entryDao.updateEntry(entry)

    fun deleteEntry(entryId: Long) {
        val entry = entryDao.getEntryById(entryId)
        entry?.let { entryDao.deleteEntry(it) }
    }
}
