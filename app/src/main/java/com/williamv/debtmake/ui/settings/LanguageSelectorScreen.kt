package com.williamv.debtmake.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class AppLanguage(val languageName: String, val localeCode: String)

// 语言列表
val languages = listOf(
    AppLanguage("English", "en"),
    AppLanguage("中文", "zh"),
    AppLanguage("Malay", "ms"),
    AppLanguage("日本語", "ja"),
    AppLanguage("한국어", "ko"),
    AppLanguage("Français", "fr"),
    AppLanguage("Deutsch", "de"),
    AppLanguage("Español", "es"),
    AppLanguage("Italiano", "it"),
    AppLanguage("Português", "pt"),
    AppLanguage("Русский", "ru"),
    AppLanguage("Tiếng Việt", "vi"),
    AppLanguage("ภาษาไทย", "th"),
    AppLanguage("हिन्दी", "hi"),
    AppLanguage("العربية", "ar")
    // 可以继续补充其他语言
)

@Composable
fun LanguageSelectorScreen(
    navController: NavController,
    onLanguageSelected: (AppLanguage) -> Unit
) {
    var query by remember { mutableStateOf("") }

    val filteredLanguages = languages.filter {
        it.languageName.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Language", fontSize = 22.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // 搜索框
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search language") }
            )

            Divider()

            LazyColumn {
                items(filteredLanguages) { language ->
                    LanguageItem(language) {
                        onLanguageSelected(language)
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

@Composable
fun LanguageItem(language: AppLanguage, onClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(horizontal = 16.dp, vertical = 10.dp)) {
        Text(language.languageName, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}
