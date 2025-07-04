package com.williamv.debtmake.ui.entry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.viewmodel.EntryViewModel
import com.williamv.debtmake.viewmodel.EntryViewModelFactory
import com.williamv.debtmake.database.repository.EntryRepository

/**
 * EntryStackScreen 用于展示某个联系人在指定账本下的所有账目记录。
 *
 * @param contactId 选中的联系人 ID
 * @param bookId 当前账本 ID
 * @param repository 当前注入的 EntryRepository
 */
@Composable
fun EntryStackScreen(
    contactId: Long,
    bookId: Long,
    repository: EntryRepository
) {
    // 使用自定义 ViewModelFactory 注入 repository
    val viewModel: EntryViewModel = viewModel(factory = EntryViewModelFactory(repository))

    // 从 ViewModel 获取该联系人在此账本下的所有交易记录（Flow<List<Entry>> 转为 State<List<Entry>>）
    val entries by viewModel.getEntriesForContactInBook(contactId, bookId).collectAsState(initial = emptyList())

    // UI 构建
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Entry Stack") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
        ) {
            if (entries.isEmpty()) {
                Text("No entries available for this contact.")
            } else {
                LazyColumn {
                    items(entries) { entry ->
                        EntryItem(entry = entry)
                    }
                }
            }
        }
    }
}

/**
 * 展示单条 Entry 的样式组件
 */
@Composable
fun EntryItem(entry: Entry) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Amount: ${entry.amount}")
            Text(text = "Description: ${entry.description ?: "No Description"}")
            Text(text = "Date: ${entry.date}")
            Text(text = "Type: ${entry.type}")
        }
    }
}
