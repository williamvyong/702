package com.williamv.debtmake.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * PaidOff 筛选条（Active/Paid off 切换）
 * @param selected 当前选中，0=Active, 1=Paid off
 * @param onSelected 切换回调
 */
@Composable
fun PaidOffFilterBar(
    selected: Int,
    onSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3E3E3))
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        FilterButton("Active", selected == 0) { onSelected(0) }
        Spacer(modifier = Modifier.width(12.dp))
        FilterButton("Paid off", selected == 1) { onSelected(1) }
    }
}

@Composable
private fun FilterButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected) Color(0xFF1976D2) else Color.White,
            contentColor = if (selected) Color.White else Color(0xFF1976D2)
        ),
        modifier = Modifier
            .defaultMinSize(minHeight = 36.dp)
            .weight(1f)
    ) {
        Text(text)
    }
}
