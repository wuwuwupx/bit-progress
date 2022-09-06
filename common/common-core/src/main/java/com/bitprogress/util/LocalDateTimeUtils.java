package com.bitprogress.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;

/**
 * LocalDateTime 工具类
 *
 * @author wukun
 * created on 2019/10/29
 */
public class LocalDateTimeUtils {

    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    /**
     * 时刻类型
     */
    public enum MomentType {
        /**
         * 一天的第一个时刻
         */
        FIRST,

        /**
         * 一天的最后一个时刻(最后一个时刻仅精确到秒)
         */
        LAST,
        ;
    }

    /**
     * 周类型
     */
    public enum WeekType {
        /**
         * 上一周
         */
        PRE_WEEK,

        /**
         * 当前周
         */
        THIS_WEEK,

        /**
         * 当前周
         */
        NEXT_WEEK,
        ;
    }

    /**
     * 传入时间是否与当前时间在同一天
     *
     * @param    time
     * @return   Boolean
     */
    public static Boolean onSameDayWithNow(LocalDateTime time) {
        return Objects.nonNull(time) && onSameDay(time, LocalDateTime.now());
    }

    /**
     *
     * 比较两个时间是否在同一天
     *
     * @param    time
     * @param    comparativeTime
     * @return   Boolean
     */
    public static Boolean onSameDay(LocalDateTime time, LocalDateTime comparativeTime) {
        return Objects.nonNull(time) && Objects.nonNull(comparativeTime) && time.toLocalDate().isEqual(comparativeTime.toLocalDate());
    }

    /**
     *
     * 比较传入时间与当前时间是否在同一周
     *
     * @param    time
     * @return   Boolean
     */
    public static Boolean onSameWeekWithNow(LocalDateTime time) {
        return Objects.nonNull(time) && onSameWeek(time, LocalDateTime.now());
    }

    /**
     *
     * 比较两个时间是否在同一周
     *
     * @param    time
     * @param    comparativeTime
     * @return   Boolean
     */
    public static Boolean onSameWeek(LocalDateTime time, LocalDateTime comparativeTime) {
        if (Objects.isNull(time) || Objects.isNull(comparativeTime)) {
            return false;
        }
        LocalDateTime weekFirst = getWeekFirstMoment(comparativeTime);
        LocalDateTime weekLast = getWeekLastMoment(comparativeTime);
        return time.isAfter(weekFirst) && time.isBefore(weekLast) || time.isEqual(weekFirst) || time.isEqual(weekLast);
    }

    /**
     *
     * 比较传入时间与当前时间是否在同一月
     *
     * @param    time
     * @return   Boolean
     */
    public static Boolean onSameMonthWithNow(LocalDateTime time) {
        return Objects.nonNull(time) && onSameMonth(time, LocalDateTime.now());
    }

    /**
     *
     * 比较两个时间是否在同一周
     *
     * @param    time
     * @return   Boolean
     */
    public static Boolean onSameMonth(LocalDateTime time, LocalDateTime comparativeTime) {
        if (Objects.isNull(time) || Objects.isNull(comparativeTime)) {
            return false;
        }
        LocalDateTime monthFirst = getFirstDayOfMonth(comparativeTime, MomentType.FIRST);
        LocalDateTime monthLast = getLastDayOfMonth(comparativeTime, MomentType.LAST);
        return time.isAfter(monthFirst) && time.isBefore(monthLast) || time.isEqual(monthFirst) || time.isEqual(monthLast);
    }

    /**
     * 获取传入的LocalDateTime对象当天的第一个时刻
     *
     * @param time time
     * @return 第一个时刻
     */
    public static LocalDateTime getFirstMoment(LocalDateTime time) {
        return time.toLocalDate().atTime(LocalTime.MIN);
    }

    /**
     * 获取传入的LocalDateTime对象当天的的最后一个时刻(最后一个时刻仅精确到秒)
     *
     * @param time time
     * @return 最后一个时刻
     */
    public static LocalDateTime getLastMoment(LocalDateTime time) {
        return time.toLocalDate().atTime(LocalTime.MAX);
    }

    /**
     * 将时间对象的时刻转化为指定的时刻类型
     *
     * @param time 时间对象
     * @param type 时刻类型
     * @return 时间对象的时刻转化为指定的时刻类型
     */
    private static LocalDateTime getMomentByType(LocalDateTime time, MomentType type) {
        if (Objects.isNull(time)) {
            throw new RuntimeException("Time can not be null !");
        }

        switch (type) {
            case FIRST:
                return getFirstMoment(time);
            case LAST:
                return getLastMoment(time);
            default:
                return time;
        }
    }

