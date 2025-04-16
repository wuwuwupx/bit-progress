package com.bitprogress.core;

import com.bitprogress.basemodel.enums.time.WeekType;
import com.bitprogress.util.LocalDateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;

public class LocalDateUtilsTest {

    public static void main(String[] args) {
        LocalDateUtilsTest test = new LocalDateUtilsTest();
        test.isBetweenInclusive();
        test.isBetweenExclusive();
        test.isToday();
        test.onSameWeekWithToday();
        test.onSameWeek();
        test.onSameMonthWithToday();
        test.onSameMonth();
        test.onSameYearWithToday();
        test.onSameYear();
        test.getFirstDayOfWeek();
        test.getLastDayOfWeek();
        test.getFirstDayOfMonth();
        test.getLastDayOfMonth();
        test.getFirstDayOfYear();
        test.getLastDayOfYear();
        test.getWeekDay();
        test.getWeekDayWithTime();
    }

    private void printResult(String methodName, Object result, Object... params) {
        System.out.println(methodName + " with params " + java.util.Arrays.toString(params) + ": " + result);
    }

    void isBetweenInclusive() {
        LocalDate date = LocalDate.of(2023, 10, 15);
        LocalDate start = LocalDate.of(2023, 10, 10);
        LocalDate end = LocalDate.of(2023, 10, 20);
        printResult("isBetweenInclusive", LocalDateUtils.isBetween(date, start, end), date, start, end);
        printResult("isBetweenInclusive", LocalDateUtils.isBetween(date, start, LocalDate.of(2023, 10, 14)), date, start, LocalDate.of(2023, 10, 14));
        // Boundary value tests
        printResult("isBetweenInclusive", LocalDateUtils.isBetween(start, start, end), start, start, end);
        printResult("isBetweenInclusive", LocalDateUtils.isBetween(end, start, end), end, start, end);
    }

    void isBetweenExclusive() {
        LocalDate date = LocalDate.of(2023, 10, 15);
        LocalDate start = LocalDate.of(2023, 10, 10);
        LocalDate end = LocalDate.of(2023, 10, 20);
        printResult("isBetweenExclusive", LocalDateUtils.isBetween(date, start, end, false), date, start, end, false);
        printResult("isBetweenExclusive", LocalDateUtils.isBetween(date, start, LocalDate.of(2023, 10, 15), false), date, start, LocalDate.of(2023, 10, 15), false);
        // Boundary value tests
        printResult("isBetweenExclusive", LocalDateUtils.isBetween(start, start, end, false), start, start, end, false);
        printResult("isBetweenExclusive", LocalDateUtils.isBetween(end, start, end, false), end, start, end, false);
    }

    void isToday() {
        LocalDate today = LocalDate.now();
        printResult("isToday", LocalDateUtils.isToday(today), today);
        printResult("isToday", LocalDateUtils.isToday(today.minusDays(1)), today.minusDays(1));
    }

    void onSameWeekWithToday() {
        LocalDate today = LocalDate.now();
        printResult("onSameWeekWithToday", LocalDateUtils.onSameWeekWithToday(today), today);
        printResult("onSameWeekWithToday", LocalDateUtils.onSameWeekWithToday(today.minusWeeks(1)), today.minusWeeks(1));
        printResult("onSameWeekWithToday", LocalDateUtils.onSameWeekWithToday(today.with(DayOfWeek.MONDAY).minusDays(1)), today.with(DayOfWeek.MONDAY).minusDays(1));
        printResult("onSameWeekWithToday", LocalDateUtils.onSameWeekWithToday(today.with(DayOfWeek.MONDAY)), today.with(DayOfWeek.MONDAY));
        printResult("onSameWeekWithToday", LocalDateUtils.onSameWeekWithToday(today.with(DayOfWeek.SUNDAY)), today.with(DayOfWeek.SUNDAY));
        printResult("onSameWeekWithToday", LocalDateUtils.onSameWeekWithToday(today.with(DayOfWeek.SUNDAY).plusDays(1)), today.with(DayOfWeek.SUNDAY).plusDays(1));
    }

