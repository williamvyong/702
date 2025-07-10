package com.williamv.debtmake.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Transaction 实体类，代表一笔账目/交易
 */
@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,   // 主键ID
    val bookId: Long,               // 所属账本ID
    val contactId: Long,            // 所属联系人ID
    val amount: Double,             // 金额
    val description: String = "",   // 描述
    val isLend: Boolean,            // true: 欠我（Their Stack），false: 我欠（My Stack）
    val paidAmount: Double = 0.0,   // 已还金额
    val timestamp: Long             // 时间戳
)
