package com.williamv.debtmake.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.williamv.debtmake.navigation.AppNavHost
import com.williamv.debtmake.ui.theme.DebtMakeTheme
import com.williamv.debtmake.viewmodel.BookViewModel
import com.williamv.debtmake.viewmodel.ContactViewModel
import com.williamv.debtmake.viewmodel.EntryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DebtMakeTheme {
                // 使用 viewModel() 注入
                val bookViewModel: BookViewModel = viewModel()
                val entryViewModel: EntryViewModel = viewModel()
                val contactViewModel: ContactViewModel = viewModel()
                AppNavHost(
                    bookViewModel = bookViewModel,
                    entryViewModel = entryViewModel,
                    contactViewModel = contactViewModel
                )
            }
        }
    }
}
