// ✅ 文件路径: app/src/main/java/com/williamv/debtmake/ui/MainActivity.kt
package com.williamv.debtmake.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.williamv.debtmake.database.AppDatabase
import com.williamv.debtmake.navigation.AppNavHost
import com.williamv.debtmake.ui.theme.DebtMakeTheme
import com.williamv.debtmake.supabase.SupabaseService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// ✅ DataStore 扩展属性
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ 初始化 Supabase（必须放在 onCreate 内）
        val supabaseUrl = BuildConfig.SUPABASE_URL
        val supabaseAnonKey = BuildConfig.SUPABASE_ANON_KEY
        SupabaseService.initialize(applicationContext, supabaseUrl, supabaseAnonKey)

        val context = this
        val dataStore = context.dataStore
        val isLoggedInKey = booleanPreferencesKey("is_logged_in")

        setContent {
            DebtMakeTheme {
                val navController = rememberNavController()
                var isLoggedIn by remember { mutableStateOf<Boolean?>(null) }

                LaunchedEffect(Unit) {
                    val prefs = dataStore.data.first()
                    isLoggedIn = prefs[isLoggedInKey] ?: false
                }

                val database = AppDatabase.getInstance(context)
                val bookDao = database.bookDao()

                isLoggedIn?.let {
                    AppNavHost(
                        navController = navController,
                        bookDao = bookDao,
                        isLoggedIn = it,
                        onLogin = {
                            context.lifecycleScope.launch {
                                dataStore.edit { prefs ->
                                    prefs[isLoggedInKey] = true
                                }
                            }
                            navController.navigate("bookList") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onLogout = {
                            context.lifecycleScope.launch {
                                dataStore.edit { prefs ->
                                    prefs[isLoggedInKey] = false
                                }
                            }
                            navController.navigate("login") {
                                popUpTo("bookList") { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
