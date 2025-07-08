package com.williamv.debtmake.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Contact 数据模型，用于存储联系人信息
 * 每个联系人属于一个特定账本（通过 bookId 关联）
 */
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,                 // 主键，自增
    val bookId: Long,                  // 所属账本的 ID（外键，逻辑关联）
    val name: String,                  // 联系人姓名
    val phoneNumber: String? = null,   // 可选的电话号码
    val imageUri: String? = null       // 可选的联系人头像（本地路径或 URI）
)
