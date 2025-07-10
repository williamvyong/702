package com.williamv.debtmake.ui.entry

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.viewmodel.EntryViewModel

/**
 * 账目详情页面
 * @param entryId 当前账目ID
 * @param onBack 返回按钮
 * @param onEdit 跳转编辑账目页面
 * @param onDelete 删除账目回调（可回退上一页）
 * @param entryViewModel 可注入
 */
@Composable
fun EntryDetailScreen(
    entryId: Long,
    onBack: () -> Unit,
    onEdit: (Entry) -> Unit,
    onDelete: (Entry) -> Unit,
    entryViewModel: EntryViewModel = viewModel()
) {
    var entry by remember { mutableStateOf<Entry?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // 加载账目详情
    LaunchedEffect(entryId) {
        entry = entryViewModel.getEntryById(entryId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Entry Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { entry?.let { onEdit(it) } }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { padding ->
        entry?.let { e ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text("Amount: RM${e.amount}", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Description: ${e.description ?: "-"}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Timestamp: ${e.timestamp}")
                // ... 可扩展更多字段 ...
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    // 删除账目确认弹窗
    entry?.let { e ->
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Entry") },
                text = { Text("Are you sure you want to delete this entry?") },
                confirmButton = {
                    Button(
                        onClick = {
                            onDelete(e)
                            showDeleteDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                    ) { Text("Delete", color = MaterialTheme.colors.onError) }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
