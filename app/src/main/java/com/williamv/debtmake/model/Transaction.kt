package com.williamv.debtmake.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Transaction 数据模型，表示某笔账目记录
 * 每条记录属于一个账本（bookId）和一个联系人（contactId）
 */
@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,                 // 主键，自增
    val bookId: Long,                  // 所属账本 ID
    val contactId: Long,               // 所属联系人 ID
    val amount: Double,                // 金额
    val description: String = "",      // 可选描述
    val isLend: Boolean,               // true 表示欠我 (Their Stack)，false 表示我欠 (My Stack)
    val paidAmount: Double = 0.0,      // 已还金额（用于支持部分还款）
    val timestamp: Long                // 时间戳，用于排序与筛选
)
