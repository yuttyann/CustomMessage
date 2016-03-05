package com.github.yuttyann.custommessage;

import java.util.Calendar;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.yuttyann.custommessage.handle.ClassHandler;

public class TimeManager {

	private static FileConfiguration file = ClassHandler.getMainClass().getConfig();
	private static Calendar cal = Calendar.getInstance();
	private static String[] week_name = {file.getString("Sunday"), file.getString("Monday"), file.getString("Tuesday"),
	file.getString("Wednesday"), file.getString("Thursday"), file.getString("Friday"), file.getString("Saturday")};

	private static Integer year;
	private static Integer month;
	private static Integer day;
	private static Integer hour;
	private static Integer minute;
	private static Integer second;
	private static Integer week;
	private static Integer day_of_year;

	public static Integer getYear() {
		year = cal.get(Calendar.YEAR);
		return year;
	}

	public static Integer getMonth() {
		month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	public static Integer getDay() {
		day = cal.get(Calendar.DATE);
		return day;
	}

	public static Integer getHour() {
		hour = cal.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	public static Integer getMinute() {
		minute = cal.get(Calendar.MINUTE);
		return minute;
	}

	public static Integer getSecond() {
		second = cal.get(Calendar.SECOND);
		return second;
	}

	public static Integer getWeek() {
		week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return week;
	}

	public static Integer getDay_Of_Year() {
		day_of_year = cal.get(Calendar.DAY_OF_YEAR);
		return day_of_year;
	}

	public static String getTime() {
		String time = file.getString("Time");
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
