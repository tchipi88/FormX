package com.appli.nyx.formx.utils;

import com.google.firebase.Timestamp;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

	// This class should not be initialized
	private DateUtils() {

	}

	public static LocalTime getLocalTime(String time) {
		return LocalTime.parse(time, DateTimeFormat.forPattern("HH:mm"));
	}

	public static LocalDate getLocalDate(String date) {
		return LocalDate.parse(date, DateTimeFormat.forPattern("dd/MM/yyyy"));
	}

	public static LocalDate getLocalDate(Long date) {
		return LocalDate.fromDateFields(new Date(date));
	}

	public static String getStringDate(LocalDate localDate) {
		return localDate.toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
	}


	public static String getStringTime(LocalTime localTime) {
		return localTime.toString(DateTimeFormat.forPattern("HH:mm"));
	}

	public static Long getLongDate(LocalDate localDate) {
		return localDate.toDate().getTime();
	}

	public static Timestamp getFirestoreTimestamp(String date) {
		return new Timestamp(getLocalDate(date).toDate());
	}

	public static String getDurationString(int seconds) {
		Date date = new Date(seconds * 1000);
		SimpleDateFormat formatter = new SimpleDateFormat(seconds >= 3600 ? "HH:mm:ss" : "mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		return formatter.format(date);
	}

	/**
	 * Gets timestamp in millis and converts it to HH:mm (e.g. 16:44).
	 */
	public static String formatTime(long timeInMillis) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
		return dateFormat.format(timeInMillis);
	}

	public static String formatTimeWithMarker(long timeInMillis) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
		return dateFormat.format(timeInMillis);
	}

	public static int getHourOfDay(long timeInMillis) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("H", Locale.getDefault());
		return Integer.valueOf(dateFormat.format(timeInMillis));
	}

	public static int getMinute(long timeInMillis) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("m", Locale.getDefault());
		return Integer.valueOf(dateFormat.format(timeInMillis));
	}

	/**
	 * If the given time is of a different date, display the date.
	 * If it is of the same date, display the time.
	 *
	 * @param timeInMillis The time to convert, in milliseconds.
	 * @return The time or date.
	 */
	public static String formatDateTime(long timeInMillis) {
		if (isToday(timeInMillis)) {
			return formatTime(timeInMillis);
		} else {
			return formatDate(timeInMillis);
		}
	}

	/**
	 * Formats timestamp to 'date month' format (e.g. 'February 3').
	 */
	public static String formatDate(long timeInMillis) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd", Locale.getDefault());
		return dateFormat.format(timeInMillis);
	}

	/**
	 * Returns whether the given date is today, based on the user's current locale.
	 */
	public static boolean isToday(long timeInMillis) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		String date = dateFormat.format(timeInMillis);
		return date.equals(dateFormat.format(System.currentTimeMillis()));
	}

	/**
	 * Checks if two dates are of the same day.
	 *
	 * @param millisFirst  The time in milliseconds of the first date.
	 * @param millisSecond The time in milliseconds of the second date.
	 * @return Whether {@param millisFirst} and {@param millisSecond} are off the same day.
	 */
	public static boolean hasSameDate(long millisFirst, long millisSecond) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		return dateFormat.format(millisFirst).equals(dateFormat.format(millisSecond));
	}
}
