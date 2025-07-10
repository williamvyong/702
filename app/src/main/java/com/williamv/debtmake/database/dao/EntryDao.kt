package com.williamv.debtmake.data.dao

import androidx.room.*
import com.williamv.debtmake.model.Entry

/**
 * 账目流水数据库 DAO，支持 Active/Paid off 查询、增删改、归档
 */
@Dao
interface EntryDao {

    /**
     * 查询账本下某联系人的所有未归档流水（Active）
     */
    @Query("SELECT * FROM entries WHERE bookId = :bookId AND contactId = :contactId AND isPaidoff = 0 ORDER BY timestamp ASC")
    suspend fun getEntriesForContactInBook(bookId: Long, contactId: Long): List<Entry>

    /**
     * 查询账本下某联系人的所有已归档流水（Paid off 历史）
     */
    @Query("SELECT * FROM entries WHERE bookId = :bookId AND contactId = :contactId AND isPaidoff = 1 ORDER BY timestamp ASC")
    suspend fun getPaidoffEntriesForContactInBook(bookId: Long, contactId: Long): List<Entry>

    /**
     * 新增账目流水
     */
    @Insert
    suspend fun insertEntry(entry: Entry)

    /**
     * 更新账目
     */
    @Update
    suspend fun updateEntry(entry: Entry)

    /**
     * 删除账目
     */
    @Delete
    suspend fun deleteEntry(entry: Entry)

    /**
     * 归档该账本+联系人下所有流水（isPaidoff=1，Paid off）
     */
    @Query("UPDATE entries SET isPaidoff = 1 WHERE bookId = :bookId AND contactId = :contactId")
    suspend fun archivePaidoff(bookId: Long, contactId: Long)

    // 如果你还有其他 filter/批量/统计等业务，可以继续拓展
}
