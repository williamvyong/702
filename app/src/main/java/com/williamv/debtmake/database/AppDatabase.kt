package com.williamv.debtmake.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.williamv.debtmake.model.Book
import com.williamv.debtmake.model.Contact
import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.model.Transaction
import com.williamv.debtmake.database.dao.BookDao
import com.williamv.debtmake.database.dao.ContactDao
import com.williamv.debtmake.database.dao.EntryDao
import com.williamv.debtmake.database.dao.TransactionDao

/**
 * AppDatabase
 * Room数据库主入口，声明所有Entity和Dao
 */
@Database(
    entities = [Book::class, Contact::class, Entry::class, Transaction::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun contactDao(): ContactDao
    abstract fun entryDao(): EntryDao
    abstract fun transactionDao(): TransactionDao
}
