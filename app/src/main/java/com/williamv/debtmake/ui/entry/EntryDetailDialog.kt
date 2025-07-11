package com.williamv.debtmake.ui.entry

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.williamv.debtmake.model.Entry

/**
 * 账目详情/编辑弹窗
 * @param visible 是否显示弹窗
 * @param entry 当前展示/编辑的账目对象
 * @param onDismiss 关闭弹窗回调
 * @param onSave 编辑保存后的回调（带回最新 entry）
 * @param onDelete 删除账目的回调（可选）
 */
@Composable
fun EntryDetailDialog(
    visible: Boolean,
    entry: Entry,
    onDismiss: () -> Unit,
    onSave: (Entry) -> Unit,
    onDelete: (() -> Unit)? = null,
    showDelete: Boolean = false
) {
    var editAmount by remember { mutableStateOf(entry.amount.toString()) }
    var editDesc by remember { mutableStateOf(entry.description ?: "") }

    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Entry Details") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editAmount,
                        onValueChange = { editAmount = it },
                        label = { Text("Amount") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editDesc,
                        onValueChange = { editDesc = it },
                        label = { Text("Description (optional)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Time: ${entry.timestamp}", style = MaterialTheme.typography.caption)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = entry.copy(
                            amount = editAmount.toDoubleOrNull() ?: entry.amount,
                            description = editDesc
                        )
                        onSave(updated)
                        onDismiss()
                    }
                ) { Text("Save") }
            },
            dismissButton = {
                Row {
                    if (showDelete && onDelete != null) {
                        OutlinedButton(
                            onClick = {
                                onDelete()
                                onDismiss()
                            }
                        ) { Text("Delete") }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    OutlinedButton(onClick = onDismiss) { Text("Cancel") }
                }
            }
        )
    }
}
