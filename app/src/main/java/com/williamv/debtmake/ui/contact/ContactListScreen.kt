package com.williamv.debtmake.ui.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.williamv.debtmake.R
import com.williamv.debtmake.model.Contact
import com.williamv.debtmake.viewmodel.ContactViewModel

/**
 * 联系人列表页面（集成搜索框）
 * @param bookId 当前账本ID
 * @param onContactClick 点击联系人回调
 * @param onAddContact 点击添加联系人按钮回调
 * @param contactViewModel 可注入（用于测试或导航）
 */
@Composable
fun ContactListScreen(
    bookId: Long,
    onContactClick: (contactId: Long, name: String) -> Unit,
    onAddContact: () -> Unit,
    contactViewModel: ContactViewModel = viewModel()
) {
    val contacts by contactViewModel.contacts.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // 页面启动时加载联系人
    LaunchedEffect(bookId) {
        contactViewModel.loadContactsForBook(bookId)
    }

    // 根据搜索框过滤联系人
    val filteredContacts = contacts.filter {
        it.name.contains(searchQuery.text, ignoreCase = true) ||
                (it.phoneNumber?.contains(searchQuery.text, ignoreCase = true) == true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contacts") },
                backgroundColor = Color(0xFF1976D2),
                contentColor = Color.White
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddContact) {
                Icon(painterResource(id = R.drawable.ic_add), contentDescription = "Add Contact")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // 搜索框
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search contacts...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )

            if (filteredContacts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 60.dp, 0.dp, 0.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text("No contacts found.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(filteredContacts) { contact ->
                        ContactListItem(
                            contact = contact,
                            onClick = { onContactClick(contact.id, contact.name) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContactListItem(
    contact: Contact,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() },
        elevation = 3.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 头像
            val painter = if (!contact.imageUri.isNullOrBlank()) {
                rememberAsyncImagePainter(model = contact.imageUri)
            } else {
                painterResource(id = R.drawable.ic_default_avatar)
            }
            Image(
                painter = painter,
                contentDescription = "Contact Avatar",
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                contact.phoneNumber?.takeIf { it.isNotBlank() }?.let {
                    Text(
                        text = "Phone: $it",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
