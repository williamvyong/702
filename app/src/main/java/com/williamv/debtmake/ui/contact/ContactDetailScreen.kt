// 文件路径: app/src/main/java/com/williamv.debtmake/ui/contact/ContactDetailScreen.kt
package com.williamv.debtmake.ui.contact

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.williamv.debtmake.R
import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.model.Contact
import com.williamv.debtmake.viewmodel.ContactViewModel
import com.williamv.debtmake.viewmodel.EntryViewModel

/**
 * 联系人详情页面，展示联系人基本信息及账目流水
 * @param contactId 联系人ID
 * @param bookId 所属账本ID
 * @param onBack 返回
 */
@Composable
fun ContactDetailScreen(
    contactId: Long,
    bookId: Long,
    onBack: () -> Unit,
    contactViewModel: ContactViewModel = viewModel(),
    entryViewModel: EntryViewModel = viewModel()
) {
    var contact by remember { mutableStateOf<Contact?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedEntry by remember { mutableStateOf<Entry?>(null) }
    var showEntryDetailDialog by remember { mutableStateOf(false) }

    // 拉取联系人详情
    LaunchedEffect(contactId) {
        contact = contactViewModel.getContactById(contactId)
    }

    // 加载该联系人账本下所有流水账目（示例用 EntryViewModel 实现）
    val entries = remember { entryViewModel.getEntriesForContactInBook(bookId, contactId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color(0xFF1976D2),
                contentColor = Color.White
            )
        }
    ) { padding ->
        contact?.let { c ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 头像
                val painter = if (!c.imageUri.isNullOrBlank()) {
                    rememberAsyncImagePainter(model = Uri.parse(c.imageUri))
                } else {
                    painterResource(id = R.drawable.ic_default_avatar)
                }
                Image(
                    painter = painter,
                    contentDescription = "Contact Avatar",
                    modifier = Modifier
                        .size(96.dp)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(text = c.name, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                c.phoneNumber?.takeIf { it.isNotBlank() }?.let {
                    Text(text = "Phone: $it", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Button(
                    onClick = { showDeleteDialog = true },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text("Delete Contact", color = Color.White)
                }

                // 账目流水
                Spacer(modifier = Modifier.height(24.dp))
                Text("All Transactions", style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.height(8.dp))
                if (entries.isEmpty()) {
                    Text("No transactions for this contact.", color = Color.Gray)
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        items(entries) { entry ->
                            EntryListItem(
                                entry = entry,
                                onClick = {
                                    selectedEntry = entry
                                    showEntryDetailDialog = true
                                }
                            )
                        }
                    }
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
    }

    // 删除联系人弹窗
    if (showDeleteDialog && contact != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Contact") },
            text = { Text("Are you sure you want to delete this contact?") },
            confirmButton = {
                Button(
                    onClick = {
                        contactViewModel.deleteContact(contact!!.id, contact!!.bookId)
                        showDeleteDialog = false
                        onBack()
                    }
                ) { Text("Delete", color = Color.White) }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }

    // 账目详情弹窗（如有 EntryDetailDialog 可补充，暂无则可省略）
}

/**
 * 单条账目条目（可复用）
 */
@Composable
fun EntryListItem(
    entry: Entry,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = 3.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Amount: RM${entry.amount}",
                style = MaterialTheme.typography.subtitle1,
                color = if (entry.amount >= 0) Color(0xFF43A047) else Color(0xFFD32F2F)
            )
            entry.description?.takeIf { it.isNotBlank() }?.let {
                Text(it, style = MaterialTheme.typography.body2, color = Color.Gray)
            }
            Text(
                text = "Time: ${entry.timestamp}",
                style = MaterialTheme.typography.caption,
                color = Color.LightGray
            )
        }
    }
}
