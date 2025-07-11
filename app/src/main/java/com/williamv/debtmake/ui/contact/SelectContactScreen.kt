// 文件路径: app/src/main/java/com/williamv.debtmake/ui/contact/SelectContactScreen.kt
package com.williamv.debtmake.ui.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.williamv.debtmake.R
import com.williamv.debtmake.model.Contact
import com.williamv.debtmake.viewmodel.ContactViewModel

/**
 * 联系人选择页面
 * @param onBack 返回回调
 * @param onSelected 选中联系人回调
 * @param contactViewModel 联系人ViewModel
 */
@Composable
fun SelectContactScreen(
    onBack: () -> Unit,
    onSelected: (Contact) -> Unit,
    contactViewModel: ContactViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val mainColor = Color(0xFF1976D2)
    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    val allContacts by contactViewModel.contacts.collectAsState(initial = emptyList())
    val recentContacts by contactViewModel.recentContacts.collectAsState(initial = emptyList())

    val showContacts = if (selectedTab == 0) recentContacts else allContacts
    val filteredContacts = showContacts.filter {
        searchQuery.isBlank() ||
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.phoneNumber?.contains(searchQuery, ignoreCase = true) == true
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    backgroundColor = mainColor,
                    contentColor = Color.White,
                    elevation = 0.dp,
                    title = {
                        Text(
                            "Select Contact",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    }
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(mainColor)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    TabButton(
                        text = "Recently",
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        mainColor = mainColor
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    TabButton(
                        text = "Contacts",
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        mainColor = mainColor
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF7F8FB))
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 18.dp)
            ) {
                items(filteredContacts) { contact ->
                    ContactListItem(
                        contact = contact,
                        onClick = { onSelected(contact) }
                    )
                }
            }

            if (filteredContacts.isEmpty()) {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No contacts found.", color = Color.Gray)
                }
            }
        }
    }
}

@Composable
private fun TabButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    mainColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .height(34.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected) Color.White else mainColor,
            contentColor = if (selected) mainColor else Color.White
        ),
        elevation = null,
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}

@Composable
private fun ContactListItem(
    contact: Contact,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val painter = if (contact.imageUri.isNullOrBlank()) {
            painterResource(id = R.drawable.ic_default_avatar)
        } else {
            rememberAsyncImagePainter(contact.imageUri)
        }
        Image(
            painter = painter,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(0xFFE2E2E2))
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(contact.name, fontWeight = FontWeight.Medium, fontSize = 16.sp)
            if (!contact.phoneNumber.isNullOrBlank()) {
                Text(contact.phoneNumber!!, color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}
