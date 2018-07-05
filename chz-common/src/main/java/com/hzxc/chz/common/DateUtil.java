package com.hzxc.chz.common;


import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	/**
	 * milliseconds in a second.
	 */
	public static final long SECOND = 1000;

	/**
	 * milliseconds in a minute.
	 */
	public static final long MINUTE = SECOND * 60;

	/**
	 * milliseconds in a hour.
	 */
	public static final long HOUR = MINUTE * 60;

	/**
	 * milliseconds in a day.
	 */
	public static final long DAY = 24 * HOUR;

	/** time_begin */
	public static final String TIME_BEGIN = " 00:00:00";

	/** time_end */
	public static final String TIME_END = " 23:59:59";

	/** date format yyyyMMdd */
	public static final String MONTH_PATTERN = "yyyy-MM";

	/** date format yyyyMMdd */
	public static final String DEFAULT_PATTERN = "yyyyMMdd";

	/** date format yyyyMMddHHmmss */
	public static final String FULL_PATTERN = "yyyyMMddHHmmss";

	/** date format yyyyMMdd HH:mm:ss */
	public static final String FULL_STANDARD_PATTERN = "yyyyMMdd HH:mm:ss";

	/** date format yyyy-MM-dd */
	public static final String TRADITION_PATTERN = "yyyy-MM-dd";

	/** date format yyyy-MM-dd HH:mm:ss */
	public static final String FULL_TRADITION_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/** date format yyyy/MM/dd */
	public static final String DIAGONAL_PATTERN = "yyyy/MM/dd";

	/** date format yyyy/MM/dd HH:mm:ss */
	public static final String FULL_DIAGONAL_PATTERN = "yyyy/MM/dd HH:mm:ss";

	/** date format MM-dd HH:mm */
	public static final String NOYEAR_NOSECOND_PATTERN = "MM-dd HH:mm";

	public static final String YEARMONTH_PATTERN = "yyMM";

	/** date format yyMMdd */
	public static final String YYMMDD_PATTERN ="yyMMdd";

	private static final ThreadLocal<Map<String, SimpleDateFormat>> threadLocalFormat = new ThreadLocal<Map<String, SimpleDateFormat>>();

	public static SimpleDateFormat getSimpleDateFormat(String format) {
		Map<String, SimpleDateFormat> map = threadLocalFormat.get();
		if (map == null) {
			map = new HashMap<String, SimpleDateFormat>();
			threadLocalFormat.set(map);
		}
		SimpleDateFormat sdf = map.get(format);
		if (sdf == null) {
			sdf = new SimpleDateFormat(format);
			map.put(format, sdf);
		}
		return sdf;
	}

	/**
	 * 返回中文格式的当前日期
	 * 
	 * @return [yyyy-mm-dd]
	 */
	public static String getShortNow() {
		return formatDate(TRADITION_PATTERN);
	}

	/**
	 * 返回当前时间24小时制式
	 * 
	 * @return [H:mm]
	 */

	public static String getTimeBykm() {
		return formatDate("H:mm");
	}

	/**
	 * 返回当前月份
	 * 
	 * @return [MM]
	 */

	public static String getMonth() {
		return formatDate("MM");
	}

	/**
	 * 返回当前日
	 * 
	 * @return [dd]
	 */

	public static String getDay() {
		return formatDate("dd");
	}

	/**
	 * Format date as "yyyyMMdd".
	 * 
	 * @param date
	 *            日期 @see Date
	 * @return 格式化后的日期字符串
	 */
	public static String formatDate(final Date date) {
		return formatDate(date, DEFAULT_PATTERN);
	}

	/**
	 * Format date as given date format.
	 * 
	 * @param date
	 *            日期 @see Date
	 * @param format
	 *            日期格式
	 * @return 格式化后的日期字符串，如果<code>date</code>为<code>null</code>或者
	 *         <code>format</code>为空，则返回<code>null</code>。
	 */
	public static String formatDate(final Date date, String format) {
		if (null == date || StringUtils.isBlank(format))
			return null;
		try {
			return getSimpleDateFormat(format).format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Format the current date.
	 * 
	 * @param format
	 *            日期格式
	 * @return 格式化后的日期字符串
	 */
	public static String formatDate(String format) {
		return formatDate(new Date(), format);
	}

	/**
	 * change the string to date
	 * 
	 * @param date
	 *            the date string
	 * @return Date if failed return <code>null</code>
	 */
	public static Date parseDate(String date) {
		return parseDate(date, DEFAULT_PATTERN, null);
	}

	/**
	 * change the string to date
	 * 
	 * @param date
	 *            String
	 * @param df
	 *            DateFormat
	 * @return Date
	 */
	public static Date parseDate(String date, String df) {
		return parseDate(date, df, null);
	}

	/**
	 * change the string to date
	 * 
	 * @param date
	 *            String
	 * @param df
	 *            DateFormat
	 * @param defaultValue
	 *            if parse failed return the default value
	 * @return Date
	 */
	public static Date parseDate(String date, String df, Date defaultValue) {
		if (date == null || StringUtils.isBlank(df)) {
			return defaultValue;
		}

		SimpleDateFormat formatter = getSimpleDateFormat(df);

		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return defaultValue;
	}

	/**
	 * @return the current date without time component
	 */
	public static Date currentDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Date(calendar.getTimeInMillis());
	}

	/**
	 * Get start of date.
	 * 
	 * @param date
	 * @see Date
	 * @return the start of date
	 */
	public static Date getStartOfDate(final Date date) {
		if (date == null)
			return null;
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return new Date(cal.getTime().getTime());
	}

	public static Date getEndOfDate(final Date date) {
		if (date == null)
			return null;
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return new Date(cal.getTime().getTime());
	}
	
	public static Date getStartOfYesterday() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_YEAR, -1); //减一天,使用DAY_OF_MONTH也行

		return new Date(cal.getTime().getTime());
	}

	public static Date getEndOfYesterday() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		cal.add(Calendar.DAY_OF_YEAR, -1);

		return new Date(cal.getTime().getTime());
	}

	public static Date getStartOfMonth(){
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH,1); //设置为当月第一天
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return new Date(cal.getTime().getTime());
	}

	public static Date getEndOfMonth(){
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH)); //设置为当月最后一天
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return new Date(cal.getTime().getTime());
	}


	public static Date getStartOfLastMonth(){
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH,-1); //上个月
		cal.set(Calendar.DAY_OF_MONTH,1); //设置为当月第一天
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return new Date(cal.getTime().getTime());
	}

	public static Date getEndOfLastMonth(){
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH,-1); //上个月
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH)); //设置为当月最后一天
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return new Date(cal.getTime().getTime());
	}

	public static void main(String[] args) {
		System.out.println(getStartOfDate(new Date()));
		System.out.println(getEndOfDate(new Date()));
		System.out.println(getStartOfYesterday());
		System.out.println(getEndOfYesterday());


		System.out.println(getStartOfMonth());
		System.out.println(getEndOfMonth());
		System.out.println(getStartOfLastMonth());
		System.out.println(getEndOfLastMonth());
	}

	/**
	 * 获取上周的星期一
	 * 
	 * @return the day previous monday
	 */
	public static Date getPreviousMonday() {
		Calendar cd = Calendar.getInstance();

		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		Date date;
		if (dayOfWeek == 1) {
			date = addDays(cd.getTime(), -7);
		} else {
			date = addDays(cd.getTime(), -6 - dayOfWeek);
		}

		return getStartOfDate(date);
	}

	/**
	 * 获取一个月之前的星期一
	 * 
	 * @return the monday before one month
	 */
	public static Date getMondayBefore4Week() {
		Calendar cd = Calendar.getInstance();

		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		Date date;
		if (dayOfWeek == 1) {
			date = addDays(cd.getTime(), -28);
		} else {
			date = addDays(cd.getTime(), -27 - dayOfWeek);
		}

		return getStartOfDate(date);
	}

	/**
	 * 获取本周的星期一
	 * 
	 * @return the day of current monday
	 */
	public static Date getCurrentMonday() {
		Calendar cd = Calendar.getInstance();

		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		Date date;
		if (dayOfWeek == 1) {
			date = cd.getTime();
		} else {
			date = addDays(cd.getTime(), 1 - dayOfWeek);
		}

		return getStartOfDate(date);
	}

	/**
	 * Get date one day before specified one.
	 * 
	 * @param date1
	 *            test date
	 * @param date2
	 *            date when
	 * @return true if date1 is before date2
	 */
	public static boolean beforeDay(final Date date1, final Date date2) {
		if (date1 == null)
			return true;
		return getStartOfDate(date1).before(getStartOfDate(date2));
	}

	/**
	 * Get date one day after specified one.
	 * 
	 * @param date1
	 *            Date 1
	 * @param date2
	 *            Date 2
	 * @return true if after day
	 */
	public static boolean afterDay(final Date date1, final Date date2) {
		if (date1 == null)
			return false;
		return getStartOfDate(date1).after(getStartOfDate(date2));
	}

	/**
	 * Add specified number of months to the date given.
	 * 
	 * @param date
	 *            Date
	 * @param months
	 *            Int number of months to add
	 * @return Date
	 */
	public static Date addMonths(Date date, int months) {
		if (months == 0)
			return date;
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * Add specified number of days to the given date.
	 * 
	 * @param date
	 *            date
	 * @param days
	 *            Int number of days to add
	 * @return revised date
	 */
	public static Date addDays(final Date date, int days) {
		if (days == 0)
			return date;
		if (date == null)
			return null;
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);

		return new Date(cal.getTime().getTime());
	}

	public static Date addMins(final Date date, int mins) {
		if (mins == 0)
			return date;
		if (date == null)
			return null;
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, mins);

		return new Date(cal.getTime().getTime());
	}

	public static Date addSeconds(final Date date, int secs) {
		if (secs == 0)
			return date;
		if (date == null)
			return null;
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, secs);

		return new Date(cal.getTime().getTime());
	}

	/**
	 * Compare the two dates whether are in the same month.
	 * 
	 * @param date1
	 *            the first date
	 * @param date2
	 *            the second date
	 * @return whether are in the same month
	 */
	public static boolean isSameMonth(Date date1, Date date2) {
		if (date1 == null && date2 == null)
			return true;
		if (date1 == null || date2 == null)
			return false;
		Calendar cal1 = GregorianCalendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = GregorianCalendar.getInstance();
		cal2.setTime(date2);
		return isSameMonth(cal1, cal2);
	}

	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null && date2 == null)
			return true;
		if (date1 == null || date2 == null)
			return false;
		Calendar cal1 = GregorianCalendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = GregorianCalendar.getInstance();
		cal2.setTime(date2);

		return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
				&& (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && (cal1
						.get(Calendar.DATE) == cal2.get(Calendar.DATE)));
	}

	/**
	 * Compare the two calendars whether they are in the same month.
	 * 
	 * @param cal1
	 *            the first calendar
	 * @param cal2
	 *            the second calendar
	 * @return whether are in the same month
	 */
	public static boolean isSameMonth(Calendar cal1, Calendar cal2) {
		if (cal1 == null && cal2 == null)
			return true;
		if (cal1 == null || cal2 == null)
			return false;
		return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
				&& (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH));
	}

	/**
	 * 检查日期的合法性
	 * 
	 * @param sourceDate
	 *            the date @see Date
	 * @return 如果合法返回<code>true</code>，如果<code>sourceDate</code>为
	 *         <code>null</code>或者<code>format</code>为空，返回<code>false</code>
	 */
	public static boolean inFormat(String sourceDate, String format) {
		if (sourceDate == null || StringUtils.isBlank(format)) {
			return false;
		}
		try {
			SimpleDateFormat dateFormat = getSimpleDateFormat(format);
			dateFormat.setLenient(false);
			dateFormat.parse(sourceDate);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * get date time as "yyyyMMddhhmmss"
	 * 
	 * @return the current date with time component
	 */
	public static String now() {
		return formatDate(new Date(), FULL_PATTERN);
	}

	/**
	 * 格式化中文日期短日期格式
	 * 
	 * @param gstrDate
	 *            输入欲格式化的日期
	 * @return [yyyy年MM月dd日]
	 */

	public static String formatShortDateC(Date gstrDate) {
		if (gstrDate == null)
			return null;
		SimpleDateFormat formatter = getSimpleDateFormat("yyyy年MM月dd日");
		// Date nowc = new Date();
		String pid = formatter.format(gstrDate);
		return pid;
	}

	/**
	 * 返回标准格式的当前时间
	 * 
	 * @return [yyyy-MM-dd k:mm:ss]
	 */

	public static String getNow() {
		SimpleDateFormat formatter = getSimpleDateFormat("yyyy-MM-dd H:mm:ss");
		Date nowc = new Date();
		String pid = formatter.format(nowc);
		return pid;
	}

	/**
	 * 返回短日期格式
	 * 
	 * @return [yyyy-mm-dd]
	 */
	public static String formatShort(String strDate) {
		String ret = "";
		if (strDate != null && !"1900-01-01 00:00:00.0".equals(strDate)
				&& strDate.indexOf("-") > 0) {
			ret = strDate;
			if (ret.indexOf(" ") > -1)
				ret = ret.substring(0, ret.indexOf(" "));
		}
		return ret;
	}

	/**
	 * 返回两个时间间隔的秒数
	 * 
	 * @param d1
	 *            起始时间
	 * @param d2
	 *            终止时间
	 * @return the number of seconds interval,if either <code>d1</code> or
	 *         <code>d2</code> is zero,return <code>-1</code>
	 */
	public static int getNumberOfSecondsBetween(final double d1, final double d2) {
		if ((d1 == 0) || (d2 == 0)) {
			return -1;
		}

		return (int) (Math.abs(d1 - d2) / SECOND);
	}

	/**
	 * 返回两个时间间隔的月数
	 * 
	 * @param before
	 *            起始时间 @see Date
	 * @param end
	 *            终止时间 @see Date
	 * @return the number of months interval,if either <code>before</code> or
	 *         <code>end</code> is <code>null</code>,return <code>-1</code>
	 */
	public static int getNumberOfMonthsBetween(final Date before, final Date end) {
		if (before == null || end == null)
			return -1;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(before);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(end);
		return (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)) * 12
				+ (cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH));
	}

	/**
	 * 返回两个时间间隔的分钟数
	 * 
	 * @param before
	 *            起始时间
	 * @param end
	 *            终止时间
	 * @return 分钟数，如果<code>before</code>或者<code>end</code>为<code>null</code>，返回
	 *         <code>-1</code>
	 */
	public static long getNumberOfMinuteBetween(final Date before,
			final Date end) {
		if (before == null || end == null)
			return -1;
		long millisec = end.getTime() - before.getTime();
		return millisec / (60 * 1000);
	}

	/**
	 * 返回两个时间间隔的小时数
	 * 
	 * @param before
	 *            起始时间
	 * @param end
	 *            终止时间
	 * @return 小时数，如果<code>before</code>或者<code>end</code>为<code>null</code>，返回
	 *         <code>-1</code>
	 */
	public static long getNumberOfHoursBetween(final Date before, final Date end) {
		if (before == null || end == null)
			return -1;
		long millisec = end.getTime() - before.getTime() + 1;
		return millisec / (60 * 60 * 1000);
	}

	/**
	 * 返回MM月dd日
	 * 
	 * @param srcDate
	 *            the src date @see Date
	 * @return [MM月dd日]
	 */
	public static String formatMonthAndDay(Date srcDate) {
		return formatDate("MM月dd日");
	}

	public static long getNumberOfDaysBetween(final Date before, final Date end) {
		if (before == null || end == null)
			return -1;
		Calendar cal = Calendar.getInstance();
		cal.setTime(before);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(end);
		return getNumberOfDaysBetween(cal, endCal);
	}

	/**
	 * 计算2个日前直接相差的天数
	 * 
	 * @param cal1
	 *            the before calendar @see Calendar
	 * @param cal2
	 *            the end calendar @see Calendar
	 * @return 天数，如果<code>cal1</code>或者<code>cal2</code>为<code>null</code>，返回
	 *         <code>-1</code>
	 */
	public static long getNumberOfDaysBetween(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null)
			return -1;
		cal1.set(Calendar.MILLISECOND, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.HOUR_OF_DAY, 0);

		cal2.set(Calendar.MILLISECOND, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.HOUR_OF_DAY, 0);

		long elapsed = cal2.getTime().getTime() - cal1.getTime().getTime();
		return elapsed / DAY;
	}

	/**
	 * return current calendar instance
	 * 
	 * @return Calendar
	 */
	public static Calendar getCurrentCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * return current time
	 * 
	 * @return current time
	 */
	public static Timestamp getCurrentDateTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取系统日期
	 * 
	 * @return 系统日期
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 获取年份
	 * 
	 * @param date
	 * @see Date
	 * @return the year of <code>date</code>,if <code>date</code> is null,return
	 *         -1
	 */
	public static final int getYear(Date date) {
		if (date == null)
			return -1;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取年份
	 * 
	 * @param millis
	 *            long
	 * @return the year of date
	 */
	public static final int getYear(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 * @see Date
	 * @return the month of <code>date</code>,if <code>date</code> is
	 *         null,return -1
	 */
	public static final int getMonth(Date date) {
		if (date == null)
			return -1;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取月份
	 * 
	 * @param millis
	 *            long
	 * @return the month of date
	 */
	public static final int getMonth(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取日期
	 * 
	 * @param date
	 * @see Date
	 * @return the day of <code>date</code>,if <code>date</code> is null,return
	 *         -1
	 */
	public static final int getDate(Date date) {
		if (date == null)
			return -1;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取日期
	 * 
	 * @param millis
	 *            long
	 * @return the day of date
	 */
	public static final int getDate(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取小时
	 * 
	 * @param date
	 * @see Date
	 * @return the hour of <code>date</code>,if <code>date</code> is null,return
	 *         -1
	 */
	public static final int getHour(Date date) {
		if (date == null)
			return -1;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取小时
	 * 
	 * @param millis
	 *            long
	 * @return the hour of date
	 */
	public static final int getHour(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 根据url获取日期
	 * 
	 * @param url
	 *            <pre>
	 * http://192.9.162.55
	 * http://java.sun.com
	 * </pre>
	 * @return 远程服务器日期
	 */
	public static Date getDateByUrl(String url) {
		URLConnection uc;
		try {
			uc = new URL(url).openConnection();
			uc.connect(); // 发出连接
			return new Date(uc.getDate());// 生成连接对象
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回两个时间间隔的秒数
	 * 
	 * @param before
	 *            起始时间
	 * @param end
	 *            终止时间
	 * @return 分钟数，如果<code>before</code>或者<code>end</code>为<code>null</code>，返回
	 *         <code>-1</code>
	 */
	public static long getNumberOfSecondBetween(final Date before,
			final Date end) {
		if (before == null || end == null)
			return -1;
		long millisec = end.getTime() - before.getTime();
		return millisec / (1000);
	}

	/**
	 * <p>
	 * Title: dateToStr
	 * </p>
	 * <p>
	 * Description: 将Date类型的时间转换为制定格式的String类型
	 * </p>
	 * 
	 * @param date
	 *            Date类型参数
	 * @param pattern
	 *            制定的日期类型，null为默认"yyyy-MM-dd HH:mm:ss.SSS"；
	 * @return 按照pattern中指定的日期类型返回String字符
	 */
	public static String dateToStr(Date date, String pattern) {
		if (pattern == null) {
			pattern = "yyyy-MM-dd HH:mm:ss.SSS";
		}
		DateFormat ymdhmsFormat = getSimpleDateFormat(pattern);

		return ymdhmsFormat.format(date);
	}

	/**
	 * 将指定格式的字符串转换为Date类型的时间
	 * 
	 * @param str
	 * @param pattern
	 *            制定的日期类型，null为默认"yyyy-MM-dd HH:mm:ss.SSS"；
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String str, String pattern)
			throws ParseException {
		if (pattern == null) {
			pattern = "yyyy-MM-dd HH:mm:ss.SSS";
		}
		DateFormat ymdhmsFormat = getSimpleDateFormat(pattern);
		return ymdhmsFormat.parse(str);
	}

	/**
	 * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
	 * represented by this <tt>Date</tt> object.
	 * 
	 * @return the number of milliseconds since January 1, 1970, 00:00:00 GMT
	 *         represented by this date.
	 */
	public static Long dateToLong(Date date) {
		return date.getTime();
	}

	/**
	 * Allocates a <code>Date</code> object and initializes it to represent the
	 * specified number of milliseconds since the standard base time known as
	 * "the epoch", namely January 1, 1970, 00:00:00 GMT.
	 * 
	 * @param l
	 *            the milliseconds since January 1, 1970, 00:00:00 GMT.
	 * @see System#currentTimeMillis()
	 */
	public static Date longToDate(long l) {
		Date date = new Date(l);
		return date;
	}

	/**
	 * <p>
	 * Title: getToday
	 * </p>
	 * <p>
	 * Description: 获取当前时间
	 * </p>
	 * 
	 * @return 当前时间
	 */
	public static Date getCurrentTime() {
		Calendar ca = Calendar.getInstance();
		return ca.getTime();
	}

	/**
	 * 获取今天的日期
	 * @return 今天
	 */
	public static Date getToday(){
	    return getStartOfDate(new Date());
	}

	/**
	 * <p>
	 * Title: getDirDat
	 * </p>
	 * <p>
	 * Description:获取指定时间
	 * </p>
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param date
	 *            日
	 * @return
	 */
	public static Date getDirDate(int year, int month, int date) {
		Calendar ca = Calendar.getInstance();
		ca.set(year, month - 1, date);
		SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		sdf.format(ca.getTime());
		return ca.getTime();
	}

}
