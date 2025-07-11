package com.williamv.debtmake.ui.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.williamv.debtmake.viewmodel.EntryViewModel
import com.williamv.debtmake.viewmodel.ContactViewModel

private val Icons.Filled.Remove: Any

/**
 * BookDetailScreen
 * 展示某本账本的所有联系人、流水统计等
 *
 * @param bookId 当前账本的ID
 * @param entryViewModel 账目流水 ViewModel
 * @param contactViewModel 联系人 ViewModel
 * @param onBack 返回按钮回调
 * @param onAddCollect 新增Collect账目回调
 * @param onAddPayout 新增Payout账目回调
 * @param onSelectContact 跳转选择联系人回调
 */
@ExperimentalMaterial3Api
@Composable
fun BookDetailScreen(
    bookId: Long,
    entryViewModel: EntryViewModel,
    contactViewModel: ContactViewModel,
    onBack: () -> Unit = {},
    onAddCollect: () -> Unit = {},
    onAddPayout: () -> Unit = {},
    onSelectContact: () -> Unit = {}
) {
    // 通过 ViewModel 拉取数据
    val contacts by remember { mutableStateOf(contactViewModel.getContactsForBook(bookId)) }
    val entries by remember { mutableStateOf(entryViewModel.getEntriesForBook(bookId)) }
    // TODO: 账本名称应通过 ViewModel 查询
    val bookName = "Book #$bookId"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(bookName) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onAddCollect) {
                        Icon(Icons.Default.Add, contentDescription = "Add Collect")
                    }
                    IconButton(onClick = onAddPayout) {
                        Icon(Icons.Default.Remove, contentDescription = "Add Payout",

                        )
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
            // 账本统计数据
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("联系人数: ${contacts.size}")
                Text("流水数: ${entries.size}")
            }
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            // 联系人列表
            Text(
                "联系人列表",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
            contacts.forEach { contact ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { onSelectContact() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 可扩展显示头像等
                    Text(contact.name, modifier = Modifier.weight(1f))
                    Text(contact.phoneNumber ?: "", modifier = Modifier.padding(start = 16.dp))
                }
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            }
        }
    }
}