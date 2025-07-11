package com.williamv.debtmake.ui.settings

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.williamv.debtmake.utils.CountryCurrency
import com.williamv.debtmake.utils.CurrencyCountryUtil

private val Int.currencyName: Any
private val Int.currency: String
private val Int.country: String

/**
 * SettingsScreen
 * 应用设置页 - 主题色、暗黑模式、智能国家/币种选择
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentColor: Color,
    onColorChange: (Color) -> Unit,
    currentDarkMode: Boolean,
    onDarkModeToggle: (Boolean) -> Unit,
    currentCurrency: String,
    onCurrencyChange: (String) -> Unit,
    currentCountry: String,
    onCountryChange: (String) -> Unit
) {
    val context = LocalContext.current
    // 初始化币种国家数据
    LaunchedEffect(Unit) {
        CurrencyCountryUtil.loadFromAssets(context)
    }

    var showCurrencyCountryPicker by remember { mutableStateOf(false) }
    // 当前选中对象
    var selectedCountryCurrency by remember {
        mutableStateOf(
            CurrencyCountryUtil.getByCountry(currentCountry)
                ?: CurrencyCountryUtil.getAll().firstOrNull()
                ?: CountryCurrency("Malaysia", "MYR", "Malaysian Ringgit")
        )
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // 主题色
            Text("Theme Color", style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                ColorBox(Color(0xFFEF5350), "Red", currentColor, onColorChange)
                Spacer(Modifier.width(12.dp))
                ColorBox(Color(0xFF26A69A), "Green", currentColor, onColorChange)
                Spacer(Modifier.width(12.dp))
                ColorBox(Color(0xFF5C6BC0), "Blue", currentColor, onColorChange)
            }
            Spacer(Modifier.height(16.dp))

            // 暗黑模式
            Text("Dark Mode", style = MaterialTheme.typography.titleMedium)
            Switch(
                checked = currentDarkMode,
                onCheckedChange = onDarkModeToggle
            )
            Spacer(Modifier.height(16.dp))

            // 智能国家币种选择
            Text("Country & Currency", style = MaterialTheme.typography.titleMedium)
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { showCurrencyCountryPicker = true }
            ) {
                Text("${selectedCountryCurrency.country} (${selectedCountryCurrency.currency}) - ${selectedCountryCurrency.currencyName}")
            }
            if (showCurrencyCountryPicker) {
                CurrencyCountryPickerDialogV2(
                    onSelected = { cc ->
                        selectedCountryCurrency = cc
                        onCountryChange(cc.country)
                        onCurrencyChange("${cc.currency} - ${cc.currencyName}")
                    },
                    onDismiss = { showCurrencyCountryPicker = false }
                )
            }
        }
    }
}

/** 颜色选择块 **/
@Composable
fun ColorBox(
    color: Color,
    label: String,
    selected: Color,
    onSelected: (Color) -> Unit
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .border(
                width = if (selected == color) 3.dp else 1.dp,
                color = if (selected == color) Color.Black else Color.LightGray,
                shape = MaterialTheme.shapes.small
            )
            .background(color, MaterialTheme.shapes.small)
            .clickable { onSelected(color) }
    )
}

/**
 * 智能国家/币种 Picker（全世界币种支持，实时搜索）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyCountryPickerDialogV2(
    onSelected: (CountryCurrency) -> Unit,
    onDismiss: () -> Unit
) {
    var search by remember { mutableStateOf("") }
    val list = if (search.isBlank()) CurrencyCountryUtil.getAll() else CurrencyCountryUtil.search(search)
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Search Country/Currency") }
            )
            Spacer(Modifier.height(8.dp))
            LazyColumn {
                items(list) { item ->
                    ListItem(
                        headlineContent = { Text("${item.country} (${item.currency})") },
                        supportingContent = { Text(item.currencyName) },
                        modifier = Modifier.clickable { onSelected(item); onDismiss() }
                    )
                }
            }
        }
    }
}
