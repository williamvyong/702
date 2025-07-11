// 文件路径: app/src/main/java/com/williamv.debtmake/ui/entry/EntryListScreen.kt
package com.williamv.debtmake.ui.entry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.viewmodel.EntryViewModel

/**
 * 账目流水总览页面（所有账本/所有联系人）
 * @param entryViewModel 注入账目 ViewModel
 * @param onEntryClick 点击单条账目（可跳转到 EntryStackScreen）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryListScreen(
    entryViewModel: EntryViewModel,
    onEntryClick: (bookId: Long, contactId: Long) -> Unit
) {
    // 注意这里用 remember 防止重组过度
    val entries by remember { mutableStateOf(entryViewModel.getAllEntries()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Transactions") },
            )
        }
    ) { padding ->
        if (entries.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No transactions yet.", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(entries) { entry ->
                    EntryListItem(
                        entry = entry,
                        onClick = {
                            onEntryClick(entry.bookId, entry.contactId)
                        }
                    )
                }
            }
        }
    }
}

/**
 * 单条账目条目
 */
@Composable
fun EntryListItem(entry: Entry, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("类型: ${entry.type}")
                Text("金额: ${entry.amount}")
                Text("时间: ${entry.createdAt}")
            }
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }
    }
}
