package com.zx.fitter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zx.fitter.ui.theme.AppMainBackGround
import com.zx.fitter.ui.theme.AppText3
import com.zx.fitter.ui.theme.PhoneAndTableTheme
import com.zx.fitter.view.CalendarType
import com.zx.fitter.view.EmptyView
import com.zx.fitter.view.HdCalendarView
import com.zx.fitter.view.PhoneCalendarView
import java.util.Date

class ComposeCalendarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhoneAndTableTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(

                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    fun Greeting() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
                .background(AppMainBackGround),
        ) {
            Text("课程日历")
            val windowSizeClass = calculateWindowSizeClass(this@ComposeCalendarActivity)
            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> LiveCalendarPhonePage()
                WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> LiveCalendarPadPage()
                else -> {
                    LiveCalendarPadPage()
                }
            }
        }

    }

    @Composable
    fun LiveCalendarPhonePage() {
        //默认时间
        PhoneCalendarView(
            defaultDate = Date(),
            type = CalendarType.MONTH,
            onMonthChange = fun(startDate: String, endDate: String) {
            },
            onDateClick = fun(date: String) {
                Log.i("onDateClick", "onDateClick===$date")
            },
            markDateList = mutableListOf()
        )
        //直播数据列表
        val liveCourseList: MutableList<String> = mutableListOf()
        if (liveCourseList.isNotEmpty()) {

        } else {
            EmptyView(
                modifier = Modifier.padding(top = 20.dp),
                emptyText = "今日没有直播课哦~ 自主安排学习吧"
            )
        }
    }


    @Composable
    fun LiveCalendarPadPage() {
        Row {
            HdCalendarView(
                defaultDate = Date(),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .weight(1.5f)
                    .background(Color.White, RoundedCornerShape(8.dp)),
                onMonthChange = fun(startDate: String, endDate: String) {
                },
                onDateClick = fun(date: String) {
                    Log.i("onDateClick", "onDateClick===$date")
                },
                markDateList = mutableListOf()
            )
            //直播数据列表
            val liveCourseList: MutableList<String> = mutableListOf()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .weight(2f)
                    .background(Color.White, RoundedCornerShape(8.dp))
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    fontSize = 20.sp,
                    color = AppText3,
                    fontWeight = FontWeight.Medium,
                    text = "今日课程"
                )
                if (liveCourseList.isNotEmpty()) {

                } else {
                    EmptyView(
                        top = 100.dp,
                        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                        emptyText = "今日没有直播课哦~ 自主安排学习吧"
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PhoneAndTableTheme {
            Greeting()
        }
    }
}

