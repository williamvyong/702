// ✅ 文件路径：ui/entry/EntryStackScreen.kt
// ✅ 文件用途：展示某个联系人在某账本下的动态流水账，支持 Collect、显示总额等

package com.williamv.debtmake.ui.entry

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.williamv.debtmake.R
import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.viewmodel.EntryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryStackScreen(
    bookId: Long,
    contactId: Long,
    contactName: String,
    contactAvatar: String?,
    onBack: () -> Unit,
    onCollectClicked: (Entry) -> Unit,
    entryViewModel: EntryViewModel
) {
    val scope = rememberCoroutineScope()
    val entries by entryViewModel.getEntriesForContactInBook(bookId, contactId).collectAsState(initial = emptyList())
    val totalAmount = entries.sumOf { it.amount }
    val collectedAmount = entries.sumOf { it.collectedAmount }
    val remainingAmount = totalAmount - collectedAmount

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Entry Stack", fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D47A1),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp)
        ) {
            // 联系人信息块
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                val avatarPainter = if (!contactAvatar.isNullOrBlank()) {
                    rememberAsyncImagePainter(model = Uri.parse(contactAvatar))
                } else {
                    painterResource(id = R.drawable.ic_default_avatar)
                }

                Image(
                    painter = avatarPainter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.LightGray)
                        .padding(4.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(text = contactName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Since: ${entries.firstOrNull()?.date ?: "-"}", fontSize = 14.sp)
                }
            }

            // 金额卡片
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                AmountCard(label = "Total", amount = totalAmount)
                AmountCard(label = "Collected", amount = collectedAmount)
                AmountCard(label = "Remaining", amount = remainingAmount)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Transactions", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(4.dp))

            LazyColumn {
                items(entries) { entry ->
                    EntryListItem(
                        entry = entry,
                        onEdit = { /* 预留编辑逻辑 */ },
                        onDelete = {
                            scope.launch {
                                entryViewModel.deleteEntry(entry)
                            }
                        },
                        onCollect = { onCollectClicked(entry) }
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.AmountCard(label: String, amount: Double) {
    Card(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, fontSize = 14.sp, color = Color.Gray)
            Text("RM %.2f".format(amount), fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
