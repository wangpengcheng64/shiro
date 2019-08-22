package com.wpc.shiro.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * date:2016/7/8. time:20:20.
 */
public class DateUtil {
	/**
	 * 锁对象
	 */
	private static final Object lockObj = new Object();
	/**
	 * 存放不同的日期模板格式的sdf的Map
	 */
	private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();
	/**
	 * 日期时间格式 *
	 */
	public static final String timePattern = "HHmmss";
	public static final String timesPattern = "yyyy/MM/dd HH:mm:ss";
	public static final String datePattern = "yyyyMMdd";
	public static final String shortDatePattern = "yyMMdd";
	public static final String fullPattern = "yyyyMMddHHmmss";
	public static final String fullPatterns = "yyyyMMddHHmmssSS";
	public static final String partPattern = "yyMMddHHmmss";
	public static final String ticketPattern = "yyyy.MM.dd HH:mm:ss";
	public static final String settlePattern = "yyyy-MM-dd HH:mm:ss";
	public static final String hour_of_minute = "HHmm";
	public static final String timeColPattern = "HH:mm:ss";
	public static final String dateFullPattern = "yyyyMMdd HH:mm:ss";
	public static final String year_of_minute = "yyyyMMddHHmm";
	public static final String yearDate = "yyyy-MM-dd HH:mm";
	public static final String shotPattern = "yyyy-MM-dd";
	public static final String pointPattern = "yyyy.MM.dd";

	/**
	 * 时间格式转换
	 *
	 * @param date          时间字符串
	 * @param originPattern 原时间格式
	 * @param targetPattern 新的时间格式
	 * @return
	 * @throws ParseException
	 */
	public static String convert(String date, String originPattern, String targetPattern) throws ParseException {
		Date originDate = parse(date, originPattern);
		return format(originDate, targetPattern);
	}

	/**
	 * 源日期和（目标日期加上毫秒数）比较大小， 大则返回true ，小返回false
	 *
	 * @param src    源日期
	 * @param target 目的日期
	 * @param second 秒数
	 * @return 成功，失败
	 */
	public static boolean compareDateForSecond(Date src, Date target, int second) {
		Calendar targetTime = Calendar.getInstance();
		targetTime.setTime(target);
		targetTime.add(Calendar.SECOND, second);
		Calendar srcTime = Calendar.getInstance();
		srcTime.setTime(src);
		return srcTime.compareTo(targetTime) >= 0;
	}

	public static boolean compareDate(Date src, Date target) {
		Calendar targetTime = Calendar.getInstance();
		targetTime.setTime(target);
		Calendar srcTime = Calendar.getInstance();
		srcTime.setTime(src);
		return srcTime.compareTo(targetTime) >= 0;
	}

	public static boolean compareDateForTime(Date src, Date target, int time, int field) {
		Calendar targetTime = Calendar.getInstance();
		targetTime.setTime(target);
		targetTime.add(field, time);
		Calendar srcTime = Calendar.getInstance();
		srcTime.setTime(src);
		return srcTime.compareTo(targetTime) >= 0;
	}

	/**
	 * 日期加
	 *
	 * @param time
	 * @param field
	 * @return
	 */
	public static Date getCurrentAfterTime(int time, int field) {
		Calendar targetTime = Calendar.getInstance();
		targetTime.setTime(new Date());
		targetTime.add(field, time);
		return targetTime.getTime();
	}

