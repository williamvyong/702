package com.williamv.debtmake.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.williamv.debtmake.navigation.AppNavHost
import com.williamv.debtmake.ui.theme.DebtMakeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        setContent {
            DebtMakeTheme {
                AppNavHost(
                    isLoggedIn = isLoggedIn,
                    context = this
                )
            }
        }
    }
}
