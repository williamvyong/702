package com.williamv.debtmake.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Contact 实体类，代表一个账本下的联系人
 */
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,   // 主键ID，自增
    val bookId: Long,                // 所属账本ID
    val name: String,                // 联系人姓名
    val phoneNumber: String? = null, // 联系电话
    val imageUri: String? = null     // 头像URI
)
