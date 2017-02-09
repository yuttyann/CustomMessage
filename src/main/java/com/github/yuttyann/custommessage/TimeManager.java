package com.github.yuttyann.custommessage;

import java.util.Calendar;

import com.github.yuttyann.custommessage.file.Files;
import com.github.yuttyann.custommessage.file.YamlConfig;

public class TimeManager {

	public static Integer getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public static Integer getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	public static Integer getDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	public static Integer getHour_Of_Day() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	public static Integer getMinute() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}

	public static Integer getSecond() {
		return Calendar.getInstance().get(Calendar.SECOND);
	}

	public static Integer getDay_Of_Week() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
	}

	public static Integer getDay_Of_Year() {
		return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
	}

	public static String getTimesofDay() {
		YamlConfig config = Files.getConfig();
		String[] week_name = {
			config.getString("Sunday"), config.getString("Monday"), config.getString("Tuesday"), config.getString("Wednesday"),
			config.getString("Thursday"), config.getString("Friday"), config.getString("Saturday")
		};
		String timesofday = config.getString("TimesofDay");
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
