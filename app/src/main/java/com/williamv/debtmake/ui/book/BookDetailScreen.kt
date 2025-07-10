package com.williamv.debtmake.ui.book

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.williamv.debtmake.model.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.williamv.debtmake.util.RecentBookStore

/**
 * 账本详情页面（统计、联系人、Tab、按钮等全部用新词 Collect/Payout/Total collect amount/Total payout amount）
 * 进入时自动保存最近访问的 bookId
 */
@Composable
fun BookDetailScreen(
    book: Book,
    onBack: () -> Unit,
    onAddCollect: () -> Unit,
    onAddPayout: () -> Unit,
    onMore: () -> Unit
) {
    // -------- ① 保存最近访问的账本 id --------
    val context = LocalContext.current
    LaunchedEffect(book.id) {
        CoroutineScope(Dispatchers.IO).launch {
            RecentBookStore.saveRecentBookId(context, book.id)
        }
    }

    val collectTotal = book.collectTotal
    val payoutTotal = book.payoutTotal
    val remaining = collectTotal - payoutTotal

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(book.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onMore) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                },
                backgroundColor = if (remaining >= 0) Color(0xFFD32F2F) else Color(0xFF388E3C),
                contentColor = Color.White
            )
        },
        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                FloatingActionButton(
                    onClick = onAddCollect,
                    backgroundColor = Color(0xFFD32F2F)
                ) { Text("+", fontSize = 28.sp, color = Color.White) }

                FloatingActionButton(
                    onClick = onAddPayout,
                    backgroundColor = Color(0xFF388E3C)
                ) { Text("-", fontSize = 28.sp, color = Color.White) }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 统计面板
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = 6.dp
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Overview",
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Total collect amount", color = Color(0xFF388E3C))
                            Text(
                                String.format("%.2f RM", collectTotal),
                                color = Color(0xFFD32F2F),
                                fontSize = 20.sp
                            )
                        }
                        Column {
                            Text("Total payout amount", color = Color(0xFFD32F2F))
                            Text(
                                String.format("%.2f RM", payoutTotal),
                                color = Color(0xFF388E3C),
                                fontSize = 20.sp
                            )
                        }
                        Column {
                            Text("Remaining", color = Color(0xFFFBC02D))
                            Text(
                                String.format("%.2f RM", remaining),
                                color = Color(0xFFFBC02D),
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }

            // Tab 或按钮可用于切换/筛选
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TabButton(
                    selected = true,
                    text = "Collect",
                    color = Color(0xFFD32F2F),
                    onClick = onAddCollect
                )
                TabButton(
                    selected = false,
                    text = "Payout",
                    color = Color(0xFF388E3C),
                    onClick = onAddPayout
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            // 这里可放联系人流水列表、统计等内容（略）
        }
    }
}

@Composable
fun TabButton(selected: Boolean, text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected) color else Color.White,
            contentColor = if (selected) Color.White else color
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .height(36.dp)
            .weight(1f)
    ) {
        Text(text)
    }
}
