package com.example.jddemo.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtil {

	public static final String FORMAT_DATE_TIME_MILLI = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATE_TIME_NO = "yyyyMMddHHmmss";
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_DATE_NO = "yyyyMMdd";
	public static final String FORMAT_TIME_MILLI = "HH:mm:ss.SSS";
	public static final String FORMAT_TIME = "HH:mm:ss";

	public static final long MILLISECOND_OF_SECOND = 1000;
	public static final long MILLISECOND_OF_MINUTE = MILLISECOND_OF_SECOND * 60;
	public static final long MILLISECOND_OF_HOUR = MILLISECOND_OF_MINUTE * 60;
	public static final long MILLISECOND_OF_DAY = MILLISECOND_OF_HOUR * 24;
	public static final long MILLISECOND_OF_WEEK = MILLISECOND_OF_DAY * 7;

	public DateUtil() {
	}

	public static String format(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date parse(String date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 添加或减少周
	 */
	public static Date addWeek(Date date, int weeks) {
		if (date != null) {
			return new Date(date.getTime() + weeks * MILLISECOND_OF_WEEK);
		}
		return date;
	}

	/**
	 * 添加或减少天数
	 */
	public static Date addDay(Date date, int days) {
		if (date != null) {
			return new Date(date.getTime() + days * MILLISECOND_OF_DAY);
		}
		return date;
	}

	/**
	 * 添加或减少小时
	 */
	public static Date addHour(Date date, int hours) {
		if (date != null) {
			return new Date(date.getTime() + hours * MILLISECOND_OF_HOUR);
		}
		return date;
	}

	public static Date addMonth(Date date, int month){
		Date dNow = date;   //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.MONTH, month);
		dBefore = calendar.getTime();
		return dBefore;

	}

	/**
	 * 添加或减少月
	 */
	public static Date addMonths(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * 添加或减少分钟
	 */
	public static Date addMinutes(Date date, int minutes) {
		if (date != null) {
			return new Date(date.getTime() + minutes * MILLISECOND_OF_MINUTE);
		}
		return date;
	}
	public static Date addSecond(Date date, int seconds) {
		if (date != null) {
			return new Date(date.getTime() + seconds * MILLISECOND_OF_SECOND);
		}
		return date;
	}

	/**
	 * 获得某个月的第一天0时0分0秒的时间
	 *
	 * @param year
	 * @param month
	 * @return java.util.Date
	 */
	public static Date getFirstDateOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}

	/**
	 * 获取某一天的0时0分0秒的时间
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getFirstTimeOfDay(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}

	/**
	 * 获取某一天的0时0分0秒的时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstTimeOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取某一天的23时59分59秒的时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastTimeOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 获得Date型对象，根据时间点
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getDateFromTime(int year, int month, int day, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);

		return cal.getTime();
	}

	/**
	 * 将util型的日期型的数据转换为sql型的日期数据
	 *
	 * @param date
	 *            java.util.Date
	 * @return java.sql.Date
	 */
	public static java.sql.Date utilToSql(Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 将sql型的日期型的数据转换为util型的日期数据
	 *
	 * @param sqlDate
	 *            java.sql.Date
	 * @return java.util.Date
	 */
	public static Date sqlToUtil(java.sql.Date sqlDate) {
		return new Date(sqlDate.getTime());
	}

    /**
     * 将时间字符串进行相加后操作后格式化输出
     * @param timeStr 格式<HH:mm>
     * @param hour
     * @param minute
     * @param second
     * @return 格式<HH:mm>
     */
    public static String timeOperate(String timeStr, int hour, int minute, int second){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String[] times = timeStr.trim().split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(times[1]));
        calendar.add(Calendar.HOUR_OF_DAY,hour);
        calendar.add(Calendar.MINUTE,minute);
        calendar.add(Calendar.SECOND,second);
        return df.format(calendar.getTime());

    }

    /**
     * 根据指定string生成当天时间
     * @param timeStr 格式<HH:mm>
     * @return 秒和毫秒时间为0
     */
    public static Date getCurrentDayTimeByStr(String timeStr) {
        String[] times = timeStr.trim().split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(times[1]));
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

	public static String transferLongToDate(String dateFormat, Long millSec){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date= new Date(millSec);
		return sdf.format(date);
	}

}
