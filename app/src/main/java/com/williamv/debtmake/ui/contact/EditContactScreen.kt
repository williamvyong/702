package com.williamv.debtmake.ui.contact

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.williamv.debtmake.R
import com.williamv.debtmake.model.Contact
import com.williamv.debtmake.viewmodel.ContactViewModel

/**
 * 联系人编辑页面
 * @param contactId 被编辑的联系人ID
 * @param onEditSuccess 保存成功后回调（如返回详情页/列表）
 * @param onBack 返回按钮
 * @param contactViewModel 可注入
 */
@Composable
fun EditContactScreen(
    contactId: Long,
    onEditSuccess: () -> Unit,
    onBack: () -> Unit,
    contactViewModel: ContactViewModel = viewModel()
) {
    var contact by remember { mutableStateOf<Contact?>(null) }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // 加载联系人数据
    LaunchedEffect(contactId) {
        val c = contactViewModel.getContactById(contactId)
        contact = c
        c?.let {
            name = it.name
            phone = it.phoneNumber ?: ""
            imageUri = it.imageUri?.let { uri -> Uri.parse(uri) }
        }
    }

    // 图库选择头像
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> if (uri != null) imageUri = uri }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Contact") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color(0xFF1976D2),
                contentColor = Color.White
            )
        }
    ) { padding ->
        contact?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 头像
                val painter = if (imageUri != null) {
                    rememberAsyncImagePainter(model = imageUri)
                } else {
                    painterResource(id = R.drawable.ic_default_avatar)
                }
                Image(
                    painter = painter,
                    contentDescription = "Contact Avatar",
                    modifier = Modifier
                        .size(90.dp)
                        .border(2.dp, Color.Gray, CircleShape)
                        .clickable { pickImageLauncher.launch("image/*") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            val updated = it.copy(
                                name = name,
                                phoneNumber = if (phone.isBlank()) null else phone,
                                imageUri = imageUri?.toString()
                            )
                            contactViewModel.updateContact(updated)
                            onEditSuccess()
                        }
                    },
                    enabled = name.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SAVE")
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
    }
}
