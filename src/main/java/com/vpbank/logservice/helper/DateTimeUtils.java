package com.vpbank.logservice.helper;


import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String SIMPLE_DATE = "yyyy-MM-dd";
    public static final String SIMPLE_DATE_2 = "dd/MM/yyyy";


    public static String getCurrentTime() {
        return getCurrentTimeWithFormat(YYYY_MM_DD_HH_MM_SS);
    }

    public static String getCurrentTimeWithFormat(String format) {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String convertDateToString(ZonedDateTime zonedDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return zonedDateTime.format(formatter);

    }

    public static String convertDateToString(Date date, String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);

    }

    public static String convertDateToString(String strDate, String format, String formatNew) {
        if (StringUtils.isEmpty(strDate)) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat(format);
        DateFormat formatterNew = new SimpleDateFormat(formatNew);
        Date date;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            return strDate;
        }
        return formatterNew.format(date);

    }

    public static Date convertStringToDate(String strDate, String format) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(strDate);
        } catch (ParseException e) {
            return null;
        }

    }

    public static String convertDateToString(String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        DateFormat formatterNew = new SimpleDateFormat(SIMPLE_DATE_2);
        Date date;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            return strDate;
        }
        return formatterNew.format(date);

    }


    public static String convertDateToString(ZonedDateTime zonedDateTime) {
        return convertDateToString(zonedDateTime, YYYY_MM_DD_HH_MM_SS);

    }

    public static String convertDateToString(Date zonedDateTime) {
        return convertDateToString(zonedDateTime, YYYY_MM_DD_HH_MM_SS);

    }

    public static String convertSimpleDateToString(Date zonedDateTime) {
        return convertDateToString(zonedDateTime, SIMPLE_DATE);
    }

    public static String convertDateToStringWithTimeZone(ZonedDateTime zonedDateTime, String format, ZoneOffset zoneId) {
        ZonedDateTime utcDate = zonedDateTime.withZoneSameInstant(zoneId);
        return convertDateToString(utcDate, format);

    }

    public static long getNumberDayDiff(Date from, Date to) {
        long diffInMillies = Math.abs(to.getTime() - from.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
