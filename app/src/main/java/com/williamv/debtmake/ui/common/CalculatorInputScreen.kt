package com.williamv.debtmake.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorInputScreen(
    title: String,
    initialAmount: String = "",
    mainColor: Color = MaterialTheme.colors.primary,
    onResult: (String) -> Unit,
    onBack: () -> Unit,
) {
    var input by remember { mutableStateOf(initialAmount) }
    var error by remember { mutableStateOf<String?>(null) }
    var displayResult by remember { mutableStateOf(initialAmount) }

    Surface(
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        elevation = 12.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(430.dp)
            .align(Alignment.BottomCenter)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            // 顶部bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(mainColor)
                    .padding(horizontal = 8.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            // 金额实时显示区
            Text(
                text = displayResult.ifBlank { "0" },
                color = mainColor,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp),
                textAlign = TextAlign.End
            )
            if (error != null) {
                Text(
                    text = error ?: "",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 22.dp, top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Calculator 按键区
            CalculatorPadCustom(
                onKey = { key ->
                    when (key) {
                        "C" -> {
                            input = ""
                            displayResult = ""
                            error = null
                        }
                        "⌫" -> {
                            if (input.isNotEmpty()) input = input.dropLast(1)
                        }
                        "=" -> {
                            try {
                                val result = CalculatorUtil.evaluate(input)
                                displayResult = result
                                error = null
                            } catch (e: Exception) {
                                error = "Invalid"
                            }
                        }
                        else -> {
                            input += key
                        }
                    }
                    if (key != "=") {
                        try {
                            val result = CalculatorUtil.evaluate(input)
                            displayResult = result
                            error = null
                        } catch (_: Exception) {
                            error = null
                        }
                    }
                }
            ) {
                // = 按钮长这样
                Button(
                    onClick = {
                        try {
                            val result = CalculatorUtil.evaluate(input)
                            if (result.isBlank() || result == "0") {
                                error = "Enter amount"
                                return@Button
                            }
                            onResult(result)
                        } catch (e: Exception) {
                            error = "Invalid input"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(top = 10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = mainColor)
                ) {
                    Text("OK", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
        }
    }
}

/**
 * 自定义 calculator 排布
 * = 支持你原本的 layout
 * 末行最后空一格
 * 下面加 OK 按钮
 */
@Composable
private fun CalculatorPadCustom(
    onKey: (String) -> Unit,
    okButton: @Composable () -> Unit
) {
    val rows = listOf(
        listOf("C", "÷", "×", "⌫"),
        listOf("7", "8", "9", "-"),
        listOf("4", "5", "6", "+"),
        listOf("1", "2", "3", "="),
        listOf("0", "00", ".", "")
    )
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color(0xFF222222).copy(alpha = 0.04f))
            .padding(horizontal = 4.dp)
    ) {
        rows.forEach { row ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { key ->
                    if (key.isBlank()) {
                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        OutlinedButton(
                            onClick = { onKey(key) },
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .padding(horizontal = 2.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = null,
                            elevation = null,
                            colors = when (key) {
                                "C" -> ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD32F2F), contentColor = Color.White)
                                "=" -> ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1976D2), contentColor = Color.White)
                                "⌫" -> ButtonDefaults.buttonColors(backgroundColor = Color(0xFF888888), contentColor = Color.White)
                                "÷", "×", "-", "+", "." -> ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF1F1F1), contentColor = Color.Black)
                                else -> ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black)
                            }
                        ) {
                            Text(
                                text = key,
                                fontSize = 18.sp,
                                fontWeight = if (key in listOf("C", "=", "⌫")) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
        okButton()
    }
}

// --- CalculatorUtil 与 ExpressionParser 保持与上方一致，不重复贴出 ---
