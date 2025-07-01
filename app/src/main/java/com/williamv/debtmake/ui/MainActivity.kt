package com.williamv.debtmake.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.williamv.debtmake.data.GreetingService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var greetingService: GreetingService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppContainer()
        }
    }

    @Composable
    private fun AppContainer() {
        Scaffold(
            modifier = Modifier.Companion.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            content = {
                GreetingScreen()
            }
        )
    }

    @Composable
    private fun GreetingScreen() {
        Text(
            text = greetingService.greet(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.Companion.fillMaxSize()
        )
    }
}