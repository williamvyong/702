package com.williamv.debtmake.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.williamv.debtmake.model.Entry

/**
 * 账目表（Entry）DAO
 */
@Dao
interface EntryDao {
    @Query("SELECT * FROM entries WHERE bookId = :bookId ORDER BY createdAt DESC")
    fun getEntriesForBook(bookId: Long): List<Entry>

    @Query("SELECT * FROM entries WHERE contactId = :contactId AND bookId = :bookId ORDER BY createdAt DESC")
    fun getEntriesForContact(bookId: Long, contactId: Long): List<Entry>

    @Query("SELECT * FROM entries WHERE id = :entryId")
    fun getEntryById(entryId: Long): Entry?

    @Query("SELECT * FROM entries ORDER BY createdAt DESC")
    fun getAllEntries(): List<Entry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEntry(entry: Entry): Long

    @Update
    fun updateEntry(entry: Entry)

    @Delete
    fun deleteEntry(entry: Entry)
}
