package com.williamv.debtmake.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.williamv.debtmake.navigation.AppNavHost
import com.williamv.debtmake.ui.theme.DebtMakeTheme

/**
 * App 主入口 Activity
 * 负责初始化 Compose、全局主题和导航
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // 全局主题
            DebtMakeTheme {
                // 全局导航（已在 AppNavHost 内部初始化 NavController）
                AppNavHost()
            }
        }
    }
}
