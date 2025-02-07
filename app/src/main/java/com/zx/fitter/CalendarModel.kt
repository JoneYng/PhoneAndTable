package com.zx.fitter


import com.zx.fitter.utils.CalendarUtils
import com.zx.fitter.utils.CalendarUtils.splitIntoChunks
import com.zx.fitter.view.CalendarItem
import java.util.Date


/**
 * 获取当前时间默认显示的位置
 * @param allDateList
 * @returns
 */
fun getIndex(allDateList: MutableList<MutableList<CalendarItem>>): Int {
    allDateList.forEachIndexed { pIndex, calendarItems ->
        calendarItems.forEachIndexed { index, calendarItem ->
            if (CalendarUtils.isToday(calendarItem.date) && calendarItem.inMonth) {
                return pIndex
            }
        }
    }
    return 0
}

/**
 * 获取月视图数据
 * @param startTime 开始时间
 * @param endTime   结束时间
 * @returns
 */
fun genMonthData(
    startTime: Date? = null, endTime: Date? = null
): MutableList<MutableList<CalendarItem>> {
    //月份总列表
    val allDateList: MutableList<MutableList<CalendarItem>> = mutableListOf()
    val currentYear = CalendarUtils.getYear(Date())
    val startDate = startTime ?: CalendarUtils.parseDate("${currentYear-1}-01-01")//开始时间
    val endDate = endTime ?: CalendarUtils.parseDate("${currentYear+1}-12-31")//结束时间
    //获取两个日期之间的日期
    val dateList = CalendarUtils.getMonthsBetween(startDate, endDate)
    dateList.forEachIndexed { index, date ->
        allDateList.add(getItemMonthData(date))
    }

    return allDateList
}

/**
 * 获取单月数据
 * @param current  当前月份
 * @returns
 */
fun getItemMonthData(currentDate: Date): MutableList<CalendarItem> {
    val items: MutableList<CalendarItem> = mutableListOf()
    // 补齐上个月 month
    val prevMonth = CalendarUtils.getPreviousMonthDate(currentDate)
    //上个月年份
    val prevMonthYear = CalendarUtils.getYear(prevMonth).toString()
    //上个月月份
    val prevMonthMonth = CalendarUtils.getMonth(prevMonth)
    // 获取当前月份的天数
    val prevMonthLastDate = CalendarUtils.getDaysInMonth(prevMonth)
    //获取当前月的第一天是星期几
    val prevDays = CalendarUtils.getFirstDayOfWeekInCurrentMonth(currentDate)
    if (prevDays < 7) {
        for (index in 1..prevDays) {
            val date = prevMonthYear + '-' + prevMonthMonth + '-' + (prevMonthLastDate - index + 1)
            items.add(CalendarItem(date = CalendarUtils.parseDate(date), inMonth = false))
        }
    }

    val LEN = 35
    // 获取当前月份的天数
    val daysInMonth = CalendarUtils.getDaysInMonth(currentDate)
    // 获取当前年份
    val currentYear = CalendarUtils.getYear(currentDate)
    // 获取当前月份
    val currentMonth = CalendarUtils.getMonth(currentDate)
    //当前月日期
    for (index in 1..daysInMonth) {
        val date = "$currentYear-$currentMonth-$index"
        val item = CalendarUtils.parseDate(date)
        items.add(
            CalendarItem(
                date = item, inMonth = true, selected = CalendarUtils.isToday(item),//默认选中当前天
                inToDay = CalendarUtils.isToday(item)//是否是今天
            )
        )
    }

    // 补齐下个月 month
    val nextMonth = CalendarUtils.getNextMonthDate(currentDate)
    // 获取当前年份
    val nextMonthYear = CalendarUtils.getYear(nextMonth).toString()
    // 获取当前月份
    val nextMonthMonth = CalendarUtils.getMonth(nextMonth)
    val appends = LEN - items.size
    for (index in 1..appends) {
        val date = "$nextMonthYear-$nextMonthMonth-$index"
        items.add(CalendarItem(date = CalendarUtils.parseDate(date), inMonth = false))
    }
    return items
}

/**
 * 获取周视图日历数据
 * @param prevNum 前几周
 * @param nextNum 后几周
 * @returns 默认获取当前日期的前7和后7天
 */
fun genWeekData(prevNum: Int = 1, nextNum: Int = 1): MutableList<MutableList<CalendarItem>> {
    var allDateList: MutableList<MutableList<CalendarItem>> = mutableListOf()
    val items: MutableList<CalendarItem> = mutableListOf()
    //当前日期
    val currDate = Date()
    val currWeeks = CalendarUtils.getDayOfWeek(currDate)//星期几
    //获取前几天的日期
    val startDate = CalendarUtils.getDateDaysAgo(currDate, (currWeeks - 1) + 7 * prevNum)
    //获取后几天的日期
    val endDate = CalendarUtils.getDateDaysAhead(currDate, (7 - currWeeks) + 7 * nextNum)
    //获取两个日期之间的日期
    val dateList = CalendarUtils.getDaysBetweenAsArray(startDate, endDate)
    dateList.forEachIndexed { index, date ->
        items.add(
            CalendarItem(
                date = date,
                inMonth = true, //周日历默认都可以选中
                selected = CalendarUtils.isToday(date), //默认选中当前天
                inToDay = CalendarUtils.isToday(date), //是否是今天
            )
        )

    }
    // 将数组分成多个同等长度的数组
    allDateList = items.splitIntoChunks(7)
    return allDateList
}