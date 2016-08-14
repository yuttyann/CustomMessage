package com.github.yuttyann.custommessage;

import java.util.Calendar;

import com.github.yuttyann.custommessage.file.Config;

public class TimeManager {

	private static String[] week_name = {
		Config.getString("Sunday"), Config.getString("Monday"), Config.getString("Tuesday"), Config.getString("Wednesday"),
		Config.getString("Thursday"), Config.getString("Friday"), Config.getString("Saturday")
	};

	final private static Calendar calendar = Calendar.getInstance();

	public static Integer getYear() {
		return calendar.get(Calendar.YEAR);
	}

	public static Integer getMonth() {
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static Integer getDay() {
		return calendar.get(Calendar.DATE);
	}

	public static Integer getHour_Of_Day() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static Integer getMinute() {
		return calendar.get(Calendar.MINUTE);
	}

	public static Integer getSecond() {
		return calendar.get(Calendar.SECOND);
	}

	public static Integer getDay_Of_Week() {
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	public static Integer getDay_Of_Year() {
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	public static String getTimesofDay() {
		String timesofday = Config.getString("TimesofDay");
		timesofday = timesofday.replace("%year", getYear().toString());
		timesofday = timesofday.replace("%month", getMonth().toString());
		timesofday = timesofday.replace("%day", getDay().toString());
		timesofday = timesofday.replace("%hour", getHour_Of_Day().toString());
		timesofday = timesofday.replace("%minute", getMinute().toString());
		timesofday = timesofday.replace("%second", getSecond().toString());
		timesofday = timesofday.replace("%week", week_name[getDay_Of_Week()]);
		timesofday = timesofday.replace("%dayofyear", getDay_Of_Year().toString());
		timesofday = timesofday.replace("&", "§");
		return timesofday;
	}
}
