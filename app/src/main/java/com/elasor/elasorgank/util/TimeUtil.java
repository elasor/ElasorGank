package com.elasor.elasorgank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Elasor
 */
public class TimeUtil {

    public static int daysAgo(String oldTime) {
        int days = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            Date date = new Date();
            Date oldDate = sdf.parse(oldTime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.setTime(oldDate);
            int oldYear = calendar.get(Calendar.YEAR);
            int oldMonth = calendar.get(Calendar.MONTH) + 1;
            int oldDay = calendar.get(Calendar.DAY_OF_MONTH);

            int yearSub = year - oldYear;

            if (yearSub == 0) {
                int monthSub = month - oldMonth;

                if (monthSub == 0) {
                    return day - oldDay;
                }

                for (int i = oldMonth + 1; i < month && monthSub > 1; i++) {
                    days += daysOfMonth(year, i);
                }

                days += daysOfMonth(oldYear, oldMonth) - oldDay;
                days += day;
                return days;
            }

            for (int i = year - 1; i > oldYear && yearSub > 1; i--) {
                days += isLeapYear(i) ? 366 : 365;
            }

            for (int i = 1; i < month; i++) {
                days += daysOfMonth(year, i);
            }

            days += day - 1;

            for (int i = oldMonth + 1; i <= 12 && oldMonth != 12; i++) {
                days += daysOfMonth(oldYear, i);
            }

            days += daysOfMonth(oldYear, oldMonth) - oldDay + 1;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public static int daysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
        }
        return 0;
    }
}
