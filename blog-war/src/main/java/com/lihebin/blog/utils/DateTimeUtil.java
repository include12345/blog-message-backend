package com.lihebin.blog.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    public static long ONE_DAY_MILLIS = 1000 * 24 * 60 * 60L;
    public static String[] weekdays = {"日", "一", "二", "三", "四", "五", "六"};
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
    private static final DateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat datetimeCustomFormat = new SimpleDateFormat("yyyyMMddHHmmss");



    public static long StringToLong(String time) throws ParseException {
        return datetimeFormat.parse(time).getTime();
    }


    public static boolean betweenTime(String dateTime, int hoursStart, int hoursEnd) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalTime start = LocalTime.of(hoursStart, 0);
        LocalTime end;
        if (hoursEnd == 24) {
            end = LocalTime.of(23, 59, 59, 999_999_999);
        } else {
            end = LocalTime.of(hoursEnd, 0);
        }
        LocalDateTime ldt = LocalDateTime.parse(dateTime, df);
        LocalTime now = ldt.toLocalTime();
        return now.isAfter(start) && now.isBefore(end);
    }


    public static boolean betweenTimeMin(String dateTime, int hoursStart, int minStart, int hoursEnd, int minEnd) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalTime start = LocalTime.of(hoursStart, minStart);
        LocalTime end;
        if (hoursEnd == 24) {
            end = LocalTime.of(23, 59, 59, 999_999_999);
        } else {
            end = LocalTime.of(hoursEnd, minEnd);
        }
        LocalDateTime ldt = LocalDateTime.parse(dateTime, df);
        LocalTime now = ldt.toLocalTime();
        return now.isAfter(start) && now.isBefore(end);
    }


    /**
     * 计算时间差
     *
     * @param timeStart
     * @param timeEnd
     * @return
     */
    public static String getTimeBetween(long timeStart, long timeEnd) {
        long between = timeEnd - timeStart;
        long days = between / (1000 * 60 * 60 * 24);
        long hours = (between - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (between - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        long s = (between - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
        return "" + days + "天" + hours + "小时" + minutes + "分" + s + "秒";
    }

    // 返回当前timestamp所代表的天
    public static long getDate(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long result = c.getTimeInMillis();
        return result;
    }

    // 返回当天最早的时间
    public static long getOneDayStart(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    // 返回当天最后的时间
    public static long getOneDayEnd(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 59);
        return c.getTimeInMillis();
    }

    public static int getHour(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static String getMonthString(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return String.format("%d月", c.get(Calendar.MONTH) + 1);
    }

    public static int getMonth(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getYear(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return c.get(Calendar.YEAR);
    }


    public static String getDayOfMonthString(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return String.format("%d日", c.get(Calendar.DAY_OF_MONTH));
    }

    public static int getDayOfMonth(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static String getDayOfWeekString(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return weekdays[c.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public static String getTimeOfDayString(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return String.format("%02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
    }

    /**
     * @param timestamp
     * @return "xx年xx月xx日 xx:xx"
     */
    public static String getFullTimeString(long timestamp) {
        Calendar c = Calendar.getInstance();
        String today = String.format("%s.%s.%s", c.get(Calendar.YEAR), (c.get(Calendar.MONTH) + 1), c.get(Calendar.DAY_OF_MONTH));
        c.setTimeInMillis(timestamp);
        String time = String.format("%s.%s.%s", c.get(Calendar.YEAR), (c.get(Calendar.MONTH) + 1), c.get(Calendar.DAY_OF_MONTH));
        if (today.equals(time)) {
            return "今天 " + String.format("%02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        } else {
            return String.format("%s.%s.%s  %02d:%02d", c.get(Calendar.YEAR), (c.get(Calendar.MONTH) + 1), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        }
    }

    /**
     * @param timestamp
     * @return "xx年xx月xx日"
     */
    public static String getSimpleTimeString(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return String.format("%s年%s月%s日", c.get(Calendar.YEAR), (c.get(Calendar.MONTH) + 1), c.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * @param timestamp
     * @return "xx年xx月xx日"
     */
    public static String getBirthdayString(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return String.format("%s月%s日", (c.get(Calendar.MONTH) + 1), c.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * @param timestamp
     * @return "xxxx-xx-xx xx:xx:xx"
     */
    public static String getDigitalTimeString(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return String.format("%s.%s.%s  %02d:%02d:%02d",
                c.get(Calendar.YEAR),
                (c.get(Calendar.MONTH) + 1),
                c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                c.get(Calendar.SECOND));
    }

    /**
     * @param timestamp
     * @return "xxxxxxxxxxxxxx"
     */
    public static String getTimeString(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return String.format("%02d%02d%02d%02d%02d%02d",
                c.get(Calendar.YEAR),
                (c.get(Calendar.MONTH) + 1),
                c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                c.get(Calendar.SECOND));
    }

    /**
     * @param timestamp
     * @return "YYYY-MM-DD"
     */
    public static String getSimpleDigitalTimeString(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return String.format("%s.%s.%s", c.get(Calendar.YEAR), (c.get(Calendar.MONTH) + 1), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 将时间戳转换为String类型
     *
     * @param timestamp
     * @return
     */
    public static String format(long timestamp) {
        return dateFormat.format(new Date(timestamp));
    }

    /**
     * 将时间戳转换为String类型(yyyy-MM-dd HH:mm:ss)
     *
     * @param timestamp
     * @return
     */
    public static String dateFormat(long timestamp) {
        return datetimeFormat.format(new Date(timestamp));
    }


    public static String datetimeCustomFormat(long timestamp) {
        return datetimeCustomFormat.format(new Date(timestamp));
    }


    /**
     * 计算两个日期之间相差的天数 timeFrom < timeTo
     *
     * @param timeFrom
     * @param timeTo
     * @return
     */
    public static int getDaysBetween(long timeFrom, long timeTo) {

        long between_days = (timeTo - timeFrom) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Calendar getTodayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar;
    }

    //FIXME: 应该实现为天减一，否则在闰秒时出错
    public static Calendar getYesterdayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis() - 24L * 60L * 60L * 1000L);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar;
    }

    //FIXME: 应该实现为天减一，否则在闰秒时出错
    public static Calendar getTomorrowStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis() + 24L * 60L * 60L * 1000L);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar;
    }

    public static Calendar getThisWeekStart() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        if (dayOfWeek == 1) {
            // 周日，需要特殊处理
            calendar.setTimeInMillis(calendar.getTimeInMillis() - 6L * 24L * 60L * 60L * 1000L);
        } else {
            calendar.setTimeInMillis(calendar.getTimeInMillis() - (dayOfWeek - 2) * 24L * 60L * 60L * 1000L);
        }
        // calendar.

        return calendar;
    }

    public static Calendar getThisMonthStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar;
    }

    public static Calendar getThisQuarterStart() {
        Calendar calendar = Calendar.getInstance();
        int monthOfYear = calendar.get(Calendar.MONTH);
        monthOfYear /= 3;
        monthOfYear *= 3;
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar;
    }

    public static Calendar getLastQuarterStart() {
        Calendar calendar = getThisQuarterStart();
        calendar.add(Calendar.MONTH, -3);
        return calendar;
    }

    public static Calendar getLastQuarterEnd() {
        Calendar calendar = getThisQuarterStart();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar;
    }

    public static String formatDate(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(calendar.getTime());
    }

    public static String getToday() {
        return formatDate(getTodayStart());
    }

    /**
     * 把某一日期加上或者减去多少个月
     *
     * @param time
     * @param month
     * @return
     */
    public static Date addMonth(Date time, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * String日期转换为Long
     * * @param formatDate("MM/dd/yyyy HH:mm:ss")
     * * @param date("3/19/2015 00:00:00")
     * * @return
     * * @throws ParseException
     */
    public static long stringToLong()
            throws ParseException {
        Calendar calendar = Calendar.getInstance();
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (month.length() < 2) {
            month = "0" + month;
        }
        String today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (today.length() < 2)
            today = "0" + today;
        String day = month + "/" + today + "/" + calendar.get(Calendar.YEAR);
        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date dt = sdf.parse(day + " 00:00:00");
        return dt.getTime();
    }

    /**
     * 取得某一天开始的日期
     *
     * @param time
     * @return
     */
    public static Date getDayStart(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar.getTime();
    }

    /**
     * 取得某一天的下一天的开始日期
     *
     * @param time
     * @return
     */
    public static Date getNextDayStart(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDayStart(time));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();

    }

    /**
     * 获取某一天所在月份的开始日期
     *
     * @param time
     * @return
     */
    public static Date getMonthStart(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar.getTime();
    }

    /**
     * 获取某一天所在月份的下一月开始日期
     *
     * @param time
     * @return
     */
    public static Date getNextMonthStart(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }


}
