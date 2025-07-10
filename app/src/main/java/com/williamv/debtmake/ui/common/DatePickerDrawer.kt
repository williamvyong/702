package com.williamv.debtmake.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * 屏幕底部 Wheel Picker Drawer 日期选择器
 * @param visible 是否显示
 * @param initialDate 初始选中日期
 * @param topBarColor 顶部bar颜色（页面主色）
 * @param onConfirm 点击OK回调选中日期
 * @param onCancel 返回/取消回调
 * @param minYear 年份最小值
 * @param maxYear 年份最大值
 */
@Composable
fun DatePickerDrawer(
    visible: Boolean,
    initialDate: LocalDate,
    topBarColor: Color,
    onConfirm: (LocalDate) -> Unit,
    onCancel: () -> Unit,
    minYear: Int = 1980,
    maxYear: Int = LocalDate.now().year + 5,
) {
    if (!visible) return

    val years = (minYear..maxYear).toList()
    val months = (1..12).toList()
    val days = remember { mutableStateListOf<Int>() }

    var selectedYear by remember { mutableStateOf(initialDate.year) }
    var selectedMonth by remember { mutableStateOf(initialDate.monthValue) }
    var selectedDay by remember { mutableStateOf(initialDate.dayOfMonth) }

    LaunchedEffect(selectedYear, selectedMonth) {
        val daysInMonth = LocalDate.of(selectedYear, selectedMonth, 1).lengthOfMonth()
        days.clear()
        days.addAll(1..daysInMonth)
        if (selectedDay > daysInMonth) selectedDay = daysInMonth
    }

    val density = LocalDensity.current
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val sheetHeight = with(density) { (screenHeight * 0.39f).toPx() }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height((screenHeight * 0.39f))
            .align(Alignment.BottomCenter),
        color = MaterialTheme.colors.background,
        elevation = 10.dp,
        shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 顶部bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(topBarColor)
                    .padding(horizontal = 8.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onCancel() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Select date",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 三列 Wheel picker
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 年
                WheelPicker(
                    items = years.map { it.toString() },
                    selectedIndex = years.indexOf(selectedYear),
                    modifier = Modifier.weight(1f),
                    onSelected = { selectedYear = years[it] }
                )
                // 月
                WheelPicker(
                    items = months.map { it.toString().padStart(2, '0') },
                    selectedIndex = months.indexOf(selectedMonth),
                    modifier = Modifier.weight(1f),
                    onSelected = { selectedMonth = months[it] }
                )
                // 日
                WheelPicker(
                    items = days.map { it.toString().padStart(2, '0') },
                    selectedIndex = days.indexOf(selectedDay),
                    modifier = Modifier.weight(1f),
                    onSelected = { selectedDay = days[it] }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // OK按钮
            Button(
                onClick = {
                    val result = LocalDate.of(selectedYear, selectedMonth, selectedDay)
                    onConfirm(result)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 8.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = topBarColor)
            ) {
                Text("OK", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}

/**
 * WheelPicker 单列组件
 */
@Composable
fun WheelPicker(
    items: List<String>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    visibleCount: Int = 5,
    onSelected: (Int) -> Unit
) {
    val itemHeight = 40.dp
    val listState = rememberLazyListState(
        if (selectedIndex < 0) 0 else selectedIndex
    )
    LaunchedEffect(selectedIndex) {
        if (listState.firstVisibleItemIndex != selectedIndex) {
            listState.animateScrollToItem(selectedIndex)
        }
    }
    Box(modifier = modifier.height(itemHeight * visibleCount)) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = (itemHeight * (visibleCount / 2))),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(items) { index, item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight)
                        .clickable { onSelected(index) },
                    color = if (index == selectedIndex) MaterialTheme.colors.primary else Color.Gray,
                    fontWeight = if (index == selectedIndex) FontWeight.Bold else FontWeight.Normal,
                    fontSize = if (index == selectedIndex) 20.sp else 17.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        // 中间高亮区指示
        Box(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(itemHeight)
                .background(
                    MaterialTheme.colors.primary.copy(alpha = 0.08f),
                    RoundedCornerShape(10.dp)
                )
        )
    }
}
