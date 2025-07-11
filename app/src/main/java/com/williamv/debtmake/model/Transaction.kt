package com.williamv.debtmake.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Transaction
 * 额外扩展的数据结构（如有特殊业务再补充字段）
 */
@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val bookId: Long,
    val contactId: Long,
    val type: String,        // 类型
    val amount: Double,
    val description: String?,
    val createdAt: Long
    // 你可以自行添加更多字段
)
