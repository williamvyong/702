package com.williamv.debtmake.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Contact
 * 联系人数据模型
 */
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val phoneNumber: String?,
    val avatarUri: String?,
    val bookId: Long // 所属账本ID
)