	/**
	 * 获取下个月28号
	 * 
	 * @return
	 */
	public static Date getNextMonthDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 28);
		return calendar.getTime();
	}

	/**
	 * 获取任意时间的下一个月 描述:<描述函数实现的功能>.
	 * 
	 * @param repeatDate
	 * @return
	 */
	public static String nextMonth(Date date, Integer months) {
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));// 取到年份值
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(date)) + months;// 取到鱼粉值
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));// 取到天值
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String da = dateFormat.format(date);
		if (month == 0) {
			year -= 1;
			month = 12;
		} else if (day > 28) {
			if (month == 2) {
				if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
					day = 29;
				} else
					day = 28;
			} else if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
				day = 30;
			}
		}
		String y = year + "";
		String m = "";
		String d = "";
		if (month < 10)
			m = "0" + month;
		else
			m = month + "";
		if (day < 10)
			d = "0" + day;
		else
			d = day + "";
		System.out.println(y + "-" + m + "-" + d);
		return y + "-" + m + "-" + d + " " + da.split(" ")[1];
	}

	/**
	 * 
	 * @param param 控制月，如果-1，获取上月，0：获取本月，1：下个月
	 * @param time  控制天
	 * @return
	 */
	public static String getNextMonth(int param, int time, Date targetDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(targetDate);
		calendar.add(Calendar.MONTH, param);
		calendar.set(Calendar.DAY_OF_MONTH, time);
		if (targetDate == null) {
			return null;
		}
		return getSdf("yyyy-MM-dd").format(calendar.getTime());
	}

	/**
	 * 
	 * @param param 控制月，如果-1，获取上月，0：获取本月，1：下个月
	 * @param time  控制天
	 * @return
	 */
	public static String getNextMonthFormate(int param, int time, Date targetDate, String dateFormate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(targetDate);
		calendar.add(Calendar.MONTH, param);
		calendar.set(Calendar.DAY_OF_MONTH, time);
		if (targetDate == null) {
			return null;
		}
		return getSdf(dateFormate).format(calendar.getTime());
	}

	/**
	 *
	 * @param param 控制月，如果-1，获取上月，0：获取本月，1：下个月
	 * @param time  控制天
	 * @return
	 */
	public static String getNextMonthSimpleFormat(int param, int time, Date targetDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(targetDate);
		calendar.add(Calendar.MONTH, param);
		calendar.set(Calendar.DAY_OF_MONTH, time);
		if (targetDate == null) {
			return null;
		}
		return getSdf("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * 
	 * @param param 控制月，如果-1，获取上月，0：获取本月，1：下个月
	 * @param time  控制天
	 * @return
	 */
	public static Date getMonthDate(int param, int time, Date targetDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(targetDate);
		calendar.add(Calendar.MONTH, param);
		calendar.set(Calendar.DAY_OF_MONTH, time);
		if (targetDate == null) {
			return null;
		}
		return calendar.getTime();
	}

	public static Date getCurrentAfterSecond(int second) {
		Calendar targetTime = Calendar.getInstance();
		targetTime.setTime(new Date());
		targetTime.add(Calendar.SECOND, second);
		return targetTime.getTime();
	}

	public static String getCurrentAfter(int minute) {
		Calendar targetTime = Calendar.getInstance();
		targetTime.setTime(new Date());
		targetTime.add(Calendar.MINUTE, minute);
		return format(targetTime.getTime(), DateUtil.fullPattern);
	}

	public static String getCurrentAfter(int minute, String dataPattern) {
		Calendar targetTime = Calendar.getInstance();
		targetTime.setTime(new Date());
		targetTime.add(Calendar.MINUTE, minute);
		return format(targetTime.getTime(), dataPattern);
	}

	public static String getCurrentAfter(String targetDateStr, int minute, String dataPattern) {
		Calendar targetTime = Calendar.getInstance();
		targetTime.setTime(parse(targetDateStr, dataPattern));
		targetTime.add(Calendar.MINUTE, minute);
		return format(targetTime.getTime(), dataPattern);
	}

	/**
	 * 获取当前时间
	 *
	 * @return
	 */
	public static String getCurrent() {
		return format(new Date(), DateUtil.fullPattern);
	}

	/**
	 * 获取当前时间格式为 yyyy-MM-dd
	 */
	public static String getCurrentFormat() {
		return format(new Date(), DateUtil.shotPattern);
	}

	/**
	 * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
	 *
	 * @param pattern
	 * @return
	 */
	private static SimpleDateFormat getSdf(final String pattern) {
		ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
		// 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
		if (tl == null) {
			synchronized (lockObj) {
				tl = sdfMap.get(pattern);
				if (tl == null) {
					// 使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected SimpleDateFormat initialValue() {
							return new SimpleDateFormat(pattern);
						}
					};
					sdfMap.put(pattern, tl);
				}
			}
		}

		return tl.get();
	}

	/**
	 * 使用线程容器来获取SimpleDateFormat
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return getSdf(pattern).format(date);
	}

	public static Date parse(String dateStr, String pattern) {
		try {
			return getSdf(pattern).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * String转Date
	 *
	 * @param
	 * @throws ParseException
	 */
	public static Date stringToDate(String str) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getCurrent(String pattern) {
		return format(new Date(), pattern);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取当月最后一天的日期
	 * 
	 * @return
	 */
	public static Date lastMonthDate() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return ca.getTime();
	}

	/**
	 * 
	 * 计算两个日期相差的月份数
	 * 
	 * @param date1 日期1
	 * @param date2 日期2
	 * @return 相差的月份数
	 * @throws ParseException
	 */
	public static int countMonths(String date1, String date2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(sdf.parse(date1));
//        c2.setTime(sdf.parse(date2));
		int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		// 开始日期若小月结束日期
		if (year < 0) {
			year = -year;
			return year * 12 + c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
		}
		return year * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
	}

	/**
	 * 
	 * 计算两个日期相差的月份数
	 * 
	 * @param date1 日期1
	 * @param date2 日期2
	 * @return 相差的月份数
	 * @throws ParseException
	 */
	public static int countMonths(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		// 开始日期若小月结束日期
		if (year < 0) {
			year = -year;
			return year * 12 + c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
		}
		return year * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
	}

	/**
	 * 获取两个日期相差的月数
	 * 
	 * @param d1  较大的日期
	 * @param d2  较小的日期
	 * @return 如果d1>d2返回 月数差 否则返回0
	 */
	public static int getMonthDiff(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		if (c1.getTimeInMillis() < c2.getTimeInMillis())
			return 0;
		int year1 = c1.get(Calendar.YEAR);
		int year2 = c2.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int month2 = c2.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		int day2 = c2.get(Calendar.DAY_OF_MONTH);
		// 获取年的差值 假设 d1 = 2015-8-16 d2 = 2011-9-30
		int yearInterval = year1 - year2;
		// 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
		if (month1 < month2 || month1 == month2 && day1 < day2)
			yearInterval--;
		// 获取月数差值
		int monthInterval = (month1 + 12) - month2;
		if (day1 < day2)
			monthInterval--;
		monthInterval %= 12;
		return yearInterval * 12 + monthInterval;
	}

	/**
	 * 获取指定日期当月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfGivenMonth(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(shotPattern);
		// Date date = sdf.parse(dateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 0);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 返回指定日期的月的最后一天
	 *
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 *
	 * 描述:获取指定日期下个月的最后一天.
	 *
	 * @return
	 */
	public static String getNextMaxMonthDate(Date date) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dft.format(calendar.getTime());
	}

	/**
	 * 返回指定日期的月的最后一天
	 *
	 * @return
	 */
	public static String getLastDayOfMonthStr(Date date, String formate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		calendar.roll(Calendar.DATE, -1);
		return getSdf(formate).format(calendar.getTime());
	}

	/**
	 * 获取指定日期下个月的第一天
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static String getFirstDayOfNextMonth(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MONTH, 1);
			return sdf.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取指定日期下个月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfNextMonthToDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 *
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 *
	 * @param dateDate
	 * @return
	 */
	public static String dateToStr(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM( 2017-02)
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateNotDD(String strDate) {
		if (StringUtils.isBlank(strDate))
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * java8(经测试别的版本获取2月有bug) 获取某月第一天的00:00:00
	 * 
	 * @return
	 */
	public static String getFirstDayOfMonth(String datestr) {
		if (StringUtils.isBlank(datestr))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = strToDateNotDD(datestr);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
				ZoneId.systemDefault());
		;
		LocalDateTime endOfDay = localDateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
		Date dates = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
		return sdf.format(dates);
	}

	/**
	 * java8(别的版本获取2月有bug) 获取某月最后一天的23:59:59
	 * 
	 * @return
	 */
	public static String getLastDayOfMonth(String datestr) {
		if (StringUtils.isBlank(datestr))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = strToDateNotDD(datestr);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
				ZoneId.systemDefault());
		;
		LocalDateTime endOfDay = localDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
		Date dates = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
		return sdf.format(dates);
	}

	// 获得某天最大时间 2018-03-20 23:59:59
	public static Date getEndOfDay(Date date) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
				ZoneId.systemDefault());
		;
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
	}

	// 获得某天最小时间 2018-03-20 00:00:00
	public static Date getStartOfDay(Date date) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
				ZoneId.systemDefault());
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param dt 当前日期
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "日", "一", "二", "三", "四", "五", "六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 取得月的剩余天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getRemainDayOfMonth(Date date) {
		int dayOfMonth = getDayOfMonth(date);
		int day = getPassDayOfMonth(date);
		return dayOfMonth - day;
	}

	/**
	 * 取得月已经过的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getPassDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 取得月天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 取得年天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_YEAR);
	}

	
	/**
	 * 时间多重分段
	 * @param startHHmm
	 * @param endHHmm
	 * @param piecewise 段数 为0 时默认按小时分
	 * @param splitStr ~ -
	 * @return
	 * @throws ParseException
	 */
	public static List<String> timeInterVal(String startHHmm, String endHHmm,int piecewise,String splitStr) throws ParseException {
		List<String> timeInterVal = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date d1 = sdf.parse(endHHmm);
		Date d2 = sdf.parse(startHHmm);
		piecewise = piecewise == 0 ? d1.getHours() - d2.getHours() : piecewise;
		long minutes = (d1.getTime() - d2.getTime())/(1000 * 60);	
		for (int i = 0; i < piecewise; i++) {
			Date dt = sdf.parse(startHHmm);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.MINUTE, (int) minutes / piecewise);
			Date dt1 = rightNow.getTime();
			String dt2 = dt1.toString().substring(11, 16);
			timeInterVal.add(startHHmm+splitStr+dt2);
			startHHmm = dt2;
		}
		return timeInterVal;
	}
	
	/**
	 * 时间小时分段
	 * @param startHHmm
	 * @param endHHmm
	 * @param splitStr
	 * @return
	 * @throws ParseException
	 */
	public static List<String> timeIntVal(String startHHmm, String endHHmm,String splitStr) throws ParseException{
		List<String> timeInterVal = new ArrayList<String>();
			SimpleDateFormat smt = new SimpleDateFormat("HH:mm");
			int hours = smt.parse(endHHmm).getHours() - smt.parse(startHHmm).getHours() - 1;
			long startTimp = smt.parse(startHHmm).getTime();
			for (int i = 0; i < hours + 1; i++) {
				timeInterVal.add(smt.format(startTimp) + splitStr + smt.format(startTimp + 1000 * 3600));
				startTimp = startTimp + 1000 * 3600;
			}
			return timeInterVal;
	}

	
	/**
	 * 拿到明天日期
	 * @return
	 */
	public static Date getNextIntDay(int day) {
		SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		date = calendar.getTime();
		try {
		 return smt.parse(smt.format(date));
		} catch (ParseException e) {
			return date;
		}
	}
	
	
	/**
	 * 拿到明天日期
	 * @return
	 */
	public static String getNextIntMonth(int month) {
		SimpleDateFormat smt = new SimpleDateFormat("yyyy年MM月dd日");
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		date = calendar.getTime();
		 return  smt.format(date);
	}
	
	
	/**
	 * 拿到明天日期
	 * @return
	 */
	public static Date getDateByAppCreateAgreement(String str) {
		SimpleDateFormat smt = new SimpleDateFormat("yyyy年MM月dd日");
		 try {
			return  smt.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	/**
	 * 字符串转化时间
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByStr(String str){
		SimpleDateFormat smt = new SimpleDateFormat(settlePattern);
		try {
			return smt.parse(str);
		} catch (ParseException e) {
			System.out.println("时间转化出错....");
		}
		return null;
	}

	/**
	 * 获取指定日期当周的第一天
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfWeek(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(shotPattern);
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return sdf.format(c.getTime());
	}

	public static void main(String[] args) throws ParseException {
		//SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		//System.out.println(smt.format(getNextIntMonth(3)));
		System.out.println(getFirstDayOfWeek(new Date()));
	}

}
