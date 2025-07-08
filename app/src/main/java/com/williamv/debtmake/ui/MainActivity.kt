// ✅ 文件路径: app/src/main/java/com/williamv/debtmake/ui/MainActivity.kt
package com.williamv.debtmake.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.williamv.debtmake.navigation.AppNavHost
import com.williamv.debtmake.ui.theme.DebtMakeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DebtMakeTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}
