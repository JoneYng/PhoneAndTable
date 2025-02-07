package com.zx.fitter.view

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.NEXUS_6
import androidx.compose.ui.tooling.preview.Devices.PIXEL_TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zx.fitter.R
import com.zx.fitter.genMonthData
import com.zx.fitter.genWeekData
import com.zx.fitter.getIndex
import com.zx.fitter.ui.theme.AppColorCCC
import com.zx.fitter.ui.theme.AppMain
import com.zx.fitter.ui.theme.AppText3
import com.zx.fitter.ui.theme.AppText9
import com.zx.fitter.ui.theme.translate
import com.zx.fitter.utils.CalendarUtils
import com.zx.fitter.utils.click
import com.zx.fitter.utils.rememberMutableStateOf
import kotlinx.coroutines.launch
import java.util.Date


/**
 * @description:
 * @author: zhouxiang
 * @created: 2024/08/29 16:34
 * @version: V1.0
 */
// 周视图标题
var weeksTitle: MutableList<String> = mutableListOf("一", "二", "三", "四", "五", "六", "日")

//月视图标题
var monthTitle: MutableList<String> = mutableListOf("日", "一", "二", "三", "四", "五", "六")

enum class CalendarType {
    MONTH, WEEK
}

data class CalendarItem(
    var date: Date = Date(),//日期
    var selected: Boolean = false, //是否选中
    var flag: Boolean = false, //是否有标记
    var inMonth: Boolean = false, //是否是可点击
    var inToDay: Boolean = false, //是否是今天
)


fun getCalendarDateTextColor(calendarItem: CalendarItem): Color {
    return if (calendarItem.selected) {
        Color.White
    } else if (!calendarItem.inMonth) {
        AppColorCCC
    } else {
        AppText3
    }
}

