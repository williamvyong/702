// 文件路径：app/src/main/java/com/williamv.debtmake/ui/entry/EntryStackScreen.kt
package com.williamv.debtmake.ui.entry

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.williamv.debtmake.viewmodel.EntryViewModel
import com.williamv.debtmake.viewmodel.ContactViewModel

/**
 * EntryStackScreen
 * 展示某个联系人账目流水的动态变动（流水账堆栈）
 *
 * @param bookId 当前账本ID
 * @param contactId 当前联系人ID
 * @param entryViewModel 账目ViewModel
 * @param contactViewModel 联系人ViewModel（如需展示联系人信息）
 * @param onBack 返回回调
 */
@ExperimentalMaterial3Api
@Composable
fun EntryStackScreen(
    bookId: Long,
    contactId: Long,
    entryViewModel: EntryViewModel,
    contactViewModel: ContactViewModel? = null, // 可选，如不需要可删去
    onBack: () -> Unit = {}
) {
    // 拉取流水数据
    val entries by remember { mutableStateOf(entryViewModel.getEntriesForContactInBook(bookId, contactId)) }
    // 可选：拉取联系人信息
    val contact = contactViewModel?.getContactById(contactId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(contact?.name ?: "账目流水") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            if (entries.isEmpty()) {
                Text(
                    "暂无账目流水",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                )
            }
            entries.forEach { entry ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("类型: ${entry.type}")
                        Text("金额: ${entry.amount}")
                        if (!entry.description.isNullOrEmpty())
                            Text("备注: ${entry.description}")
                        Text("时间: ${entry.createdAt}")
                    }
                }
            }
        }
    }
}
