package com.williamv.debtmake.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DrawerContent(
    navController: NavHostController,
    closeDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        // 首页/账本主页
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("bookList") {
                        popUpTo("bookList")
                        launchSingleTop = true
                        restoreState = true
                    }
                    closeDrawer()
                }
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Book, contentDescription = "Book List")
            Spacer(Modifier.width(14.dp))
            Text("Books", style = MaterialTheme.typography.subtitle1)
        }
        // --------------- 新增 Settings 入口 ---------------
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("settings") {
                        launchSingleTop = true
                        restoreState = true
                    }
                    closeDrawer()
                }
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
            Spacer(Modifier.width(14.dp))
            Text("Settings", style = MaterialTheme.typography.subtitle1)
        }
    }
}
