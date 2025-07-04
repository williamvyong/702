// 📂 文件路径: com.williamv.debtmake.ui.book/AddBookScreen.kt
package com.williamv.debtmake.ui.book

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.williamv.debtmake.R
import com.williamv.debtmake.viewmodel.BookViewModel

/**
 * AddBookScreen：添加新账本页面
 * @param onBookSaved 添加成功后回调（跳回上一页）
 */
@Composable
fun AddBookScreen(
    onBookSaved: () -> Unit,
    bookViewModel: BookViewModel = viewModel()
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // 打开图库选择头像
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) imageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add new book") },
                backgroundColor = MaterialTheme.colors.primary,
                actions = {
                    TextButton(
                        onClick = {
                            if (name.isNotBlank()) {
                                bookViewModel.insertBook(name, description, imageUri)
                                onBookSaved()
                            }
                        }
                    ) {
                        Text("SAVE", color = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 图标（默认或自选）
            val painter = if (imageUri != null) {
                rememberImagePainter(data = imageUri)
            } else {
                painterResource(id = R.drawable.ic_book_default)
            }

            Image(
                painter = painter,
                contentDescription = "Book Icon",
                modifier = Modifier
                    .size(96.dp)
                    .border(2.dp, Color.Gray, CircleShape)
                    .clickable { pickImageLauncher.launch("image/*") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Book Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
