package com.williamv.debtmake.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Transaction 表：用于存储账本中的每一笔交易记录
@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,               // 交易记录主键 ID，自动生成
    val bookId: Long,                // 所属账本 ID
    val contactId: Long,             // 所属联系人 ID
    val type: String,                // 类型：例如 "LEND" 或 "BORROW"
    val amount: Double,              // 交易金额（保留 2 位小数）
    val description: String?,        // 交易备注（可选）
    val date: Long,                  // 交易时间（时间戳，用 Long 存）
    val paidAmount: Double = 0.0     // 已支付金额（用于部分还款逻辑，默认 0.0）
)
