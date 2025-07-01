package com.williamv.debtmake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                Text(
                    text = "Hello Compose!",
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Text("这是 Preview")
}
