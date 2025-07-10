package com.williamv.debtmake.ui.entry

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.williamv.debtmake.R
import com.williamv.debtmake.model.Contact
import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.ui.common.CalculatorInputScreen
import com.williamv.debtmake.ui.common.PaidOffFilterBar
import com.williamv.debtmake.viewmodel.EntryViewModel
import com.williamv.debtmake.viewmodel.ContactViewModel
import kotlinx.coroutines.launch

/**
 * 联系人账目流水详情页，支持 PaidOffFilterBar 切换 Active/Paid off
 */
@Composable
fun EntryStackScreen(
    bookId: String,
    contact: Contact,
    onBack: () -> Unit,
    entryViewModel: EntryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    contactViewModel: ContactViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val scope = rememberCoroutineScope()

    var filterIndex by remember { mutableStateOf(0) }
    val entryList by entryViewModel.getEntriesForContactInBook(bookId, contact.id).collectAsState(emptyList())
    val paidoffEntryList by entryViewModel.getPaidoffEntriesForContactInBook(bookId, contact.id).collectAsState(emptyList())
    val currentList = if (filterIndex == 0) entryList else paidoffEntryList

    val collectTotal = currentList.filter { it.type == "collect" }.sumOf { it.amount }
    val payoutTotal = currentList.filter { it.type == "payout" }.sumOf { it.amount }
    val remaining = collectTotal - payoutTotal

    val isPaidOff = (filterIndex == 1) || (remaining == 0.0)

    var showCalculator by remember { mutableStateOf(false) }
    var calculatorTitle by remember { mutableStateOf("Collect") }
    var calculatorOnResult by remember { mutableStateOf<(String) -> Unit>({}) }

    var editingEntry by remember { mutableStateOf<Entry?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Remaining ${"%.2f".format(remaining)} RM") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* 更多操作 */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                },
                backgroundColor = if (remaining > 0) Color(0xFFD32F2F) else Color(0xFF388E3C),
                contentColor = Color.White
            )
        },
        floatingActionButton = {
            if (filterIndex == 0 && !isPaidOff) {
                FloatingActionButton(
                    onClick = {
                        calculatorTitle = if (remaining > 0) "Collect" else "Payout"
                        showCalculator = true
                        calculatorOnResult = { amountStr ->
                            val amt = amountStr.toDoubleOrNull() ?: 0.0
                            if (amt == 0.0) return@FloatingActionButton
                            scope.launch {
                                if (remaining > 0) {
                                    entryViewModel.collectPartial(bookId, contact.id, amt)
                                } else {
                                    entryViewModel.payoutPartial(bookId, contact.id, amt)
                                }
                            }
                        }
                    },
                    backgroundColor = if (remaining > 0) Color(0xFFD32F2F) else Color(0xFF388E3C)
                ) {
                    if (remaining > 0)
                        Icon(Icons.Default.Payments, contentDescription = "Collect")
                    else
                        Icon(Icons.Default.TrendingDown, contentDescription = "Payout")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // PaidOff筛选
            PaidOffFilterBar(
                selected = filterIndex,
                onSelected = { filterIndex = it }
            )

            // 联系人信息卡片
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val painter = if (!contact.imageUri.isNullOrBlank()) {
                    rememberAsyncImagePainter(contact.imageUri)
                } else {
                    painterResource(id = R.drawable.ic_default_avatar)
                }
                Image(
                    painter = painter,
                    contentDescription = "Contact Avatar",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(contact.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    contact.phoneNumber?.let {
                        Text(it, color = Color.Gray, fontSize = 14.sp)
                    }
                    Text("Since: ${contact.createdAt ?: "-"}", color = Color.Gray, fontSize = 12.sp)
                }
            }

            // 统计卡片
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatCard("Total collect amount", collectTotal, Color(0xFFD32F2F))
                StatCard("Total payout amount", payoutTotal, Color(0xFF388E3C))
                StatCard("Remaining", remaining, Color(0xFFFBC02D))
            }

            // 流水明细
            Text(
                if (filterIndex == 0) "Active Transaction Records" else "Paid off Records",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                items(currentList) { entry ->
                    EntryListItem(
                        entry = entry,
                        onEdit = {
                            editingEntry = entry
                            showEditDialog = true
                        },
                        onDelete = {
                            editingEntry = entry
                            showDeleteDialog = true
                        }
                    )
                }
            }

            if (currentList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No records yet.", color = Color.Gray)
                }
            }
        }
    }

    // calculator 金额输入弹窗
    if (showCalculator) {
        CalculatorInputScreen(
            title = calculatorTitle,
            onResult = {
                calculatorOnResult(it)
                showCalculator = false
            },
            onBack = { showCalculator = false }
        )
    }

    // 编辑账目弹窗
    if (showEditDialog && editingEntry != null) {
        EditEntryDialog(
            entry = editingEntry!!,
            onSave = { updated ->
                scope.launch { entryViewModel.updateEntry(updated) }
                showEditDialog = false
            },
            onDismiss = { showEditDialog = false }
        )
    }

    // 删除账目弹窗
    if (showDeleteDialog && editingEntry != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Transaction") },
            text = { Text("Are you sure you want to delete this record?") },
            confirmButton = {
                Button(onClick = {
                    scope.launch { entryViewModel.deleteEntry(editingEntry!!) }
                    showDeleteDialog = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun StatCard(title: String, amount: Double, color: Color) {
    Card(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp, vertical = 6.dp),
        elevation = 3.dp,
        backgroundColor = color.copy(alpha = 0.10f)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 14.sp, color = color, fontWeight = FontWeight.Medium)
            Text(
                text = String.format("%.2f RM", amount),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = color,
                textAlign = TextAlign.Center
            )
        }
    }
}
