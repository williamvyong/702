package com.williamv.debtmake.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Book 实体类，代表一个账本（分组/账本）
 */
@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L, // 主键ID，自增
    val name: String,                   // 账本名称
    val description: String = "",       // 账本描述
    val iconUri: String? = null,        // 账本图标
    val createdAt: Long = System.currentTimeMillis(), // 创建时间
    val updatedAt: Long = System.currentTimeMillis()  // 更新时间
)
