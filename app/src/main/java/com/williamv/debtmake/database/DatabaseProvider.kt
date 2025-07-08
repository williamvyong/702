package com.williamv.debtmake.database

import android.content.Context
import androidx.room.Room

/**
 * DatabaseProvider 提供全局唯一 AppDatabase 实例。
 */
object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "debtmake_database" // ✅ 数据库文件名
            )
                .fallbackToDestructiveMigration() // ❗开发期开启自动清除旧 schema
                .build()
            INSTANCE = instance
            instance
        }
    }
}
