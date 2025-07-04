package com.williamv.debtmake.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.williamv.debtmake.database.dao.BookDao
import com.williamv.debtmake.model.Book

// ✅ App 的数据库类，使用 Room 管理 Book 实体表
@Database(entities = [Book::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // ✅ 单例模式获取数据库实例
        fun getDatabase(context: Context): AppDatabase {
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

// ✅ 提供统一入口以供 MainActivity.kt 调用
fun getAppDatabase(context: Context): AppDatabase {
    return AppDatabase.getDatabase(context)
}
