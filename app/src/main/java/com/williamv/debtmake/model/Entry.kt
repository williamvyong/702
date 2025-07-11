package com.williamv.debtmake.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entry
 * 账目流水（每一条交易流水）
 */
@Entity(tableName = "entries")  // 👈 注意这里
data class Entry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val bookId: Long,        // 所属账本
    val contactId: Long,     // 所属联系人
    val type: String,        // 类型：Collect/Payout/等
    val amount: Double,      // 金额
    val description: String?,// 备注
    val createdAt: Long,      // 创建时间（时间戳）
    val timestamp: Long
)