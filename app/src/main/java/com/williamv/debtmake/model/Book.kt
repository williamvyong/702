package com.williamv.debtmake.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Book
 * 账本数据结构
 */
@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val description: String?,
    val createdAt: Long  // 👈 必须有这个字段，类型必须和 Dao 里用的一致
)
