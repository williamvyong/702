package com.williamv.debtmake.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Contact 表：用于存储联系人信息
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,                // 联系人主键 ID，自动生成
    val fullName: String,             // 联系人姓名
    val phoneNumber: String?,         // 联系人电话（可选）
    val imageUri: String? = null      // 联系人头像 URI（可选，用字符串存储）
)
