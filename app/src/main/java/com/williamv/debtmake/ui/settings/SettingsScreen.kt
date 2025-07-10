package com.williamv.debtmake.ui.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.williamv.debtmake.ui.theme.BlueMain

// 可选：你的主色
val DefaultCollectColor = Color(0xFFD32F2F)
val DefaultPayoutColor = Color(0xFF388E3C)

@Composable
fun SettingsScreen(
    navController: NavController,
    collectColor: Color = DefaultCollectColor,
    payoutColor: Color = DefaultPayoutColor,
    onCollectColorChange: (Color) -> Unit,
    onPayoutColorChange: (Color) -> Unit,
    currentCurrency: String = "RM",
    onCurrencyChange: (String) -> Unit,
    currentLanguage: String = "English",
    onLanguageChange: (String) -> Unit,
    currentDateFormat: String = "yyyy-MM-dd",
    onDateFormatChange: (String) -> Unit
) {
    val context = LocalContext.current
    var showCurrencySelector by remember { mutableStateOf(false) }
    var showLanguageSelector by remember { mutableStateOf(false) }
    var showDateFormatSelector by remember { mutableStateOf(false) }
    var showCollectColorPicker by remember { mutableStateOf(false) }
    var showPayoutColorPicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Setting", fontSize = 22.sp, fontWeight = FontWeight.Bold) },
                backgroundColor = BlueMain,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Setting currency
            Text("Setting currency", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            Text(
                currentCurrency,
                Modifier
                    .fillMaxWidth()
                    .clickable { showCurrencySelector = true }
                    .padding(vertical = 6.dp),
                color = MaterialTheme.colors.primary
            )

            // Setting language
            Spacer(Modifier.height(18.dp))
            Text("Set language", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            Text(
                currentLanguage,
                Modifier
                    .fillMaxWidth()
                    .clickable { showLanguageSelector = true }
                    .padding(vertical = 6.dp),
                color = MaterialTheme.colors.primary
            )

            // Select date format
            Spacer(Modifier.height(18.dp))
            Text("Select date format", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            Text(
                currentDateFormat,
                Modifier
                    .fillMaxWidth()
                    .clickable { showDateFormatSelector = true }
                    .padding(vertical = 6.dp),
                color = MaterialTheme.colors.primary
            )

            // Custom collect color
            Spacer(Modifier.height(18.dp))
            Text("Collect color", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable { showCollectColorPicker = true }
                    .padding(vertical = 6.dp)
            ) {
                Surface(
                    color = collectColor,
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(0.15f)
                ) {}
            }

            Spacer(Modifier.height(18.dp))
            Text("Payout color", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable { showPayoutColorPicker = true }
                    .padding(vertical = 6.dp)
            ) {
                Surface(
                    color = payoutColor,
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(0.15f)
                ) {}
            }

            Spacer(Modifier.height(24.dp))
            Text(
                "MORE",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 15.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }

    // Selectors
    if (showCurrencySelector) {
        CurrencySelectorDialog(
            current = currentCurrency,
            onSelect = {
                onCurrencyChange(it)
                showCurrencySelector = false
            },
            onDismiss = { showCurrencySelector = false }
        )
    }

    if (showLanguageSelector) {
        LanguageSelectorDialog(
            current = currentLanguage,
            onSelect = {
                onLanguageChange(it)
                showLanguageSelector = false
            },
            onDismiss = { showLanguageSelector = false }
        )
    }

    if (showDateFormatSelector) {
        DateFormatSelectorDialog(
            current = currentDateFormat,
            onSelect = {
                onDateFormatChange(it)
                showDateFormatSelector = false
            },
            onDismiss = { showDateFormatSelector = false }
        )
    }

    if (showCollectColorPicker) {
        ThemeColorPickerDialog(
            title = "Pick collect color",
            initial = collectColor,
            onSelect = {
                onCollectColorChange(it)
                showCollectColorPicker = false
            },
            onDismiss = { showCollectColorPicker = false }
        )
    }

    if (showPayoutColorPicker) {
        ThemeColorPickerDialog(
            title = "Pick payout color",
            initial = payoutColor,
            onSelect = {
                onPayoutColorChange(it)
                showPayoutColorPicker = false
            },
            onDismiss = { showPayoutColorPicker = false }
        )
    }
}

// 下面是简化版选择器弹窗，正式代码请按你自己需求写复杂点

@Composable
fun CurrencySelectorDialog(current: String, onSelect: (String) -> Unit, onDismiss: () -> Unit) {
    val list = listOf("RM", "USD", "EUR", "SGD", "JPY", "CNY")
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select currency") },
        buttons = {
            Column {
                list.forEach {
                    Text(
                        it,
                        Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(it) }
                            .padding(12.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun LanguageSelectorDialog(current: String, onSelect: (String) -> Unit, onDismiss: () -> Unit) {
    val list = listOf("English", "Chinese", "Malay")
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set language") },
        buttons = {
            Column {
                list.forEach {
                    Text(
                        it,
                        Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(it) }
                            .padding(12.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun DateFormatSelectorDialog(current: String, onSelect: (String) -> Unit, onDismiss: () -> Unit) {
    val list = listOf("yyyy-MM-dd", "dd-MM-yyyy", "MM-dd-yyyy")
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select date format") },
        buttons = {
            Column {
                list.forEach {
                    Text(
                        it,
                        Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(it) }
                            .padding(12.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun ThemeColorPickerDialog(
    title: String,
    initial: Color,
    onSelect: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    val colors = listOf(
        Color(0xFFD32F2F), // 红
        Color(0xFF388E3C), // 绿
        Color(0xFF1976D2), // 蓝
        Color(0xFFFBC02D), // 黄
        Color(0xFF512DA8), // 紫
        Color(0xFFFFA726), // 橙
    )
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        buttons = {
            Row(Modifier.padding(16.dp)) {
                colors.forEach {
                    Box(
                        Modifier
                            .size(36.dp)
                            .padding(4.dp)
                            .clickable { onSelect(it) },
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(color = it, modifier = Modifier.size(32.dp)) {}
                    }
                }
            }
        }
    )
}
