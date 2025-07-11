package com.williamv.debtmake.ui.common

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * PaidOffFilterBar
 * Timeline/Active/Paid 筛选排序栏
 *
 * @param selected 当前选中项
 * @param onSelected 选择回调
 */
@Composable
fun PaidOffFilterBar(
    selected: String,
    onSelected: (String) -> Unit
) {
    val items = listOf("All", "Active", "Paid")
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEach { item ->
            FilterChip(
                selected = selected == item,
                onClick = { onSelected(item) },
                label = { Text(item) }
            )
        }
    }
}
