package com.williamv.debtmake.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.williamv.debtmake.data.GreetingService            // 示范 Hilt 注入用
import com.williamv.debtmake.navigation.AppNavHost         // 刚刚修好的导航 Host
import com.williamv.debtmake.ui.navigation.DrawerContent   // 你的侧边栏内容
import com.williamv.debtmake.ui.theme.DebtMakeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var greetingService: GreetingService  // Hilt 注入示例

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DebtMakeTheme {
                // 1. Scaffold 状态 & Drawer 控制
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()

                // 2. NavController：后面所有导航都用它
                val navController = rememberNavController()

                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = { Text("DebtMake") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch { scaffoldState.drawerState.open() }
                                }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            }
                        )
                    },
                    drawerContent = {
                        DrawerContent(
                            navController = navController,
                            closeDrawer = { scope.launch { scaffoldState.drawerState.close() } }
                        )
                    }
                ) { innerPadding ->
                    // 3. 把整个导航图放这里
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
