package com.williamv.debtmake.ui.entry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.williamv.debtmake.model.Entry
import com.williamv.debtmake.ui.common.CalculatorInputScreen

/**
 * 编辑账目页面（Entry Edit，金额输入全用 CalculatorInputScreen）
 */
@Composable
fun EditEntryScreen(
    entry: Entry,
    onEdit: (Entry) -> Unit,
    onBack: () -> Unit
) {
    var amount by remember { mutableStateOf(entry.amount.toString()) }
    var description by remember { mutableStateOf(entry.description ?: "") }
    var showCalculator by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Amount") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val amt = amount.toDoubleOrNull() ?: 0.0
                        onEdit(entry.copy(amount = amt, description = description))
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Confirm", tint = MaterialTheme.colors.onPrimary)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = {},
                label = { Text("Amount") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCalculator = true },
                enabled = false,
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Right),
                placeholder = { Text("0") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (showCalculator) {
        CalculatorInputScreen(
            title = "Edit Amount",
            initialAmount = amount,
            onResult = {
                amount = it
                showCalculator = false
            },
            onBack = { showCalculator = false }
        )
    }
}
