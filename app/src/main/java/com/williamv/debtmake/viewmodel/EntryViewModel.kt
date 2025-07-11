package com.williamv.debtmake.viewmodel

import androidx.lifecycle.ViewModel
import com.williamv.debtmake.data.repository.EntryRepository
import com.williamv.debtmake.model.Entry

/**
 * EntryViewModel
 * 管理 Entry 数据逻辑
 */
class EntryViewModel(context: EntryRepository) : ViewModel() {
    private val entryRepository = EntryRepository(context)

    fun getEntriesForBook(bookId: Long): List<Entry> = entryRepository.getEntriesForBook(bookId)

    fun getEntriesForContactInBook(bookId: Long, contactId: Long): List<Entry> =
        entryRepository.getEntriesForContactInBook(bookId, contactId)

    fun getEntryById(id: Long): Entry? = entryRepository.getEntryById(id)

    fun insertEntry(entry: Entry): Long = entryRepository.insertEntry(entry)

    fun updateEntry(entry: Entry) = entryRepository.updateEntry(entry)

    fun deleteEntry(entryId: Long) = entryRepository.deleteEntry(entryId)
}
