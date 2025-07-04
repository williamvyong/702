package com.williamv.debtmake.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.williamv.debtmake.model.Book
import com.williamv.debtmake.model.Contact
import com.williamv.debtmake.model.Transaction
import com.williamv.debtmake.dao.BookDao
import com.williamv.debtmake.dao.ContactDao
import com.williamv.debtmake.dao.TransactionDao

// AppDatabase 是整个应用的 Room 数据库核心类，包含所有表和 DAO 的定义
@Database(
    entities = [Book::class, Contact::class, Transaction::class], // 注册的实体表
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // 提供 DAO 的访问接口
    abstract fun bookDao(): BookDao
    abstract fun contactDao(): ContactDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // 获取数据库单例
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "debtmake_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}