package com.bitprogress.util;

import com.bitprogress.enums.time.WeekType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

public class LocalDateUtils {

    /**
     * 判断传入日期是否在对应的区间内，默认包含区间边界
     *
     * @param toCompare 日期对象
     * @param begin     区间开始日期
     * @param end       区间结束日期
     * @return true：是，false：否
     */
    public static boolean isBetween(LocalDate toCompare, LocalDate begin, LocalDate end) {
        return isBetween(toCompare, begin, end, true);
    }

    /**
     * 判断传入日期是否在对应的区间内
     *
     * @param toCompare 日期对象
     * @param begin     区间开始日期
     * @param end       区间结束日期
     * @param inclusive 是否包含边界值
     * @return true：是，false：否
     */
    public static boolean isBetween(LocalDate toCompare, LocalDate begin, LocalDate end, boolean inclusive) {
        return inclusive
                ? toCompare.isEqual(begin) || toCompare.isEqual(end) || (toCompare.isAfter(begin) && toCompare.isBefore(end))
                : toCompare.isAfter(begin) && toCompare.isBefore(end);
    }

    /**
     * 判断传入时间是否当天
     *
     * @param date 日期对象
     * @return 是否是同一周
     */
    public static boolean isToday(LocalDate date) {
        return Objects.nonNull(date) && date.isEqual(LocalDate.now());
    }

    /**
     * 判断两个日期是否是同一周
     *
     * @param date 日期对象
     * @return 是否是同一周
     */
    public static boolean onSameWeekWithToday(LocalDate date) {
        return onSameWeek(date, LocalDate.now());
    }

    /**
     * 判断两个日期是否是同一周
     *
     * @param date            日期对象
     * @param comparativeDate 比较日期对象
     * @return 是否是同一周
     */
    public static boolean onSameWeek(LocalDate date, LocalDate comparativeDate) {
        if (Objects.isNull(date) || Objects.isNull(comparativeDate)) {
            return false;
        }
        LocalDate firstDay = getFirstDayOfWeek(comparativeDate);
        LocalDate lastDay = getLastDayOfWeek(comparativeDate);
        return date.isEqual(firstDay) || date.isEqual(lastDay) || (date.isAfter(firstDay) && date.isBefore(lastDay));
    }

    /**
     * 判断两个日期是否是同一月
     *
     * @param date 日期对象
     * @return 是否是同一月
     */
    public static boolean onSameMonthWithToday(LocalDate date) {
        return onSameMonth(date, LocalDate.now());
    }

    /**
     * 判断两个日期是否是同一月
     *
     * @param date            日期对象
     * @param comparativeDate 比较日期对象
     * @return 是否是同一月
     */
    public static boolean onSameMonth(LocalDate date, LocalDate comparativeDate) {
        if (Objects.isNull(date) || Objects.isNull(comparativeDate)) {
            return false;
        }
        LocalDate firstDay = getFirstDayOfMonth(comparativeDate);
        LocalDate lastDay = getLastDayOfMonth(comparativeDate);
        return isBetween(date, firstDay, lastDay);
    }

    /**
     * 判断两个日期是否是同一年
     *
     * @param date 日期对象
     * @return 是否是同一年
     */
    public static boolean onSameYearWithToday(LocalDate date) {
        return onSameYear(date, LocalDate.now());
    }

    /**
     * 判断两个日期是否是同一年
     *
     * @param date            日期对象
     * @param comparativeDate 比较日期对象
     * @return 是否是同一年
     */
    public static boolean onSameYear(LocalDate date, LocalDate comparativeDate) {
        if (Objects.isNull(date) || Objects.isNull(comparativeDate)) {
            return false;
        }
        LocalDate firstDay = getFirstDayOfYear(comparativeDate);
        LocalDate lastDay = getLastDayOfYear(comparativeDate);
        return isBetween(date, firstDay, lastDay);
    }

    /**
     * 获取当前周的第一天
     *
     * @return 特定日期
     */
    public static LocalDate getFirstDayOfWeek() {
        return LocalDate.now().with(DayOfWeek.MONDAY);
    }

    /**
     * 获取当前周的第一天
     *
     * @param date 日期对象
     * @return 特定日期
     */
    public static LocalDate getFirstDayOfWeek(LocalDate date) {
        return date.with(DayOfWeek.MONDAY);
    }

    /**
     * 获取当前周的第一天
     *
     * @param time 时间对象
     * @return 特定日期
     */
    public static LocalDate getFirstDayOfWeek(LocalDateTime time) {
        return time.toLocalDate()
                .with(DayOfWeek.MONDAY);
    }

    /**
     * 获取当前周的最后一天
     *
     * @return 特定日期
     */
    public static LocalDate getLastDayOfWeek() {
        return LocalDate.now().with(DayOfWeek.SUNDAY);
    }

    /**
     * 获取当前周的最后一天
     *
     * @param date 日期对象
     * @return 特定日期
     */
    public static LocalDate getLastDayOfWeek(LocalDate date) {
        return date.with(DayOfWeek.SUNDAY);
    }

    /**
     * 获取当前周的最后一天
     *
     * @param time 日期对象
     * @return 特定日期
     */
    public static LocalDate getLastDayOfWeek(LocalDateTime time) {
        return time.toLocalDate()
                .with(DayOfWeek.SUNDAY);
    }

