package com.zx.fitter.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


/**
 * @description:
 * @author: zhouxiang
 * @created: 2024/08/29 16:48
 * @version: V1.0
 */
object CalendarUtils {
    /**
     * 通过日期字符串解析为 Date 对象。
     * @param dateString 日期字符串
     * @return 解析后的 Date 对象，如果解析失败则返回 null
     */
    fun parseDate(dateString: String): Date {
        // 定义日期格式
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            // 解析日期字符串
            dateFormat.parse(dateString)
        } catch (e: ParseException) {
            // 解析失败时返回 null
            Date()
        }
    }
    /**
     * 获取给定日期的月份。
     *
     * @param date 要操作的日期
     * @return 月份（1 表示 1 月，12 表示 12 月）
     */
    fun getMonth(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date

        // 获取月份（月份从 0 开始，因此加 1）
        return calendar.get(Calendar.MONTH) + 1
    }
    
    /**
     * 获取给定日期的天数（即当月的第几天）。
     * @param date 要操作的日期
     * @return 当月的第几天
     */
    fun getDayOfMonth(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date

        // 获取天数
        return calendar.get(Calendar.DAY_OF_MONTH)
    }
    /**
     * 获取给定日期的年份。
     *
     * @param date 要操作的日期
     * @return 年份
     */
    fun getYear(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        // 获取年份
        return calendar.get(Calendar.YEAR)
    }
    /**
     * 获取给定日期的月份的总天数。
     * @param date 要操作的日期
     * @return 月份的总天数
     */
    fun getDaysInMonth(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date

        // 将日期设置为当前月份的第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        // 获取当前月份的最大天数
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }
    /**
     * 获取两个日期之间的月份
     * @param startDate
     * @param endDate
     * @returns
     */
    fun getMonthsBetween(start: Date, end: Date): List<Date> {
        val months = mutableListOf<Date>()
        val calendar = Calendar.getInstance()
        calendar.time = start
        calendar.set(Calendar.DAY_OF_MONTH, 1) // 确保从每个月的第一天开始

        while (calendar.time.before(end)) {
            months.add(calendar.time)
            calendar.add(Calendar.MONTH, 1) // 移动到下一个月
        }
        return months
    }
    /**
     * 获取两个日期之间的所有日期（天）的数组。
     *
     * @param startDate 起始日期
     * @param endDate 结束日期
     * @return 包含两个日期之间的所有日期的列表
     */
    fun getDaysBetweenAsArray(startDate: Date, endDate: Date): List<Date> {
        val dates = mutableListOf<Date>()
        val calendar = Calendar.getInstance()
        calendar.time = startDate

        while (calendar.time <= endDate) {
            dates.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)  // 增加一天
        }

        return dates
    }

    /**
     * 是否是今天
     * @param item
     * @returns
     */
    fun isToday(date: Date): Boolean {
        // 获取当前日期
        val today = Calendar.getInstance()

        // 获取要检查的日期
        val calendar = Calendar.getInstance()
        calendar.time = date

        // 比较年份、月份和日期
        return today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
    }

    /**
     * 获取给定日期的上个月的日期。
     *
     * @param date 要操作的日期
     * @return 上个月的日期
     */
    fun getPreviousMonthDate(date: Date): Date {
        // 使用 Calendar 设置给定日期
        val calendar = Calendar.getInstance()
        calendar.time = date
        // 设置为上个月
        calendar.add(Calendar.MONTH, -1)
        // 返回上个月的日期
        return calendar.time
    }

    /**
     * 获取给定日期的下个月的日期。
     *
     * @param date 要操作的日期
     * @return 下个月的日期
     */
    fun getNextMonthDate(date: Date): Date {
        // 使用 Calendar 设置给定日期
        val calendar = Calendar.getInstance()
        calendar.time = date

        // 设置为下个月
        calendar.add(Calendar.MONTH, 1)

        // 返回下个月的日期
        return calendar.time
    }
    /**
     * 获取给定日期的星期几。
     * @param date 要操作的日期
     * @return 星期几的名称（例如 "星期一"）
     */
    fun getDayOfWeekStr(date: Date): String {
        // 使用 Calendar 设置给定日期
        val calendar = Calendar.getInstance()
        calendar.time = date

        // 获取星期几（1表示星期日，2表示星期一，以此类推）
        val dayOfWeekIndex = calendar.get(Calendar.DAY_OF_WEEK)

        // 将星期几的索引转换为星期几的名称
        return when (dayOfWeekIndex) {
            Calendar.SUNDAY -> "周日"
            Calendar.MONDAY ->  "周一"
            Calendar.TUESDAY ->  "周二"
            Calendar.WEDNESDAY ->  "周三"
            Calendar.THURSDAY ->  "周四"
            Calendar.FRIDAY ->  "周五"
            Calendar.SATURDAY ->  "周六"
            else ->  ""
        }
    }


    /**
     * 获取给定日期的星期几。
     *
     * @param date 要操作的日期
     * @return 星期几的名称（例如 "星期一"）
     */
    fun getDayOfWeek(date: Date): Int {
        // 使用 Calendar 设置给定日期
        val calendar = Calendar.getInstance()
        calendar.time = date

        // 获取星期几（1表示星期日，2表示星期一，以此类推）
        val dayOfWeekIndex = calendar.get(Calendar.DAY_OF_WEEK)

        // 将星期几的索引转换为星期几的名称
        return when (dayOfWeekIndex) {
            Calendar.SUNDAY -> 7
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            else -> 0
        }
    }

    /**
     * 获取给定日期前几天的日期。
     *
     * @param date 要操作的日期
     * @param daysAgo 要计算的天数
     * @return 前几天的日期
     */
    fun getDateDaysAgo(date: Date, daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date

        // 减去指定的天数
        calendar.add(Calendar.DAY_OF_MONTH, -daysAgo)

        // 返回计算后的日期
        return calendar.time
    }

    /**
     * 获取当前月的第一天是星期几。
     * @return
     */
    fun getFirstDayOfWeekInCurrentMonth(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        // 设置为当前月的第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        // 获取星期几
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> 7
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            else -> 0
        }
    }
    /**
     * 获取日历标题
     * @param date 要操作的日期
     * @return 日期的字符串表示，格式为 "yyyy年MM月"
     */
    fun getCurrentMonthTitle(date: Date): String {
        // 创建 SimpleDateFormat 对象，指定日期格式
        val dateFormat = SimpleDateFormat("yyyy年MM月")
        // 使用 SimpleDateFormat 格式化 Date 对象
        return dateFormat.format(date)
    }

    /**
     * 获取日历标题
     * @param date 要操作的日期
     * @return 日期的字符串表示，格式为 "yyyy年MM月"
     */
    fun getCurrentDate(date: Date): String {
        // 格式化时间
        val format: String = "yyyy-MM-dd"
        // 创建 SimpleDateFormat 对象，指定日期格式
        val dateFormat = SimpleDateFormat(format)
        // 使用 SimpleDateFormat 格式化 Date 对象
        return dateFormat.format(date)
    }
    /**
     * 比较两个 Date 对象的年月日部分是否相等，忽略时间部分。
     *
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return 如果两个日期的年月日部分相等，返回 true；否则返回 false
     */
    fun areDatesEqualIgnoreTime(date1: Date, date2: Date): Boolean {
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()

        calendar1.time = date1
        calendar2.time = date2

        // 比较年份、月份和日期
        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH))
    }
    /**
     * 获取给定日期后几天的日期。
     *
     * @param date 要操作的日期
     * @param daysAhead 要计算的天数
     * @return 后几天的日期
     */
    fun getDateDaysAhead(date: Date, daysAhead: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date

        // 增加指定的天数
        calendar.add(Calendar.DAY_OF_MONTH, daysAhead)

        // 返回计算后的日期
        return calendar.time
    }
    /**
     * 将 MutableList 分成多个同等长度的 MutableList。
     *
     * @param chunkSize 每个子列表的长度
     * @return 分割后的 MutableList 列表
     */
    fun <T> MutableList<T>.splitIntoChunks(chunkSize: Int): MutableList<MutableList<T>> {
        require(chunkSize > 0) { "Chunk size must be greater than 0" }

        val result = mutableListOf<MutableList<T>>()
        var startIndex = 0

        while (startIndex < this.size) {
            // 计算结束索引
            val endIndex = (startIndex + chunkSize).coerceAtMost(this.size)
            // 创建子列表并添加到结果列表
            result.add(this.subList(startIndex, endIndex).toMutableList())
            // 更新开始索引
            startIndex = endIndex
        }

        return result
    }
}