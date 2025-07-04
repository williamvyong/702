package com.williamv.debtmake.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entry 实体类代表某个联系人在某本账本内的一条记录（借出/借入）
 * 每条 Entry 代表一笔记录在时间线上的流动（不一定是交易本身，也可能是收款记录）
 */
@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L, // 主键，自增

    @ColumnInfo(name = "book_id")
    val bookId: Long, // 所属账本 ID

    @ColumnInfo(name = "contact_id")
    val contactId: Long, // 所属联系人 ID

    @ColumnInfo(name = "amount")
    val amount: Double, // 金额，正数代表 Lend（对方欠我），负数代表 Borrow（我欠对方）

    @ColumnInfo(name = "description")
    val description: String?, // 可选备注内容

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis() // 时间戳，用于排序
)
