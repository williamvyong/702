package com.williamv.debtmake.ui.entry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.viewmodel.EntryViewModel

/**
 * 账本流水账目主列表页面
 * @param bookId 当前账本ID
 * @param onBack 返回按钮
 * @param onEntryClick 点击单条账目
 * @param onAddEntry 新增账目按钮
 * @param entryViewModel 可注入
 */
@Composable
fun EntryListScreen(
    bookId: Long,
    onBack: () -> Unit,
    onEntryClick: (Entry) -> Unit,
    onAddEntry: () -> Unit,
    entryViewModel: EntryViewModel = viewModel()
) {
    // 账目列表
    val entries by entryViewModel.entries.collectAsState()

    // 页面启动时加载账目列表
    LaunchedEffect(bookId) {
        entryViewModel.loadEntries(bookId = bookId, contactId = -1L) // -1L 代表所有联系人
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Transactions") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onAddEntry) {
                        Icon(Icons.Default.Add, contentDescription = "Add Entry")
                    }
                }
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
                Text("No transactions yet.", color = MaterialTheme.colors.onSurface.copy(alpha = 0.4f))
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
                        onClick = { onEntryClick(entry) }
                    )
                }
            }
        }
    }
}

/**
 * 单条账目流水条目
 */
@Composable
fun EntryListItem(
    entry: Entry,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() },
        elevation = 3.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Amount: RM${entry.amount}",
                style = MaterialTheme.typography.subtitle1,
                color = if (entry.amount >= 0) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
            )
            entry.description?.takeIf { it.isNotBlank() }?.let {
                Text(it, style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f))
            }
            Text(
                text = "Time: ${entry.timestamp}",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
