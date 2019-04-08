package com.ssm.tsy.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	/**
	 * 对先有日期进行调整 日期迁移后退
	 * @param nowDay 设定现在的日期
	 * @param field  取1加1年,取2加半年,取3加一季度,取4加一周,取5加一天
	 * @param amount 增加量 正数日期将后加，负数日期将前推
	 * @return
	 */
	public static String changeDay(String nowDay, int field, int amount) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		Date date = format.parse(nowDay);
		gc.setTime(date);
		gc.add(field, amount);
		return format.format(gc.getTime());
	}

	/**
	 * 比较两个日期之间相差的天数daysBetween("2016-12-17","2016-12-29")
	 * @param stareTime  开始日期 较小的日期
	 * @param endTime 结束日期，比较大的日期
	 * @return
	 * @throws Exception
	 */
	public static int daysBetween(String stareTime, String endTime) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(stareTime));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(endTime));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * 获取日志表后缀日期
	 * @return
	 */
	public static String getTimeSixAndToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMM");
		String nowTime = df.format(dt);
		return nowTime;
	}
	
	/**
	 * 获取上个月日志表后缀日期
	 * @return
	 */
	public static String getTimePrevMonthAndToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMM");
		String nowTime = df.format(getLastDate(dt));
		return nowTime;
	}
	
	private static Date getLastDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

	/**
	 * 获取当前日期(2016-12-29 11:23:09)
	 * @return
	 */
	public static String getTimeAndToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = df.format(dt);
		return nowTime;
	}

	/**
	 * 获取当前日期字符串(20161229112321)
	 * @return
	 */
	public static String getToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowTime = df.format(dt);
		return nowTime;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(daysBetween("2016-12-17","2016-12-29"));
	}

}
