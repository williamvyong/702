// 文件路径: app/src/main/java/com/williamv.debtmake/ui/contact/AddContactScreen.kt
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.williamv.debtmake.R
import com.williamv.debtmake.model.Contact
import com.williamv.debtmake.viewmodel.ContactViewModel

/**
 * 新建联系人页面
 * @param bookId 当前账本ID（联系人归属）
 * @param onContactAdded 新建完成后回调（一般用于返回联系人列表）
 * @param contactViewModel 可注入
 */
@Composable
fun AddContactScreen(
    bookId: Long,
    onContactAdded: () -> Unit,
    contactViewModel: ContactViewModel = viewModel()
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
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
                title = { Text("Add Contact") },
                backgroundColor = Color(0xFF1976D2),
                contentColor = Color.White
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
            // 头像选择（默认图/自选图）
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
                        val contact = Contact(
                            bookId = bookId,
                            name = name,
                            phoneNumber = if (phone.isBlank()) null else phone,
                            imageUri = imageUri?.toString()
                        )
                        contactViewModel.insertContact(contact)
                        onContactAdded()
                    }
                },
                enabled = name.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SAVE")
            }
        }
    }
}
