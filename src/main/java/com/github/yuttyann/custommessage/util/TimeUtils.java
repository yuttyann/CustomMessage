package com.github.yuttyann.custommessage.util;

import java.util.Calendar;

import com.github.yuttyann.custommessage.file.Config;

public class TimeUtils {

	private static Integer year;
	private static Integer month;
	private static Integer day;
	private static Integer hour;
	private static Integer minute;
	private static Integer second;
	private static Integer week;
	private static Integer day_of_year;

	private static String[] week_name = {
		Config.getString("Sunday"), Config.getString("Monday"), Config.getString("Tuesday"), Config.getString("Wednesday"),
		Config.getString("Thursday"), Config.getString("Friday"), Config.getString("Saturday")
	};

	public static Integer getYear() {
		year = Calendar.getInstance().get(Calendar.YEAR);
		return year;
	}

	public static Integer getMonth() {
		month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		return month;
	}

	public static Integer getDay() {
		day = Calendar.getInstance().get(Calendar.DATE);
		return day;
	}

	public static Integer getHour() {
		hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	public static Integer getMinute() {
		minute = Calendar.getInstance().get(Calendar.MINUTE);
		return minute;
	}

	public static Integer getSecond() {
		second = Calendar.getInstance().get(Calendar.SECOND);
		return second;
	}

	public static Integer getWeek() {
		week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
		return week;
	}

	public static Integer getDay_Of_Year() {
		day_of_year = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		return day_of_year;
	}

	public static String getTime() {
		String time = Config.getString("Time");
		time = time.replace("%year", getYear().toString());
		time = time.replace("%month", getMonth().toString());
		time = time.replace("%day", getDay().toString());
		time = time.replace("%hour", getHour().toString());
		time = time.replace("%minute", getMinute().toString());
		time = time.replace("%second", getSecond().toString());
		time = time.replace("%week", week_name[getWeek()]);
		time = time.replace("%dayofyear", getDay_Of_Year().toString());
		time = time.replace("&", "§");
		return time;
	}
}
