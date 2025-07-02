package com.williamv.debtmake.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

/**
 * @param closeDrawer 抽屉关闭的回调
 */
@Composable
fun DrawerContent(
    navController: NavHostController,
    closeDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .widthIn(max = 280.dp) // 最大宽度 280dp
            .padding(16.dp)
    ) {
        // 循环渲染菜单项
        listOf(
            "Master Book" to "master_book_route",
            "Books" to "books_route",
            "Add Book" to "add_book_route",
            "Profile" to "profile_route",
            "Sync Status" to "sync_status_route",
            "Settings" to "settings_route",
            "About & Help" to "about_route",
            "Logout" to "logout_route"
        ).forEach { (label, route) ->
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clickable {
                        closeDrawer()       // 1. 先关闭抽屉
                        navController.navigate(route) {
                            popUpTo("master_book_route") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
            )
        }
    }
}
