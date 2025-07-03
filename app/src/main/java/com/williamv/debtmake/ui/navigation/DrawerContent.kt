package com.williamv.debtmake.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DrawerContent(
    navController: NavHostController,
    closeDrawer: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Master Book",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("home") {
                        popUpTo("home")
                        launchSingleTop = true
                        restoreState = true
                    }
                    closeDrawer()
                }
                .padding(16.dp)
        )
        // 更多菜单项可依此添加
    }
}