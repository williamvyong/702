package com.williamv.debtmake.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.williamv.debtmake.model.Contact
import kotlinx.coroutines.flow.Flow

// ContactDao：用于访问联系人表 contact 的数据库操作接口
@Dao
interface ContactDao {

    // 获取指定账本下的所有联系人
    @Query("SELECT * FROM contacts WHERE bookId = :bookId")
    fun getContactsForBook(bookId: Long): Flow<List<Contact>>

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): Flow<List<Contact>>

    // 插入新联系人
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertContact(contact: Contact): Long

    // 根据 ID 删除联系人
    @Query("DELETE FROM contacts WHERE id = :contactId")
    suspend fun deleteContactById(contactId: Long)

    // 更新联系人信息
    @Update
    suspend fun updateContact(contact: Contact)

    // 查询单个联系人
    @Query("SELECT * FROM contacts WHERE id = :contactId")
    suspend fun getContactById(contactId: Long): Contact?
}