package com.ctrun.view.cateye.refresh.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ctrun on 2017/9/20.
 */

public class DateUtils {
    private static SimpleDateFormat sYMDHMFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy-MM");

    private static SimpleDateFormat sHMFormat = new SimpleDateFormat("HH:mm");

    private static SimpleDateFormat sMonthDayFormat = new SimpleDateFormat("MM月dd日");
    private static SimpleDateFormat sMonthDay2Format = new SimpleDateFormat("MM-dd");
    private static SimpleDateFormat sMonthFormat = new SimpleDateFormat("MM月");
    private static SimpleDateFormat sYearMonthFormat = new SimpleDateFormat("yyyy年M月");

    private static SimpleDateFormat sWeekMonthDayFormat = new SimpleDateFormat("EMM月dd日");

    public static long timeFromDate(String date) throws ParseException {
        return sDateFormat.parse(date).getTime();
    }

    public static long timeFromHM(String time) throws ParseException {
        return sHMFormat.parse(time).getTime();
    }

    public static long timeFromYMDHM(String time) throws ParseException {
        return sYMDHMFormat.parse(time).getTime();
    }

    /**
     * 获取近期最受期待上线时间
     * 格式1：2017-10
     * 格式2：2017-09-30
     */
    public static String getRecentExpectShowTime(String date) {
        try {
            long time = sDateFormat.parse(date).getTime();
            return sMonthDayFormat.format(time);
        } catch (ParseException e1) {
            e1.printStackTrace();
            try {
                long time = sSimpleDateFormat.parse(date).getTime();
                return sMonthFormat.format(time) + "待定";
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }

        return "";
    }

    public static String dateFromTime(long time) {
        return sDateFormat.format(time);
    }

    public static String hourMinuteFromTime(long time) {
        return sHMFormat.format(time);
    }

    public static String monthDayFromTime(long time) {
        return sMonthDayFormat.format(time);
    }

    public static String monthDayFromTime2(long time) {
        return sMonthDay2Format.format(time);
    }

    public static String weekMonthDayFromTime(long time) {
        return sWeekMonthDayFormat.format(time);
    }

    public static String dayToNow(String time) {
        try {
            long timeInMillis = timeFromYMDHM(time);
            return dayToNow(timeInMillis);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String dayToNow(long time) {
        Calendar now = Calendar.getInstance();

        long minute = (now.getTimeInMillis() - time) / 60000;
        if (minute < 60) {
            if (minute == 0) {
                return "刚刚";
            } else {
                return minute + "分钟前";
            }
        }

        long hour = minute / 60;
        if (hour < 24) {
            return hour + "小时前";
        }

        long day = hour / 24;
        if (day < 30) {
            return day + "天前";
        }

        long month = day / 30;
        if (month < 12) {
            return month + "个月前";
        }

        long year = month / 12;
        return year + "年前";
    }

    public static String dayToMonth(long timeMillis, boolean showCurrentMonth) {
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);

        calendar.setTimeInMillis(timeMillis);
        //今年
        if (thisYear == calendar.get(Calendar.YEAR)) {
            if (thisMonth == calendar.get(Calendar.MONTH)) {
                //本月
                return showCurrentMonth ? "本月" : (calendar.get(Calendar.MONTH) + 1) + "月";
            } else {
                //不是本月
                return (calendar.get(Calendar.MONTH) + 1) + "月";
            }
        } else {
            //不是今年
            return sYearMonthFormat.format(timeMillis);
        }
    }

    public static String secondToMinute(int seconds) {
        if (seconds < 60) {
            return String.format("00:%s", seconds);
        } else {
            if (seconds / 60 <= 10) {
                return String.format("0%s:%s", seconds / 60, seconds % 60);
            } else {
                return String.format("%s:%s", seconds / 60, seconds % 60);
            }
        }
    }

    public static boolean isBeforeToday(long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return timeMillis < calendar.getTimeInMillis();
    }

    public static boolean isAfterToday(long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return timeMillis >= calendar.getTimeInMillis();
    }

    public static boolean isToday(long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);

        int thenYear = calendar.get(Calendar.YEAR);
        int thenMonth = calendar.get(Calendar.MONTH);
        int thenMonthDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTimeInMillis(System.currentTimeMillis());

        return (thenYear == calendar.get(Calendar.YEAR))
                && (thenMonth == calendar.get(Calendar.MONTH))
                && (thenMonthDay == calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 是否是明天
     * @param timeMillis
     * @return
     */
    public static boolean isTomorrow(long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);

        int thenYear = calendar.get(Calendar.YEAR);
        int thenMonth = calendar.get(Calendar.MONTH);
        int thenMonthDay = calendar.get(Calendar.DAY_OF_MONTH);

        final long oneDayMillis = 1000 * 3600 * 24;
        calendar.setTimeInMillis(System.currentTimeMillis() + oneDayMillis);

        return (thenYear == calendar.get(Calendar.YEAR))
                && (thenMonth == calendar.get(Calendar.MONTH))
                && (thenMonthDay == calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 是否是后天
     * @param timeMillis
     * @return
     */
    public static boolean isTomorrowNext(long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);

        int thenYear = calendar.get(Calendar.YEAR);
        int thenMonth = calendar.get(Calendar.MONTH);
        int thenMonthDay = calendar.get(Calendar.DAY_OF_MONTH);

        final long twoDayMillis = 1000 * 3600 * 24 * 2;
        calendar.setTimeInMillis(System.currentTimeMillis() + twoDayMillis);

        return (thenYear == calendar.get(Calendar.YEAR))
                && (thenMonth == calendar.get(Calendar.MONTH))
                && (thenMonthDay == calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 是否是本周
     * @param millis
     * @return
     */
    public static boolean isThisWeek(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        final long oneWeek = 1000 * 60 * 60 * 24 * 7;

        long weekBegin = calendar.getTimeInMillis();
        long nextWeekBegin = weekBegin + oneWeek;

        return millis>=weekBegin && millis<=nextWeekBegin;
    }

    public static boolean isThisYear(String date) {
        try {
            Calendar calendar = Calendar.getInstance();
            int thisYear = calendar.get(Calendar.YEAR);

            long timeMillis = timeFromDate(date);
            calendar.setTimeInMillis(timeMillis);

            return thisYear == calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
}
