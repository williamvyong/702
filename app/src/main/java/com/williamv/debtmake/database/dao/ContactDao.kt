package com.williamv.debtmake.database.dao

import androidx.room.*
import com.williamv.debtmake.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * ContactDao：联系人表操作接口
 */
@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts WHERE bookId = :bookId")
    fun getContactsForBook(bookId: Long): Flow<List<Contact>>

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): Flow<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact): Long

    @Update
    suspend fun updateContact(contact: Contact)

    @Query("DELETE FROM contacts WHERE id = :contactId")
    suspend fun deleteContactById(contactId: Long)

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    suspend fun getContactById(contactId: Long): Contact?
}
