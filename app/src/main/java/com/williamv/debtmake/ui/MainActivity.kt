package com.williamv.debtmake.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.williamv.debtmake.data.GreetingService
import com.williamv.debtmake.navigation.AppNavHost
import com.williamv.debtmake.ui.theme.DebtMakeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var greetingService: GreetingService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DebtMakeTheme {
                // 1. Drawer 状态 & scope
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                // 2. NavController
                val navController = rememberNavController()

                // 3. 判断是否大屏（示例阈值：宽度 >= 600dp 视为平板）
                val configuration = LocalConfiguration.current
                val isTablet = configuration.screenWidthDp >= 600

                if (isTablet) {
                    // 平板：直接在左侧固定展示 Drawer + 内容
                    Row {
                        // 左侧抽屉，固定宽度 280dp
                        Box(modifier = Modifier
                            .width(280.dp)
                            .fillMaxHeight()
                        ) {
                            DrawerContent(
                                navController = navController,
                                closeDrawer = {} // 平板不需要关抽屉
                            )
                        }
                        // 右侧主内容
                        Scaffold(
                            topBar = { /* ...同上 TopAppBar 部分... */ },
                            content = { inner ->
                                AppNavHost(navController, Modifier.padding(inner))
                            }
                        )
                    }
                } else {
                    // 手机：滑出式 ModalDrawer，宽度固定 280dp
                    ModalDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            Box(modifier = Modifier.width(280.dp)) {
                                DrawerContent(
                                    navController = navController,
                                    closeDrawer = { scope.launch { drawerState.close() } }
                                )
                            }
                        }
                    ) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = { Text("DebtMake") },
                                    navigationIcon = {
                                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                                        }
                                    }
                                )
                            },
                            content = { inner ->
                                AppNavHost(navController, Modifier.padding(inner))
                            }
                        )
                    }
                }
            }
        }
    }
}
