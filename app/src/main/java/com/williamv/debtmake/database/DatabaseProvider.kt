package com.williamv.debtmake.database

import android.content.Context
import androidx.room.Room

/**
 * DatabaseProvider
 * 用于全局单例获取AppDatabase对象
 */
object DatabaseProvider {
    @Volatile
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "debtmate_db"
            ).build().also { instance = it }
        }
    }
}
