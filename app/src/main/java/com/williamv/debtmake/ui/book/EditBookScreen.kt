package com.williamv.debtmake.ui.book

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.williamv.debtmake.R
import com.williamv.debtmake.model.Book
import com.williamv.debtmake.viewmodel.BookViewModel

/**
 * 编辑账本页面
 * @param bookId 要编辑的账本ID
 * @param onEditSuccess 保存成功后回调
 * @param onBack 返回按钮
 * @param bookViewModel 可注入
 */
@Composable
fun EditBookScreen(
    bookId: Long,
    onEditSuccess: () -> Unit,
    onBack: () -> Unit,
    bookViewModel: BookViewModel = viewModel()
) {
    var book by remember { mutableStateOf<Book?>(null) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // 拉取原账本信息
    LaunchedEffect(bookId) {
        val loaded = bookViewModel.getBookById(bookId)
        book = loaded
        loaded?.let {
            name = it.name
            description = it.description
            imageUri = it.iconUri?.let { uri -> Uri.parse(uri) }
        }
    }

    // 图标选择
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> if (uri != null) imageUri = uri }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Book") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                actions = {
                    TextButton(
                        onClick = {
                            if (book != null && name.isNotBlank()) {
                                val updated = book!!.copy(
                                    name = name,
                                    description = description,
                                    iconUri = imageUri?.toString() ?: ""
                                )
                                bookViewModel.updateBook(updated)
                                onEditSuccess()
                            }
                        }
                    ) {
                        Text("SAVE", color = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val painter = if (imageUri != null) {
                rememberAsyncImagePainter(model = imageUri)
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
