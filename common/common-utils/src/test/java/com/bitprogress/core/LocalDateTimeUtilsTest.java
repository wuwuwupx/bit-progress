package com.bitprogress.core;

import com.bitprogress.basemodel.enums.time.MomentType;
import com.bitprogress.basemodel.enums.time.WeekType;
import com.bitprogress.util.LocalDateTimeUtils;

import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class LocalDateTimeUtilsTest {

    public static void main(String[] args) {
        LocalDateTimeUtilsTest test = new LocalDateTimeUtilsTest();
        test.farmat();
    }

    void farmat() {
        LocalDateTime now = LocalDateTime.now();
        printResult("format", LocalDateTimeUtils.format(now), now);
        printResult("format", LocalDateTimeUtils.format(now, "yyyy-MM-dd HH:mm:ss"), now, "yyyy-MM-dd HH:mm:ss");
    }

    void parse() {
        printResult("parse", LocalDateTimeUtils.parse("2025-02-14 15:40:52.0101011"), "2025-02-14 15:40:52");
    }

    private void printResult(String methodName, Object result, Object... params) {
        System.out.println(methodName + " with params " + java.util.Arrays.toString(params) + ": " + result);
    }

    void isBetweenInclusive() {
        LocalDateTime date = LocalDateTime.of(2023, 10, 15, 12, 0);
        LocalDateTime start = LocalDateTime.of(2023, 10, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 10, 20, 23, 59);
        printResult("isBetweenInclusive", LocalDateTimeUtils.isBetween(date, start, end), date, start, end);
        printResult("isBetweenInclusive", LocalDateTimeUtils.isBetween(date, start, LocalDateTime.of(2023, 10, 14, 23, 59)), date, start, LocalDateTime.of(2023, 10, 14, 23, 59));
        // Boundary value tests
        printResult("isBetweenInclusive", LocalDateTimeUtils.isBetween(start, start, end), start, start, end);
        printResult("isBetweenInclusive", LocalDateTimeUtils.isBetween(end, start, end), end, start, end);
    }

    void isBetweenExclusive() {
        LocalDateTime date = LocalDateTime.of(2023, 10, 15, 12, 0);
        LocalDateTime start = LocalDateTime.of(2023, 10, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 10, 20, 23, 59);
        printResult("isBetweenExclusive", LocalDateTimeUtils.isBetween(date, start, end, false), date, start, end, false);
        printResult("isBetweenExclusive", LocalDateTimeUtils.isBetween(date, start, LocalDateTime.of(2023, 10, 15, 12, 0), false), date, start, LocalDateTime.of(2023, 10, 15, 12, 0), false);
        // Boundary value tests
        printResult("isBetweenExclusive", LocalDateTimeUtils.isBetween(start, start, end, false), start, start, end, false);
        printResult("isBetweenExclusive", LocalDateTimeUtils.isBetween(end, start, end, false), end, start, end, false);
    }

    void isToday() {
        LocalDateTime today = LocalDateTime.now();
        printResult("isToday", LocalDateTimeUtils.isToday(today), today);
        printResult("isToday", LocalDateTimeUtils.isToday(today.minusDays(1)), today.minusDays(1));
    }

    void onSameWeekWithNow() {
        LocalDateTime today = LocalDateTime.now();
        printResult("onSameWeekWithNow", LocalDateTimeUtils.onSameWeekWithNow(today), today);
        printResult("onSameWeekWithNow", LocalDateTimeUtils.onSameWeekWithNow(today.minusWeeks(1)), today.minusWeeks(1));
    }

    void onSameWeek() {
        LocalDateTime today = LocalDateTime.now();
        printResult("onSameWeek", LocalDateTimeUtils.onSameWeek(today, today), today, today);
        printResult("onSameWeek", LocalDateTimeUtils.onSameWeek(today, today.minusWeeks(1)), today, today.minusWeeks(1));
    }

    void onSameMonthWithNow() {
        LocalDateTime today = LocalDateTime.now();
        printResult("onSameMonthWithNow", LocalDateTimeUtils.onSameMonthWithNow(today), today);
        printResult("onSameMonthWithNow", LocalDateTimeUtils.onSameMonthWithNow(today.minusMonths(1)), today.minusMonths(1));
    }

    void onSameMonth() {
        LocalDateTime today = LocalDateTime.now();
        printResult("onSameMonth", LocalDateTimeUtils.onSameMonth(today, today), today, today);
        printResult("onSameMonth", LocalDateTimeUtils.onSameMonth(today, today.minusMonths(1)), today, today.minusMonths(1));
    }

    void onSameYearWithNow() {
        LocalDateTime today = LocalDateTime.now();
        printResult("onSameYearWithNow", LocalDateTimeUtils.onSameYearWithNow(today), today);
        printResult("onSameYearWithNow", LocalDateTimeUtils.onSameYearWithNow(today.minusYears(1)), today.minusYears(1));
    }

    void onSameYear() {
        LocalDateTime today = LocalDateTime.now();
        printResult("onSameYear", LocalDateTimeUtils.onSameYear(today, today), today, today);
        printResult("onSameYear", LocalDateTimeUtils.onSameYear(today, today.minusYears(1)), today, today.minusYears(1));
    }

    void getFirstMoment() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getFirstMoment", LocalDateTimeUtils.getFirstMoment(now), now);
    }

    void getLastMoment() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getLastMoment", LocalDateTimeUtils.getLastMoment(now), now);
    }

    void getWeekDayWithSameTime() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getWeekDayWithSameTime", LocalDateTimeUtils.getWeekDayWithSameTime(now, DayOfWeek.MONDAY, WeekType.THIS_WEEK), now, DayOfWeek.MONDAY, WeekType.THIS_WEEK);
    }

    void getWeekDayWithMomentType() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getWeekDayWithMomentType", LocalDateTimeUtils.getWeekDayWithMomentType(now, DayOfWeek.MONDAY, WeekType.THIS_WEEK, MomentType.FIRST), now, DayOfWeek.MONDAY, WeekType.THIS_WEEK, MomentType.FIRST);
    }

    void getFirstMomentOfWeek() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getFirstMomentOfWeek", LocalDateTimeUtils.getFirstMomentOfWeek(now), now);
    }

    void getLastMomentOfWeek() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getLastMomentOfWeek", LocalDateTimeUtils.getLastMomentOfWeek(now), now);
    }

    void getFirstMomentOfMonth() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getFirstMomentOfMonth", LocalDateTimeUtils.getFirstMomentOfMonth(now), now);
    }

    void getLastMomentOfMonth() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getLastMomentOfMonth", LocalDateTimeUtils.getLastMomentOfMonth(now), now);
    }

    void getFirstMomentOfYear() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getFirstMomentOfYear", LocalDateTimeUtils.getFirstMomentOfYear(now), now);
    }

    void getLastMomentOfYear() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getLastMomentOfYear", LocalDateTimeUtils.getLastMomentOfYear(now), now);
    }

    void getDuration() {
        LocalDateTime start = LocalDateTime.of(2023, 10, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 10, 20, 23, 59);
        printResult("getDuration", LocalDateTimeUtils.getDuration(start, end, ChronoUnit.DAYS), start, end, ChronoUnit.DAYS);
    }

    void getDurationToNow() {
        LocalDateTime past = LocalDateTime.of(2023, 10, 10, 0, 0);
        printResult("getDurationToNow", LocalDateTimeUtils.getDurationToNow(past, ChronoUnit.DAYS), past, ChronoUnit.DAYS);
    }

    void getRemainingDurationOfDay() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getRemainingDurationOfDay", LocalDateTimeUtils.getRemainingDurationOfDay(now, ChronoUnit.HOURS), now, ChronoUnit.HOURS);
    }

    void getRemainingDurationOfWeek() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getRemainingDurationOfWeek", LocalDateTimeUtils.getRemainingDurationOfWeek(now, ChronoUnit.DAYS), now, ChronoUnit.DAYS);
    }

    void getRemainingDurationOfMonth() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getRemainingDurationOfMonth", LocalDateTimeUtils.getRemainingDurationOfMonth(now, ChronoUnit.DAYS), now, ChronoUnit.DAYS);
    }

    void getRemainingDurationOfYear() {
        LocalDateTime now = LocalDateTime.now();
        printResult("getRemainingDurationOfYear", LocalDateTimeUtils.getRemainingDurationOfYear(now, ChronoUnit.DAYS), now, ChronoUnit.DAYS);
    }

    void toMilliseconds() {
        LocalDateTime now = LocalDateTime.now();
        printResult("toMilliseconds", LocalDateTimeUtils.toMilliseconds(now), now);
    }

    void toLocalDateHour() {
        LocalDateTime now = LocalDateTime.now();
        printResult("toLocalDateHour", LocalDateTimeUtils.toLocalDateHour(now), now);
    }

    void toLocalTimeHour() {
        LocalDateTime now = LocalDateTime.now();
        printResult("toLocalTimeHour", LocalDateTimeUtils.toLocalTimeHour(now), now);
    }

    void getEarlierTime() {
        LocalDateTime time1 = LocalDateTime.of(2023, 10, 10, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2023, 10, 20, 23, 59);
        printResult("getEarlierTime", LocalDateTimeUtils.getEarlierTime(time1, time2), time1, time2);
    }

    void getEarliestTime() {
        LocalDateTime time1 = LocalDateTime.of(2023, 10, 10, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2023, 10, 20, 23, 59);
        LocalDateTime time3 = LocalDateTime.of(2023, 10, 5, 12, 0);
        printResult("getEarliestTime", LocalDateTimeUtils.getEarliestTime(time1, time2, time3), time1, time2, time3);
    }

    void getLaterTime() {
        LocalDateTime time1 = LocalDateTime.of(2023, 10, 10, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2023, 10, 20, 23, 59);
        printResult("getLaterTime", LocalDateTimeUtils.getLaterTime(time1, time2), time1, time2);
    }

    void getLatestTime() {
        LocalDateTime time1 = LocalDateTime.of(2023, 10, 10, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2023, 10, 20, 23, 59);
        LocalDateTime time3 = LocalDateTime.of(2023, 10, 25, 12, 0);
        printResult("getLatestTime", LocalDateTimeUtils.getLatestTime(time1, time2, time3), time1, time2, time3);
    }
}