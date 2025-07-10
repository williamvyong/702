package com.williamv.debtmake.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

/**
 * 账目流水实体类
 * @property id 主键
 * @property bookId 所属账本ID
 * @property contactId 所属联系人ID
 * @property amount 金额（正数，收款为collect，付款为payout，partial/overpay均支持）
 * @property type 交易类型："collect" or "payout"
 * @property description 备注
 * @property timestamp 时间戳（建议存ISO格式字符串或Long）
 * @property isPaidoff 是否归档（Paid off，平仓后归档历史账单）
 */
@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "bookId") val bookId: Long,
    @ColumnInfo(name = "contactId") val contactId: Long,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "type") val type: String, // "collect" 或 "payout"
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "timestamp") val timestamp: String? = null,
    @ColumnInfo(name = "isPaidoff") val isPaidoff: Boolean = false
)
