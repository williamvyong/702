package com.williamv.debtmake.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.williamv.debtmake.model.Entry
import kotlinx.coroutines.flow.Flow

/**
 * EntryDao 提供对 entries 表的访问接口
 * 包含插入、删除、更新、查询等基本操作
 */
@Dao
interface EntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: Entry)

    @Delete
    suspend fun deleteEntry(entry: Entry)

    @Update
    suspend fun updateEntry(entry: Entry)

    @Query("SELECT * FROM entries WHERE book_id = :bookId ORDER BY timestamp DESC")
    fun getEntriesForBook(bookId: Long): Flow<List<Entry>>

    @Query("SELECT * FROM entries WHERE contact_id = :contactId AND book_id = :bookId ORDER BY timestamp ASC")
    fun getEntriesForContactInBook(bookId: Long, contactId: Long): Flow<List<Entry>>

    @Query("SELECT * FROM entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<Entry>>

    @Query("SELECT * FROM entries WHERE id = :entryId LIMIT 1")
    suspend fun getEntryById(entryId: Long): Entry?

    @Query("DELETE FROM entries WHERE book_id = :bookId")
    suspend fun deleteEntriesForBook(bookId: Long)
}
