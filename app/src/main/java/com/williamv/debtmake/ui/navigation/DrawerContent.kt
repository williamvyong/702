package com.williamv.debtmake.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class DrawerItem(val label: String, val route: String)

private val drawerItems = listOf(
    DrawerItem("Master Book", "masterBook"),
    DrawerItem("Books", "books"),
    DrawerItem("Add Book", "addBook"),
    DrawerItem("Profile", "profile"),
    DrawerItem("Sync Status", "syncStatus"),
    DrawerItem("Settings", "settings"),
    DrawerItem("About & Help", "aboutHelp"),
    DrawerItem("Logout", "logout")
)

@Composable
fun DrawerContent(navController: NavController, closeDrawer: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        drawerItems.forEach { item ->
            Text(
                text = item.label,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        closeDrawer()
                        navController.navigate(item.route) {
                            // 避免重复入栈
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    .padding(vertical = 12.dp)
            )
        }
    }
}
