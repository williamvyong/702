package com.williamv.debtmake.ui.entry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.model.EntryType
import com.williamv.debtmake.repository.EntryRepository
import kotlinx.coroutines.launch

/**
 * EntryListScreen.kt
 * 路径: ui/entry/EntryListScreen.kt
 * 类型: Composable 页面，用于显示某个账本下所有 Entry 的列表。
 * 参数说明：
 * - bookId: 当前账本的 ID，用于加载对应 Entry。
 * - navController: 导航控制器。
 */
@Composable
fun EntryListScreen(
    bookId: Long,
    navController: NavController,
    repository: EntryRepository
) {
    val scope = rememberCoroutineScope()
    var entries by remember { mutableStateOf<List<Entry>>(emptyList()) }

    // 初始加载
    LaunchedEffect(bookId) {
        repository.getAllEntriesForBook(bookId).collect {
            entries = it
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Transactions") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(entries) { entry ->
                EntryListItem(entry = entry)
            }
        }
    }
}

/**
 * 单条 Entry 的展示样式
 */
@Composable
fun EntryListItem(entry: Entry) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (entry.type == EntryType.BORROW) "My Stack: RM${entry.amount}" else "Their Stack: RM${entry.amount}",
                style = MaterialTheme.typography.titleMedium,
                color = if (entry.type == EntryType.BORROW) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Date: ${entry.date}", style = MaterialTheme.typography.bodySmall)
            if (!entry.note.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Note: ${entry.note}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}