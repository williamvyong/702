package com.williamv.debtmake.ui.entry

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.williamv.debtmake.R
import com.williamv.debtmake.model.Book
import com.williamv.debtmake.model.Contact
import com.williamv.debtmake.viewmodel.BookViewModel
import com.williamv.debtmake.viewmodel.ContactViewModel
import com.williamv.debtmake.viewmodel.EntryViewModel
import com.williamv.debtmake.ui.common.CalculatorInputScreen
import com.williamv.debtmake.ui.common.DatePickerDrawer
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * 新增账目页面
 * - 支持联系人、账本选择页面的跳转与回填
 * - 金额/日期弹窗，主色随模式切换
 */
@Composable
fun AddTransactionScreen(
    navController: NavController, // 需要传入 navController
    onBack: () -> Unit,
    onSaved: () -> Unit,
    contactViewModel: ContactViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    bookViewModel: BookViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    entryViewModel: EntryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    selectedContact: Contact? = null, // 从导航回传的联系人
    selectedBook: Book? = null,       // 从导航回传的账本
    onClearContact: () -> Unit = {},  // 回填后清理
    onClearBook: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    // 联系人、账本：收到回传后优先用回传参数
    var contact by remember { mutableStateOf(selectedContact) }
    var book by remember { mutableStateOf(selectedBook) }

    // 当收到新的参数时自动回填，并清理参数
    LaunchedEffect(selectedContact) {
        if (selectedContact != null) {
            contact = selectedContact
            onClearContact()
        }
    }
    LaunchedEffect(selectedBook) {
        if (selectedBook != null) {
            book = selectedBook
            onClearBook()
        }
    }

    // 收付切换
    var isCollect by remember { mutableStateOf(true) }

    // 金额输入
    var amount by remember { mutableStateOf("") }
    var showCalculator by remember { mutableStateOf(false) }

    // 描述输入
    var description by remember { mutableStateOf("") }

    // 日期选择
    var date by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    // 主色（collect 红/payout 绿）
    val mainColor by animateColorAsState(
        if (isCollect) Color(0xFFD32F2F) else Color(0xFF388E3C)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add transaction",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                backgroundColor = mainColor,
                contentColor = Color.White
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            // 联系人选择区
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val painter = if (contact?.imageUri.isNullOrBlank()) {
                    painterResource(id = R.drawable.ic_default_avatar)
                } else {
                    rememberAsyncImagePainter(contact?.imageUri)
                }
                Image(
                    painter = painter,
                    contentDescription = "Contact Avatar",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        contact?.name ?: "Select from the list",
                        fontWeight = FontWeight.Medium,
                        fontSize = 17.sp
                    )
                    Text(
                        contact?.phoneNumber ?: "",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
                Button(
                    onClick = {
                        navController.navigate("selectContact")
                    },
                    modifier = Modifier
                        .height(32.dp)
                        .padding(start = 4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = mainColor)
                ) {
                    Text("Select", color = Color.White, fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(28.dp))

            // Collect / Payout 切换
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ModeSwitchButton(
                    label = "Collect",
                    selected = isCollect,
                    color = Color(0xFFD32F2F),
                    onClick = { isCollect = true }
                )
                Spacer(modifier = Modifier.width(8.dp))
                ModeSwitchButton(
                    label = "Payout",
                    selected = !isCollect,
                    color = Color(0xFF388E3C),
                    onClick = { isCollect = false }
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // 金额输入（点击跳 calculator）
            Text(
                "Loan amount",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            OutlinedTextField(
                value = amount,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCalculator = true },
                enabled = false,
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 21.sp,
                    textAlign = TextAlign.Right
                ),
                placeholder = { Text("0", fontSize = 21.sp) }
            )

            Spacer(modifier = Modifier.height(22.dp))

            // 描述
            Text(
                "Descriptions",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                placeholder = { Text("Optional") },
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(22.dp))

            // 账本选择
            Text(
                "Record in a book",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            OutlinedButton(
                onClick = {
                    navController.navigate("selectBook")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(book?.name ?: "Select book", color = mainColor)
            }

            Spacer(modifier = Modifier.height(22.dp))

            // 日期选择
            Text(
                "Date of create",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                val dateString = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                Text(dateString, color = mainColor)
            }

            Spacer(modifier = Modifier.height(42.dp))

            // 底部 OK 按钮
            Button(
                onClick = {
                    if (contact == null || amount.isBlank() || book == null) {
                        // 可弹出提示
                        return@Button
                    }
                    scope.launch {
                        entryViewModel.insertEntry(
                            com.williamv.debtmake.model.Entry(
                                bookId = book!!.id,
                                contactId = contact!!.id,
                                amount = amount.toDoubleOrNull() ?: 0.0,
                                type = if (isCollect) "collect" else "payout",
                                description = description,
                                timestamp = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                            )
                        )
                        onSaved()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = mainColor)
            ) {
                Text("OK", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
    }

    // 金额 calculator 弹窗
    if (showCalculator) {
        CalculatorInputScreen(
            title = "Loan amount",
            initialAmount = amount,
            mainColor = mainColor,
            onResult = {
                amount = it
                showCalculator = false
            },
            onBack = { showCalculator = false }
        )
    }

    // 日期 wheel picker drawer 弹窗
    if (showDatePicker) {
        DatePickerDrawer(
            visible = true,
            initialDate = date,
            topBarColor = mainColor,
            onConfirm = {
                date = it
                showDatePicker = false
            },
            onCancel = { showDatePicker = false }
        )
    }
}

/**
 * 收付模式切换按钮（Collect / Payout）
 */
@Composable
fun ModeSwitchButton(label: String, selected: Boolean, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected) color else Color.White,
            contentColor = if (selected) Color.White else color
        ),
        elevation = null,
        shape = MaterialTheme.shapes.medium
    ) {
        Text(label, fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}
