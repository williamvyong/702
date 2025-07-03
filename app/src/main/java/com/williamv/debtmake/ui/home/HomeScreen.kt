package com.williamv.debtmake.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.williamv.debtmake.R
import com.williamv.debtmake.ui.book.Book

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(navController: NavController, selectedBook: Book?) {
    val pagerState = rememberPagerState()

    HorizontalPager(count = 2, state = pagerState) { page ->
        if (page == 0) {
            TransactionPage(
                isLend = true,
                topColor = Color(0xFFE53935),
                arrowPainter = painterResource(id = R.drawable.ic_arrow_up),
                currency = "RM",
                navController = navController,
                selectedBook = selectedBook
            )
        } else {
            TransactionPage(
                isLend = false,
                topColor = Color(0xFF43A047),
                arrowPainter = painterResource(id = R.drawable.ic_arrow_down),
                currency = "RM",
                navController = navController,
                selectedBook = selectedBook
            )
        }
    }
}

@Composable
fun TransactionPage(
    isLend: Boolean,
    topColor: Color,
    arrowPainter: Painter,
    currency: String,
    navController: NavController,
    selectedBook: Book?
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Red/Green Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(topColor)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.login_background),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = selectedBook?.title ?: "example",
                            color = Color.White,
                            modifier = Modifier.clickable {
                                navController.navigate("book_list")
                            }
                        )
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
                    }
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "LEND\n0.00 $currency",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "BORROW\n0.00 $currency",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Overview Card
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Overview", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Touch to view full report", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = if (isLend) "Total Lend Amount" else "Total Borrow Amount",
                            fontSize = 14.sp
                        )
                        Text("Collected", fontSize = 14.sp)
                        Text("Remaining", fontSize = 14.sp)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("0.00 $currency", color = if (isLend) Color.Red else Color.Green)
                        Text("0.00 $currency", color = if (isLend) Color.Green else Color.Red)
                        Text("0.00 $currency", color = Color(0xFFFFA000))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Timeline / Filter / Sort Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilterButton(icon = Icons.Default.Visibility, text = "Timeline")
                FilterButton(icon = Icons.Default.FilterList, text = "Active")
                FilterButton(icon = Icons.Default.SwapVert, text = "Default")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Empty transaction message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Transaction is currently empty", color = Color.Gray)
            }
        }

        // Floating + Button
        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = topColor,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

@Composable
fun FilterButton(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.1f))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}