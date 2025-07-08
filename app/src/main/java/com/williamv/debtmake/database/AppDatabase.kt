package com.williamv.debtmake.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.williamv.debtmake.database.dao.BookDao
import com.williamv.debtmake.database.dao.ContactDao
import com.williamv.debtmake.database.dao.EntryDao
import com.williamv.debtmake.database.dao.TransactionDao
import com.williamv.debtmake.model.Book
import com.williamv.debtmake.model.Contact
import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.model.Transaction

/**
 * AppDatabase 是主数据库定义，使用 Room 进行数据持久化。
 * 包含 4 个实体：Book, Contact, Transaction, Entry
 * 提供各自的 DAO 接口。
 */
@Database(
    entities = [Book::class, Contact::class, Transaction::class, Entry::class],
    version = 1, // ✅ 请确保 version 与未来 migration 同步
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun contactDao(): ContactDao
    abstract fun transactionDao(): TransactionDao
    abstract fun entryDao(): EntryDao
}