    /**
     * 根据传入的时间获取所在月第一天 00:00:00
     *
     * @param time time
     * @return 所在月第一天
     */
    public static LocalDateTime getFirstDayOfMonth(LocalDateTime time) {
        return getMomentByType(time.with(TemporalAdjusters.firstDayOfMonth()), MomentType.FIRST);
    }

    /**
     *
     * 获取传入的时间所在月的第一天的传入时刻
     *
     * @param    time
     * @return   LocalDateTime
     */
    public static LocalDateTime getMomentFirstDayOfMonth(LocalDateTime time) {
        return time.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 根据传入的时间获取所在月第一天
     *
     * @param time time
     * @param type 时刻的类型
     * @return 所在月第一天
     */
    public static LocalDateTime getFirstDayOfMonth(LocalDateTime time, MomentType type) {
        return getMomentByType(time.with(TemporalAdjusters.firstDayOfMonth()), type);
    }

    /**
     *
     * 根据传入时间获取所在周的某一天开始或结束时刻  （周一 --- 周日）
     *
     * @param    time
     * @param    dayOfWeek
     * @param    weekType
     * @param    momentType
     * @return   LocalDateTime
     */
    public static LocalDateTime getWeekDay(LocalDateTime time, DayOfWeek dayOfWeek, WeekType weekType, MomentType momentType){
        if (Objects.isNull(time)) {
            throw new RuntimeException("Time can not be null !");
        }
        switch (momentType){
            case FIRST:
                return getWeekDay(time.toLocalDate().atTime(LocalTime.MIN), dayOfWeek, weekType);
            case LAST:
                return getWeekDay(time.toLocalDate().atTime(LocalTime.MAX), dayOfWeek, weekType);
            default:
                return time;
        }
    }

    /**
     *
     * 根据传入时间获取所在周的某一天的当前时刻  （周一 --- 周日）
     *
     * @param    time
     * @param    dayOfWeek
     * @param    type
     * @return   LocalDateTime
     */
    public static LocalDateTime getWeekDay(LocalDateTime time, DayOfWeek dayOfWeek, WeekType type){
        if (Objects.isNull(time)) {
            throw new RuntimeException("Time can not be null !");
        }
        switch (type){
            case PRE_WEEK:
                return getPreWeekDay(time, dayOfWeek);
            case THIS_WEEK:
                return getThisWeekDay(time, dayOfWeek);
            case NEXT_WEEK:
                return getNextWeekDay(time, dayOfWeek);
            default:
                return time;
        }
    }

    /**
     *
     * 根据传入时间获取上一周的某一天 （周一 --- 周日）
     *
     * @Author   wpx
     * @param    time
     * @param    dayOfWeek
     * @return   LocalDateTime
     */
    private static LocalDateTime getPreWeekDay(LocalDateTime time, DayOfWeek dayOfWeek){
        return getThisWeekDay(time.plusWeeks(-1), dayOfWeek);
    }

    /**
     *
     * 根据传入时间获取所在周的某一天当前时刻  （周一 --- 周日）
     *
     * @Author   wpx
     * @param    time
     * @param    dayOfWeek
     * @return   LocalDateTime
     */
    private static LocalDateTime getThisWeekDay(LocalDateTime time, DayOfWeek dayOfWeek){
        return Objects.equals(dayOfWeek, time.getDayOfWeek()) ? time : getDayOfWeek(time, dayOfWeek);
    }

    /**
     *
     * 根据传入时间获取下一周的某一天 （周一 --- 周日）
     *
     * @Author   wpx
     * @param    time
     * @param    dayOfWeek
     * @return   LocalDateTime
     */
    private static LocalDateTime getNextWeekDay(LocalDateTime time, DayOfWeek dayOfWeek){
        return getThisWeekDay(time.plusWeeks(1), dayOfWeek);
    }

    /**
     *
     * 根据传入时间获取所在周的某一天
     *
     * @Author   wpx
     * @param    time
     * @param    dayOfWeek
     * @return   LocalDateTime
     */
    private static LocalDateTime getDayOfWeek(LocalDateTime time, DayOfWeek dayOfWeek) {
        return time.getDayOfWeek().getValue() > dayOfWeek.getValue() ? time.with(TemporalAdjusters.previous(dayOfWeek)) : time.with(TemporalAdjusters.next(dayOfWeek));
    }

    /**
     *
     * 获取传入时间所在周的开始时刻 周一 00:00:00
     *
     * @param    time
     * @return   LocalDateTime
     */
    public static LocalDateTime getWeekFirstMoment(LocalDateTime time){
        return getWeekDay(time, DayOfWeek.MONDAY, WeekType.THIS_WEEK, MomentType.FIRST);
    }

    /**
     *
     * 获取传入时间所在周的结束时刻 周日 23:59:59
     *
     * @param    time
     * @return   LocalDateTime
     */
    public static LocalDateTime getWeekLastMoment(LocalDateTime time){
        return getWeekDay(time, DayOfWeek.SUNDAY, WeekType.THIS_WEEK, MomentType.LAST);
    }

    /**
     * 获取下一天
     *
     * @param time LocalDateTime
     * @param type 时刻的类型
     * @return 下一天的
     */
    public static LocalDateTime getNextDay(LocalDateTime time, MomentType type) {
        return getMomentByType(time.plusDays(1), type);
    }

    /**
     * 获取下一天 00:00:00
     *
     * @param time LocalDateTime
     * @return 下一天的 00:00:00
     */
    public static LocalDateTime getNextDay(LocalDateTime time) {
        return getMomentByType(time.plusDays(1), MomentType.FIRST);
    }

    /**
     * 根据传入的LocalDateTime 转为对应当前月的最后一天
     *
     * @param time LocalDateTime
     * @param type 时刻的类型
     * @return 当前月的最后一天
     */
    public static LocalDateTime getLastDayOfMonth(LocalDateTime time, MomentType type) {
        return getMomentByType(time.with(TemporalAdjusters.lastDayOfMonth()), type);
    }


    /**
     * 格式化 LocalDateTime , 默认 'yyyy-MM-dd HH:mm:ss' 格式.
     *
     * @param time LocalDateTime
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String format(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN));
    }

    /**
     * 格式化 LocalDateTime , 手动指定日期格式.
     *
     * @param time    LocalDateTime
     * @param pattern 日期格式
     * @return LocalDateTime with pattern
     */
    public static String format(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 比较两个两个日期的差值(绝对值),以毫秒的形式返回
     *
     * @param one one
     * @param two two
     * @return 日期毫秒差值(绝对值)
     */
    public static long getDuration(LocalDateTime one, LocalDateTime two) {
        return Math.abs(toMilliseconds(two) - toMilliseconds(one));
    }

    /**
     * 计算从指定时间到现在的差值(绝对值),以毫秒的形式返回
     *
     * @param time time
     * @return 指定时间到现在的差值(绝对值)
     */
    public static long getDurationFromNow(LocalDateTime time) {
        return Math.abs(toMilliseconds(time) - toMilliseconds(LocalDateTime.now()));
    }

    /**
     * 从现在到明天00:00:00 的毫秒值
     *
     * @return 从现在到明天00:00:00 的毫秒值
     */
    public static long getDurationFromNowToNextDay() {
        LocalDateTime now = LocalDateTime.now();
        return Math.abs(toMilliseconds(now) - toMilliseconds(getNextDay(now)));
    }

    /**
     * LocalDateTime 转 毫秒
     *
     * @param time LocalDateTime
     * @return 对应的毫秒值
     */
    public static long toMilliseconds(LocalDateTime time) {
        return time.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 判断日期是否在指定时间范围内
     *
     * @param toCompare 待判断的日期
     * @param begin     范围起始值
     * @param end       范围结束值
     * @return 是否在指定时间范围内
     */
    public boolean isBetween(LocalDateTime toCompare, LocalDateTime begin, LocalDateTime end) {
        if (begin.isAfter(end)) {
            throw new IllegalArgumentException("The begin time can not be after the end time!");
        }
        return toCompare.isAfter(begin) && toCompare.isBefore(end);
    }

    /**
     * 根据传入时间格式化到小时
     *
     * @param    time
     * @return   LocalDateTime
     */
    public static LocalDateTime toLocalDateHour(LocalDateTime time) {
        return time.toLocalDate().atTime(time.getHour(), 0, 0);
    }

    /**
     * 根据传入时间格式化到小时  不包含日期
     *
     * @param    time
     * @return   LocalTime
     */
    public static LocalTime toLocalTimeHour(LocalDateTime time) {
        return toLocalDateHour(time).toLocalTime();
    }

    /**
     * 转换为 date
     *
     * @param time
     * @return
     */
    public static Date toDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取最大的时间
     *
     * @param    time
     * @param    dateTime
     * @return   LocalDateTime
     */
    public static LocalDateTime maxTime(LocalDateTime time, LocalDateTime dateTime) {
        if (Objects.isNull(time) || Objects.isNull(dateTime)) {
            throw new IllegalArgumentException("time can't null");
        }
        return time.isAfter(dateTime) ? time : dateTime;
    }

    /**
     * 获取最小时间
     *
     * @param    time
     * @param    dateTime
     * @return   LocalDateTime
     */
    public static LocalDateTime minTime(LocalDateTime time, LocalDateTime dateTime) {
        if (Objects.isNull(time) || Objects.isNull(dateTime)) {
            throw new IllegalArgumentException("time can't null");
        }
        return time.isBefore(dateTime) ? time : dateTime;
    }

}