    /**
     * 根据传入时间获取上一周的某一天 （周一 --- 周日）
     *
     * @param date      日期对象
     * @param dayOfWeek 周几
     * @return 特定日期
     */
    private static LocalDate getPreWeekDay(LocalDate date, DayOfWeek dayOfWeek) {
        return date
                .with(dayOfWeek)
                .minusWeeks(1);
    }

    /**
     * 根据传入时间获取上一周的某一天 （周一 --- 周日）
     *
     * @param time      时间对象
     * @param dayOfWeek 周几
     * @return 特定时间
     */
    private static LocalDate getPreWeekDay(LocalDateTime time, DayOfWeek dayOfWeek) {
        return time
                .toLocalDate()
                .with(dayOfWeek)
                .minusWeeks(1);
    }

    /**
     * 根据传入时间获取所在周的某一天当前时刻  （周一 --- 周日）
     *
     * @param date      日期对象
     * @param dayOfWeek 周几
     * @return 特定日期
     */
    private static LocalDate getThisWeekDay(LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(dayOfWeek);
    }

    /**
     * 根据传入时间获取所在周的某一天当前时刻  （周一 --- 周日）
     *
     * @param time      时间对象
     * @param dayOfWeek 周几
     * @return 特定日期
     */
    private static LocalDate getThisWeekDay(LocalDateTime time, DayOfWeek dayOfWeek) {
        return time
                .toLocalDate()
                .with(dayOfWeek);
    }

    /**
     * 根据传入时间获取下一周的某一天 （周一 --- 周日）
     *
     * @param date      日期对象
     * @param dayOfWeek 周几
     * @return 特定日期
     */
    private static LocalDate getNextWeekDay(LocalDate date, DayOfWeek dayOfWeek) {
        return date
                .with(dayOfWeek)
                .plusWeeks(1);
    }

    /**
     * 根据传入时间获取下一周的某一天 （周一 --- 周日）
     *
     * @param time      时间对象
     * @param dayOfWeek 周几
     * @return 特定日期
     */
    private static LocalDate getNextWeekDay(LocalDateTime time, DayOfWeek dayOfWeek) {
        return time
                .toLocalDate()
                .plusWeeks(1)
                .with(dayOfWeek);
    }

    /**
     * 根据传入时间获取所在周的某一天开始或结束时刻  （周一 --- 周日）
     *
     * @param date      日期对象
     * @param dayOfWeek 周几
     * @param weekType  周类型
     * @return 特定时间
     */
    public static LocalDate getWeekDay(LocalDate date,
                                       DayOfWeek dayOfWeek,
                                       WeekType weekType) {
        if (Objects.isNull(date)) {
            throw new RuntimeException("Date can not be null !");
        }
        return switch (weekType) {
            case PRE_WEEK -> getPreWeekDay(date, dayOfWeek);
            case THIS_WEEK -> getThisWeekDay(date, dayOfWeek);
            case NEXT_WEEK -> getNextWeekDay(date, dayOfWeek);
        };
    }

    /**
     * 根据传入时间获取所在周的某一天开始或结束时刻  （周一 --- 周日）
     *
     * @param time      时间对象
     * @param dayOfWeek 周几
     * @param weekType  周类型
     * @return 特定时间
     */
    public static LocalDate getWeekDay(LocalDateTime time,
                                       DayOfWeek dayOfWeek,
                                       WeekType weekType) {
        if (Objects.isNull(time)) {
            throw new RuntimeException("Time can not be null !");
        }
        return switch (weekType) {
            case PRE_WEEK -> getPreWeekDay(time, dayOfWeek);
            case THIS_WEEK -> getThisWeekDay(time, dayOfWeek);
            case NEXT_WEEK -> getNextWeekDay(time, dayOfWeek);
        };
    }

    /**
     * 获取传入日期月份第一天
     *
     * @param date 日期对象
     * @return 特定日期
     */
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取传入时间月份第一天
     *
     * @param time 时间对象
     * @return 特定日期
     */
    public static LocalDate getFirstDayOfMonth(LocalDateTime time) {
        return time
                .toLocalDate()
                .with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取传入日期月份最后一天
     *
     * @param date 日期对象
     * @return 特定日期
     */
    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取传入时间月份最后一天
     *
     * @param time 时间对象
     * @return 特定日期
     */
    public static LocalDate getLastDayOfMonth(LocalDateTime time) {
        return time
                .toLocalDate()
                .with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取传入时间所属年份的第一天
     *
     * @param date 时间对象
     * @return 特定日期
     */
    public static LocalDate getFirstDayOfYear(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * 获取传入时间所属年份的第一天
     *
     * @param time 时间对象
     * @return 特定日期
     */
    public static LocalDate getFirstDayOfYear(LocalDateTime time) {
        return time
                .toLocalDate()
                .with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * 获取传入时间所属年份的最后一天
     *
     * @param date 时间对象
     * @return 特定日期
     */
    public static LocalDate getLastDayOfYear(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfYear());
    }

    /**
     * 获取传入时间所属年份的最后一天
     *
     * @param time 时间对象
     * @return 特定日期
     */
    public static LocalDate getLastDayOfYear(LocalDateTime time) {
        return time
                .toLocalDate()
                .with(TemporalAdjusters.lastDayOfYear());
    }

}
