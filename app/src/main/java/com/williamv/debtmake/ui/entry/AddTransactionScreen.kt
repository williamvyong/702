// 文件路径：app/src/main/java/com/williamv/debtmake/ui/entry/AddTransactionScreen.kt
package com.williamv.debtmake.ui.entry

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.williamv.debtmake.viewmodel.EntryViewModel

/**
 * 添加账目页面
 * @param bookId 当前账本ID
 * @param contactId 当前联系人ID
 * @param entryViewModel 账目ViewModel
 * @param onSaved 成功保存后回调
 * @param onBack 返回回调
 */
@Composable
fun AddTransactionScreen(
    bookId: Long,
    contactId: Long,
    entryViewModel: EntryViewModel,
    onSaved: () -> Unit,
    onBack: () -> Unit
) {
    var amount by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var type by remember { mutableStateOf("collect") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Transaction") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // 账目类型切换
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = { type = "collect" },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (type == "collect") MaterialTheme.colors.primary else MaterialTheme.colors.surface
                    ),
                    modifier = Modifier.weight(1f)
                ) { Text("Collect") }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { type = "payout" },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (type == "payout") MaterialTheme.colors.primary else MaterialTheme.colors.surface
                    ),
                    modifier = Modifier.weight(1f)
                ) { Text("Payout") }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // 金额输入
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount (RM)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 描述输入
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 确认添加
            Button(
                onClick = {
                    val amt = amount.text.toDoubleOrNull() ?: 0.0
                    entryViewModel.insertEntry(
                        com.williamv.debtmake.model.Entry(
                            id = 0L,
                            bookId = bookId,
                            contactId = contactId,
                            amount = amt,
                            type = type,
                            description = description.text,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                    onSaved()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = amount.text.isNotBlank() && amount.text.toDoubleOrNull() != null
            ) {
                Text("OK")
            }
        }
    }
}
