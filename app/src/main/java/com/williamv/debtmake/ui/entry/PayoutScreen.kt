package com.williamv.debtmake.ui.entry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.williamv.debtmake.ui.common.CalculatorInputScreen

/**
 * 付款页面（Payout 页面，支持 calculator 金额输入，全新词汇与完整交互）
 */
@Composable
fun PayoutScreen(
    dueDate: String,
    payoutTotal: Double,
    paid: Double,
    payable: Double,
    onPayout: () -> Unit,
    onBack: () -> Unit
) {
    var note by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var showCalculator by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payout") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") }
                },
                actions = {
                    IconButton(onClick = onPayout) {
                        Icon(Icons.Default.Check, contentDescription = "Confirm", tint = Color.White)
                    }
                },
                backgroundColor = Color(0xFF1976D2),
                contentColor = Color.White
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // 到期日
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Due date", color = Color.Gray)
                Spacer(modifier = Modifier.width(16.dp))
                Text(dueDate, color = Color(0xFF1976D2))
            }
            Spacer(modifier = Modifier.height(16.dp))

            // 统计
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Total payout amount", color = Color(0xFFD32F2F))
                    Text(
                        String.format("%.2f RM", payoutTotal),
                        color = Color(0xFF388E3C),
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Paid", color = Color(0xFF1976D2))
                    Text(
                        String.format("%.2f", paid),
                        color = Color(0xFFD32F2F)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Account payable", color = Color.Gray)
                    Text(
                        String.format("%.2f RM", payable),
                        color = Color(0xFF388E3C)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 金额输入，支持 calculator
            OutlinedTextField(
                value = amount,
                onValueChange = {},
                label = { Text("Amount to payout") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCalculator = true },
                enabled = false,
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Right),
                placeholder = { Text("0") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 备注与附件
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Enter a note") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("Attach a photo (Pro)", color = Color(0xFFFFA000))
            // 这里可以放附件上传按钮等（略）
        }
    }

    // Calculator 金额输入页面
    if (showCalculator) {
        CalculatorInputScreen(
            title = "Payout",
            initialAmount = amount,
            onResult = {
                amount = it
                showCalculator = false
            },
            onBack = { showCalculator = false }
        )
    }
}