fun getCalendarDateBgColor(calendarItem: CalendarItem): Color {
    return if (calendarItem.selected) {
        AppMain
    } else {
       translate
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, showSystemUi = true, device = NEXUS_6)
fun PhoneCalendarView(
    // 当前显示的类型
    type: CalendarType = CalendarType.MONTH,
    //默认显示时间
    defaultDate: Date = Date(),
    //样式
    modifier: Modifier = Modifier.fillMaxWidth(),
    //标记日期
    markDateList: MutableList<String> = mutableListOf(),
    //月份滑动回调
    onMonthChange: ((startDate: String, endDate: String) -> Unit)? = null,
    //日期点击回调
    onDateClick: ((date: String) -> Unit)? = null
) {
    // 获取当前的 Context
    val context = LocalContext.current

    val list = if (type == CalendarType.MONTH) {
        genMonthData()
    } else {
        genWeekData()
    }
    val titleList = if (type == CalendarType.MONTH) {
        monthTitle
    } else {
        weeksTitle
    }
    val currentMonthTitle = rememberMutableStateOf { CalendarUtils.getCurrentMonthTitle(Date()) }
    val defaultDateSate = rememberMutableStateOf { defaultDate }
    val pagerState = rememberPagerState(getIndex(list), pageCount = { list.size })
    val gridStates = List(list.size) { rememberLazyGridState() }
    Column(
        modifier = modifier
    ) {
        if (type == CalendarType.MONTH) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(47.dp)
            ) {
                val coroutineScope = rememberCoroutineScope()
                Image(painter = painterResource(id = R.mipmap.ic_calendar_left_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 50.dp)
                        .clickable {
                            if (pagerState.currentPage - 1 < 0) {
                                Toast
                                    .makeText(context, "没有更多数据啦~", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                coroutineScope.launch {
                                    //带动画跳转
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            }

                        })

                Text(
                    fontSize = 18.sp,
                    color = AppText3,
                    text = currentMonthTitle.value,
                    fontWeight = FontWeight.Medium
                )
                Image(painter = painterResource(id = R.mipmap.ic_calendar_right_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 50.dp)
                        .clickable {
                            if (pagerState.currentPage + 1 >= list.size) {
                                Toast
                                    .makeText(context, "没有更多数据啦~", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                coroutineScope.launch {
                                    //带动画跳转
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        })
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(7), contentPadding = PaddingValues(vertical = 5.dp),

            ) {
            items(titleList.size) { index ->
                Text(
                    modifier = Modifier.wrapContentSize(Alignment.Center),
                    fontSize = 15.sp,
                    color = AppText3,
                    text = titleList[index],
                    textAlign = TextAlign.Center
                )
            }
        }
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                currentMonthTitle.value =
                    CalendarUtils.getCurrentMonthTitle(list[page].filter { it.inMonth }[0].date)
                onMonthChange?.let {
                    onMonthChange(
                        CalendarUtils.getCurrentDate(list[page][0].date),
                        CalendarUtils.getCurrentDate(list[page][list[page].size - 1].date)
                    )
                }
            }
        }
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(), state = pagerState, beyondBoundsPageCount = 0
        ) { page ->
            val gridState = gridStates[page]
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                state = gridState,
                userScrollEnabled = false,
                contentPadding = PaddingValues(vertical = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(list[page].size) { index ->
                    val currentDate = list[page][index]
                    currentDate.selected = CalendarUtils.areDatesEqualIgnoreTime(
                        defaultDateSate.value, currentDate.date
                    )
                    val currentDateStr = CalendarUtils.getCurrentDate(currentDate.date)
                    currentDate.flag = markDateList.contains(currentDateStr)
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.click {
                        onDateClick?.let {
                            onDateClick(CalendarUtils.getCurrentDate(currentDate.date))
                        }
                        defaultDateSate.value = currentDate.date
                    }) {
                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(if (currentDate.selected) 19.dp else 0.dp))
                                .background(getCalendarDateBgColor(currentDate))
                                .border(
                                    1.dp,
                                    if (currentDate.flag && type == CalendarType.MONTH) AppMain else translate,
                                    CircleShape
                                )
                                .size(38.dp)
                                .wrapContentSize(Alignment.Center)
                            ,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = getCalendarDateTextColor(currentDate),
                            text = if (currentDate.inToDay) "今" else CalendarUtils.getDayOfMonth(
                                currentDate.date
                            ).toString()
                        )
                        if (currentDate.flag && type == CalendarType.WEEK) {
                            // 使用 Canvas 绘制一个圆点
                            Canvas(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 2.dp)
                            ) {
                                drawCircle(
                                    color = AppMain,   // 圆点的颜色
                                    radius = 2.dp.toPx() // 圆点的半径
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, showSystemUi = true, device = PIXEL_TABLET)
fun HdCalendarView(
    //默认显示时间
    defaultDate: Date = Date(),
    //样式
    modifier: Modifier = Modifier.fillMaxWidth(),
    //标记日期
    markDateList: MutableList<String> = mutableListOf("2024-09-02"),
    //月份滑动回调
    onMonthChange: ((startDate: String, endDate: String) -> Unit)? = null,
    //日期点击回调
    onDateClick: ((date: String) -> Unit)? = null
) {
    // 获取当前的 Context
    val context = LocalContext.current
    val list = genMonthData()
    val titleList = monthTitle
    val currentMonthTitle = rememberMutableStateOf { CalendarUtils.getCurrentMonthTitle(Date()) }
    val defaultDateSate = rememberMutableStateOf { defaultDate }
    val pagerState = rememberPagerState(getIndex(list), pageCount = { list.size })
    val gridStates = List(list.size) { rememberLazyGridState() }
    Column(modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .height(61.dp)) {
            val coroutineScope = rememberCoroutineScope()
            Image(painter = painterResource(id = R.mipmap.ic_calendar_hd_left_arrow),
                contentDescription = null,
                modifier = Modifier.clickable {
                    if (pagerState.currentPage - 1 < 0) {
                        Toast.makeText(context, "没有更多数据啦~", Toast.LENGTH_SHORT).show()
                    } else {
                        coroutineScope.launch {
                            //带动画跳转
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                })
            Text(
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                color = AppText3,
                textAlign = TextAlign.Center,
                text = currentMonthTitle.value,
                fontWeight = FontWeight.Medium
            )
            Image(painter = painterResource(id = R.mipmap.ic_calendar_hd_right_arrow),
                contentDescription = null,
                modifier = Modifier.clickable {
                    if (pagerState.currentPage + 1 >= list.size) {
                        Toast.makeText(context, "没有更多数据啦~", Toast.LENGTH_SHORT).show()
                    } else {
                        coroutineScope.launch {
                            //带动画跳转
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                })
        }
        Column(modifier = Modifier) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(7)) {
                items(titleList.size) { index ->
                    Text(
                        modifier = Modifier
                            .height(45.dp)
                            .wrapContentSize(Alignment.Center),
                        fontSize = 15.sp,
                        color = AppText9,
                        text = titleList[index],
                        textAlign = TextAlign.Center
                    )
                }
            }
            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect { page ->
                    currentMonthTitle.value =
                        CalendarUtils.getCurrentMonthTitle(list[page].filter { it.inMonth }[0].date)
                    onMonthChange?.let {
                        onMonthChange(
                            CalendarUtils.getCurrentDate(list[page][0].date),
                            CalendarUtils.getCurrentDate(list[page][list[page].size - 1].date)
                        )
                    }
                }
            }
            HorizontalPager(
                modifier = Modifier.fillMaxWidth(), state = pagerState, beyondBoundsPageCount = 0
            ) { page ->
                val gridState = gridStates[page]
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    state = gridState,
                    userScrollEnabled = false,
                    contentPadding = PaddingValues(vertical = 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    items(list[page].size) { index ->
                        val currentDate = list[page][index]
                        currentDate.selected = CalendarUtils.areDatesEqualIgnoreTime(
                            defaultDateSate.value, currentDate.date
                        )
                        val currentDateStr = CalendarUtils.getCurrentDate(currentDate.date)
                        currentDate.flag = markDateList.contains(currentDateStr)
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.height(50.dp).click {
                            onDateClick?.let {
                                onDateClick(CalendarUtils.getCurrentDate(currentDate.date))
                            }
                            defaultDateSate.value = currentDate.date
                        }) {
                            Text(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(if (currentDate.selected) 19.dp else 0.dp))
                                    .background(getCalendarDateBgColor(currentDate))
                                    .border(
                                        1.dp,
                                        if (currentDate.flag ) AppMain else translate,
                                        CircleShape
                                    )
                                    .size(30.dp)
                                    .wrapContentSize(Alignment.Center),
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                                color = getCalendarDateTextColor(currentDate),
                                text = if (currentDate.inToDay) "今" else CalendarUtils.getDayOfMonth(
                                    currentDate.date
                                ).toString()
                            )
//                            if (currentDate.flag) {
//                                // 使用 Canvas 绘制一个圆点
//                                Canvas(
//                                    modifier = Modifier
//                                        .align(Alignment.BottomCenter)
//                                        .padding(bottom = 5.dp)) {
//                                    drawCircle(
//                                        color = AppMain,   // 圆点的颜色
//                                        radius = 2.dp.toPx() // 圆点的半径
//                                    )
//                                }
//                            }
                        }
                    }
                }
            }
        }
        Text(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp).height(72.dp),
            fontSize = 64.sp,
            color = AppText3,
            text = String.format("%02d", CalendarUtils.getDayOfMonth(defaultDateSate.value)),
            fontWeight = FontWeight.Medium
        )

        Text(
            fontSize = 14.sp,
            color = AppText3,
            text = CalendarUtils.getDayOfWeekStr(defaultDateSate.value),
        )

    }

}