    void onSameWeek() {
        LocalDate today = LocalDate.now();
        printResult("onSameWeek", LocalDateUtils.onSameWeek(today, today), today, today);
        printResult("onSameWeek", LocalDateUtils.onSameWeek(today, today.minusWeeks(1)), today, today.minusWeeks(1));
        printResult("onSameWeek", LocalDateUtils.onSameWeek(today, today.with(DayOfWeek.MONDAY).minusDays(1)), today, today.with(DayOfWeek.MONDAY).minusDays(1));
        printResult("onSameWeek", LocalDateUtils.onSameWeek(today, today.with(DayOfWeek.MONDAY)), today, today.with(DayOfWeek.MONDAY));
        printResult("onSameWeek", LocalDateUtils.onSameWeek(today, today.with(DayOfWeek.SUNDAY)), today, today.with(DayOfWeek.SUNDAY));
        printResult("onSameWeek", LocalDateUtils.onSameWeek(today, today.with(DayOfWeek.SUNDAY).plusDays(1)), today, today.with(DayOfWeek.SUNDAY).plusDays(1));
    }

    void onSameMonthWithToday() {
        LocalDate today = LocalDate.now();
        printResult("onSameMonthWithToday", LocalDateUtils.onSameMonthWithToday(today), today);
        printResult("onSameMonthWithToday", LocalDateUtils.onSameMonthWithToday(today.minusMonths(1)), today.minusMonths(1));
        printResult("onSameMonthWithToday", LocalDateUtils.onSameMonthWithToday(today.with(TemporalAdjusters.firstDayOfMonth())), today.with(TemporalAdjusters.firstDayOfMonth()));
        printResult("onSameMonthWithToday", LocalDateUtils.onSameMonthWithToday(today.with(TemporalAdjusters.firstDayOfMonth()).minusDays(1)), today.with(TemporalAdjusters.firstDayOfMonth()).minusDays(1));
        printResult("onSameMonthWithToday", LocalDateUtils.onSameMonthWithToday(today.with(TemporalAdjusters.lastDayOfMonth())), today.with(TemporalAdjusters.lastDayOfMonth()));
        printResult("onSameMonthWithToday", LocalDateUtils.onSameMonthWithToday(today.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1)), today.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1));
    }

    void onSameMonth() {
        LocalDate today = LocalDate.now();
        printResult("onSameMonth", LocalDateUtils.onSameMonth(today, today), today, today);
        printResult("onSameMonth", LocalDateUtils.onSameMonth(today, today.minusMonths(1)), today, today.minusMonths(1));
        printResult("onSameMonth", LocalDateUtils.onSameMonth(today, today.with(TemporalAdjusters.firstDayOfMonth())), today, today.with(TemporalAdjusters.firstDayOfMonth()));
        printResult("onSameMonth", LocalDateUtils.onSameMonth(today, today.with(TemporalAdjusters.firstDayOfMonth()).minusDays(1)), today, today.with(TemporalAdjusters.firstDayOfMonth()).minusDays(1));
        printResult("onSameMonth", LocalDateUtils.onSameMonth(today, today.with(TemporalAdjusters.lastDayOfMonth())), today, today.with(TemporalAdjusters.lastDayOfMonth()));
        printResult("onSameMonth", LocalDateUtils.onSameMonth(today, today.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1)), today, today.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1));
    }

    void onSameYearWithToday() {
        LocalDate today = LocalDate.now();
        printResult("onSameYearWithToday", LocalDateUtils.onSameYearWithToday(today), today);
        printResult("onSameYearWithToday", LocalDateUtils.onSameYearWithToday(today.minusYears(1)), today.minusYears(1));
        printResult("onSameYearWithToday", LocalDateUtils.onSameYearWithToday(today.with(TemporalAdjusters.firstDayOfYear())), today.with(TemporalAdjusters.firstDayOfYear()));
        printResult("onSameYearWithToday", LocalDateUtils.onSameYearWithToday(today.with(TemporalAdjusters.firstDayOfYear()).minusDays(1)), today.with(TemporalAdjusters.firstDayOfYear()).minusDays(1));
        printResult("onSameYearWithToday", LocalDateUtils.onSameYearWithToday(today.with(TemporalAdjusters.lastDayOfYear())), today.with(TemporalAdjusters.lastDayOfYear()));
        printResult("onSameYearWithToday", LocalDateUtils.onSameYearWithToday(today.with(TemporalAdjusters.lastDayOfYear()).plusDays(1)), today.with(TemporalAdjusters.lastDayOfYear()).plusDays(1));
    }

    void onSameYear() {
        LocalDate today = LocalDate.now();
        printResult("onSameYear", LocalDateUtils.onSameYear(today, today), today, today);
        printResult("onSameYear", LocalDateUtils.onSameYear(today, today.minusYears(1)), today, today.minusYears(1));
        printResult("onSameYear", LocalDateUtils.onSameYear(today, today.with(TemporalAdjusters.firstDayOfYear())), today, today.with(TemporalAdjusters.firstDayOfYear()));
        printResult("onSameYear", LocalDateUtils.onSameYear(today, today.with(TemporalAdjusters.firstDayOfYear()).minusDays(1)), today, today.with(TemporalAdjusters.firstDayOfYear()).minusDays(1));
        printResult("onSameYear", LocalDateUtils.onSameYear(today, today.with(TemporalAdjusters.lastDayOfYear())), today, today.with(TemporalAdjusters.lastDayOfYear()));
        printResult("onSameYear", LocalDateUtils.onSameYear(today, today.with(TemporalAdjusters.lastDayOfYear()).plusDays(1)), today, today.with(TemporalAdjusters.lastDayOfYear()).plusDays(1));
    }

    void getFirstDayOfWeek() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfWeek = LocalDateUtils.getFirstDayOfWeek(today);
        printResult("getFirstDayOfWeek", firstDayOfWeek, today);
    }

    void getLastDayOfWeek() {
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfWeek = LocalDateUtils.getLastDayOfWeek(today);
        printResult("getLastDayOfWeek", lastDayOfWeek, today);
    }

    void getFirstDayOfMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = LocalDateUtils.getFirstDayOfMonth(today);
        printResult("getFirstDayOfMonth", firstDayOfMonth, today);
    }

    void getLastDayOfMonth() {
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfMonth = LocalDateUtils.getLastDayOfMonth(today);
        printResult("getLastDayOfMonth", lastDayOfMonth, today);
    }

    void getFirstDayOfYear() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfYear = LocalDateUtils.getFirstDayOfYear(today);
        printResult("getFirstDayOfYear", firstDayOfYear, today);
    }

    void getLastDayOfYear() {
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfYear = LocalDateUtils.getLastDayOfYear(today);
        printResult("getLastDayOfYear", lastDayOfYear, today);
    }

    void getWeekDay() {
        LocalDate today = LocalDate.now();
        LocalDate monday = LocalDateUtils.getWeekDay(today, DayOfWeek.MONDAY, WeekType.PRE_WEEK);
        printResult("getWeekDay", monday, today, DayOfWeek.MONDAY, WeekType.PRE_WEEK);
        monday = LocalDateUtils.getWeekDay(today, DayOfWeek.MONDAY, WeekType.THIS_WEEK);
        printResult("getWeekDay", monday, today, DayOfWeek.MONDAY, WeekType.THIS_WEEK);
        monday = LocalDateUtils.getWeekDay(today, DayOfWeek.MONDAY, WeekType.NEXT_WEEK);
        printResult("getWeekDay", monday, today, DayOfWeek.MONDAY, WeekType.NEXT_WEEK);
    }

    void getWeekDayWithTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate monday = LocalDateUtils.getWeekDay(now, DayOfWeek.MONDAY, WeekType.PRE_WEEK);
        printResult("getWeekDayWithTime", monday, now, DayOfWeek.MONDAY, WeekType.PRE_WEEK);
        monday = LocalDateUtils.getWeekDay(now, DayOfWeek.MONDAY, WeekType.THIS_WEEK);
        printResult("getWeekDayWithTime", monday, now, DayOfWeek.MONDAY, WeekType.THIS_WEEK);
        monday = LocalDateUtils.getWeekDay(now, DayOfWeek.MONDAY, WeekType.NEXT_WEEK);
        printResult("getWeekDayWithTime", monday, now, DayOfWeek.MONDAY, WeekType.NEXT_WEEK);
    }

}