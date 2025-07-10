package com.williamv.debtmake.ui.contact

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * 删除联系人确认弹窗
 * @param visible 控制弹窗显示
 * @param contactName 显示要删除的联系人姓名
 * @param onConfirm 确认删除回调
 * @param onDismiss 取消/关闭弹窗回调
 */
@Composable
fun DeleteContactDialog(
    visible: Boolean,
    contactName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Delete Contact") },
            text = {
                Text("Are you sure you want to delete \"$contactName\"? This cannot be undone.")
            },
            confirmButton = {
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                ) {
                    Text("Delete", color = MaterialTheme.colors.onError)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}
