package com.williamv.debtmake.ui.common

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*

/**
 * DatePickerDrawer
 * 底部弹出日期选择器，支持年月日滚动
 *
 * @param initialDate 默认日期
 * @param onDateSelected 日期选择回调
 * @param onDismiss 关闭抽屉
 */
@Composable
fun DatePickerDrawer(
    initialDate: Calendar = Calendar.getInstance(),
    onDateSelected: (Calendar) -> Unit,
    onDismiss: () -> Unit
) {
    // 用于演示，可用 Android DatePickerDialog 或自行实现三轮选择
    var selectedDate by remember { mutableStateOf(initialDate) }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("选择日期", style = MaterialTheme.typography.titleMedium)
            // 简单演示（实际应用可用WheelPicker库替换！）
            OutlinedTextField(
                value = "%04d-%02d-%02d".format(
                    selectedDate.get(Calendar.YEAR),
                    selectedDate.get(Calendar.MONTH) + 1,
                    selectedDate.get(Calendar.DAY_OF_MONTH)
                ),
                onValueChange = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = {
                    selectedDate.add(Calendar.DAY_OF_MONTH, -1)
                }) { Text("- 日") }
                Button(onClick = {
                    selectedDate.add(Calendar.MONTH, -1)
                }) { Text("- 月") }
                Button(onClick = {
                    selectedDate.add(Calendar.YEAR, -1)
                }) { Text("- 年") }
                Button(onClick = {
                    selectedDate.add(Calendar.DAY_OF_MONTH, 1)
                }) { Text("+ 日") }
                Button(onClick = {
                    selectedDate.add(Calendar.MONTH, 1)
                }) { Text("+ 月") }
                Button(onClick = {
                    selectedDate.add(Calendar.YEAR, 1)
                }) { Text("+ 年") }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onDateSelected(selectedDate); onDismiss() }
            ) { Text("确定") }
        }
    }
}
