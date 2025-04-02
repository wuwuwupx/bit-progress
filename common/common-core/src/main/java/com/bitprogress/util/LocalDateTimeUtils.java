package com.bitprogress.util;

import com.bitprogress.basemodel.enums.time.MomentType;
import com.bitprogress.basemodel.enums.time.WeekType;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Objects;

/**
 * LocalDateTime 工具类
 */
public class LocalDateTimeUtils {

    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(DEFAULT_DATE_TIME_PATTERN)
            .optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
            .toFormatter();
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);
    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
    public static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN);

    /**
     * 格式化 LocalDateTime , 默认 'yyyy-MM-dd HH:mm:ss' 格式.
     *
     * @param time LocalDateTime
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String format(LocalDateTime time) {
        return format(time, DEFAULT_DATE_TIME_FORMATTER);
    }

    /**
     * 格式化 LocalDateTime , 手动指定日期格式.
     *
     * @param time    LocalDateTime
     * @param pattern 日期格式
     * @return LocalDateTime with pattern
     */
    public static String format(LocalDateTime time, String pattern) {
        return format(time, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化 LocalDateTime , 手动指定日期格式.
     *
     * @param time      LocalDateTime
     * @param formatter 日期格式
     * @return LocalDateTime with formatter
     */
    public static String format(LocalDateTime time, DateTimeFormatter formatter) {
        return time.format(formatter);
    }

    /**
     * Converts a string to LocalDateTime using the specified formatter
     *
     * @param time LocalDateTime
     * @return LocalDateTime with default formatter
     */
    public static LocalDateTime parse(String time) {
        return parse(time, DEFAULT_DATE_TIME_FORMATTER);
    }

    /**
     * Converts a string to LocalDateTime using the specified pattern
     *
     * @param time    LocalDateTime
     * @param pattern 日期格式
     * @return LocalDateTime with pattern
     */
    public static LocalDateTime parse(String time, String pattern) {
        return parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Converts a string to LocalDateTime using the specified formatter
     *
     * @param time      LocalDateTime
     * @param formatter 日期格式
     * @return LocalDateTime with formatter
     */
    public static LocalDateTime parse(String time, DateTimeFormatter formatter) {
        return LocalDateTime.parse(time, formatter);
    }

    /**
     * 判断时间是否在指定时间范围内, 默认包含边界值
     *
     * @param toCompare 待判断的日期
     * @param begin     范围起始值
     * @param end       范围结束值
     * @return 是否在指定时间范围内
     */
    public static boolean isBetween(LocalDateTime toCompare, LocalDateTime begin, LocalDateTime end) {
        return isBetween(toCompare, begin, end, true);
    }

    /**
     * 判断时间是否在指定时间范围内
     *
     * @param toCompare 待判断的日期
     * @param begin     范围起始值
     * @param end       范围结束值
     * @param inclusive 是否包含边界值
     * @return 是否在指定时间范围内
     */
    public static boolean isBetween(LocalDateTime toCompare, LocalDateTime begin, LocalDateTime end, boolean inclusive) {
        if (begin.isAfter(end)) {
            throw new IllegalArgumentException("The begin time can not be after the end time!");
        }
        return inclusive
                ? toCompare.isEqual(begin) || toCompare.isEqual(end) || (toCompare.isAfter(begin) && toCompare.isBefore(end))
                : toCompare.isAfter(begin) && toCompare.isBefore(end);
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
    public static LocalDateTime getMomentByType(LocalDateTime time, MomentType type) {
        if (Objects.isNull(time)) {
            throw new RuntimeException("Time can not be null !");
        }
        return switch (type) {
            case FIRST -> getFirstMoment(time);
            case LAST -> getLastMoment(time);
        };
    }

    /**
     * 传入时间是否与当前时间在同一天
     *
     * @param time 传入时间
     * @return Boolean
     */
    public static boolean isToday(LocalDateTime time) {
        return Objects.nonNull(time) && time.toLocalDate().isEqual(LocalDate.now());
    }

    /**
     * 比较两个时间是否在同一天
     *
     * @return true：在同一天，false：不在同一天
     */
    public static Boolean onSameDay(LocalDateTime time, LocalDateTime comparativeTime) {
        return Objects.nonNull(time)
                && Objects.nonNull(comparativeTime)
                && time.toLocalDate().isEqual(comparativeTime.toLocalDate());
    }

    /**
     * 比较传入时间与当前时间是否在同一周
     *
     * @return true：在同一周，false：不在同一周
     */
    public static Boolean onSameWeekWithNow(LocalDateTime time) {
        return onSameWeek(time, LocalDateTime.now());
    }

    /**
     * 比较两个时间是否在同一周
     *
     * @return true：在同一周，false：不在同一周
     */
    public static Boolean onSameWeek(LocalDateTime time, LocalDateTime comparativeTime) {
        if (Objects.isNull(time) || Objects.isNull(comparativeTime)) {
            return false;
        }
        LocalDate weekFirst = LocalDateUtils.getFirstDayOfWeek(comparativeTime);
        LocalDate weekLast = LocalDateUtils.getLastDayOfWeek(comparativeTime);
        return LocalDateUtils.isBetween(time.toLocalDate(), weekFirst, weekLast);
    }

    /**
     * 比较传入时间与当前时间是否在同一月
     *
     * @param time 检查的时间
     * @return Boolean
     */
    public static Boolean onSameMonthWithNow(LocalDateTime time) {
        return LocalDateUtils.onSameMonthWithToday(time.toLocalDate());
    }

    /**
     * 比较两个时间是否在同一月
     *
     * @param time 检查的时间
     * @return Boolean
     */
    public static Boolean onSameMonth(LocalDateTime time, LocalDateTime comparativeTime) {
        if (Objects.isNull(time) || Objects.isNull(comparativeTime)) {
            return false;
        }
        return LocalDateUtils.onSameMonth(time.toLocalDate(), comparativeTime.toLocalDate());
    }

    /**
     * 比较传入时间与当前时间是否在同一年
     *
     * @param time 检查的时间
     * @return Boolean
     */
    public static Boolean onSameYearWithNow(LocalDateTime time) {
        return LocalDateUtils.onSameYearWithToday(time.toLocalDate());
    }

    /**
     * 比较两个时间是否在同一年
     *
     * @param time 检查的时间
     * @return Boolean
     */
    public static Boolean onSameYear(LocalDateTime time, LocalDateTime comparativeTime) {
        if (Objects.isNull(time) || Objects.isNull(comparativeTime)) {
            return false;
        }
        return LocalDateUtils.onSameYear(time.toLocalDate(), comparativeTime.toLocalDate());
    }

    /**
     * 获取下一天 00:00:00
     *
     * @param time LocalDateTime
     * @return 下一天的 00:00:00
     */
    public static LocalDateTime getFirstMomentOfNextDay(LocalDateTime time) {
        if (Objects.isNull(time)) {
            throw new RuntimeException("Time can not be null !");
        }
        return time
                .plusDays(1)
                .with(LocalTime.MIN);
    }

    /**
     * 根据传入时间获取上一周的某一天 （周一 --- 周日）
     *
     * @param time      时间对象
     * @param dayOfWeek 周几
     * @return 特定时间
     */
    private static LocalDateTime getPreWeekDayWithSameTime(LocalDateTime time, DayOfWeek dayOfWeek) {
        return time
                .with(dayOfWeek)
                .minusWeeks(1);
    }

    /**
     * 根据传入时间获取所在周的某一天当前时刻  （周一 --- 周日）
     *
     * @param time      时间对象
     * @param dayOfWeek 周几
     * @return 特定日期
     */
    private static LocalDateTime getThisWeekDayWithSameTime(LocalDateTime time, DayOfWeek dayOfWeek) {
        return time
                .with(dayOfWeek);
    }

    /**
     * 根据传入时间获取下一周的某一天 （周一 --- 周日）
     *
     * @param time      时间对象
     * @param dayOfWeek 周几
     * @return 特定日期
     */
    private static LocalDateTime getNextWeekDayWithSameTime(LocalDateTime time, DayOfWeek dayOfWeek) {
        return time
                .plusWeeks(1)
                .with(dayOfWeek);
    }

    /**
     * 根据传入时间获取所在周的某一天的当前时刻  （周一 --- 周日）
     *
     * @param time      时间对象
     * @param dayOfWeek 周几
     * @param type      周类型
     * @return 特定时间
     */
    public static LocalDateTime getWeekDayWithSameTime(LocalDateTime time, DayOfWeek dayOfWeek, WeekType type) {
        if (Objects.isNull(time)) {
            throw new RuntimeException("Time can not be null !");
        }
        return switch (type) {
            case PRE_WEEK -> getPreWeekDayWithSameTime(time, dayOfWeek);
            case THIS_WEEK -> getThisWeekDayWithSameTime(time, dayOfWeek);
            case NEXT_WEEK -> getNextWeekDayWithSameTime(time, dayOfWeek);
        };
    }

    /**
     * 根据传入时间获取所在周的某一天开始或结束时刻  （周一 --- 周日）
     *
     * @param time       时间对象
     * @param dayOfWeek  周几
     * @param weekType   周类型
     * @param momentType 时刻类型
     * @return 特定时间
     */
    public static LocalDateTime getWeekDayWithMomentType(LocalDateTime time,
                                                         DayOfWeek dayOfWeek,
                                                         WeekType weekType,
                                                         MomentType momentType) {
        if (Objects.isNull(time)) {
            throw new RuntimeException("Time can not be null !");
        }
        return switch (momentType) {
            case FIRST -> getWeekDayWithSameTime(time, dayOfWeek, weekType).with(LocalTime.MIN);
            case LAST -> getWeekDayWithSameTime(time, dayOfWeek, weekType).with(LocalTime.MAX);
        };
    }

    /**
     * ISO 8601国际标准
     * 获取传入时间所在周的开始时刻 周一 00:00:00
     *
     * @param time 时间对象
     * @return LocalDateTime
     */
    public static LocalDateTime getFirstMomentOfWeek(LocalDateTime time) {
        return getWeekDayWithMomentType(time, DayOfWeek.MONDAY, WeekType.THIS_WEEK, MomentType.FIRST);
    }

    /**
     * ISO 8601国际标准
     * 获取传入时间所在周的结束时刻 周日 23:59:59
     *
     * @param time 时间对象
     * @return LocalDateTime
     */
    public static LocalDateTime getLastMomentOfWeek(LocalDateTime time) {
        return getWeekDayWithMomentType(time, DayOfWeek.SUNDAY, WeekType.THIS_WEEK, MomentType.LAST);
    }

    /**
     * 根据传入的时间获取所在月第一天 00:00:00
     *
     * @param time time
     * @return 所在月的第一时刻
     */
    public static LocalDateTime getFirstMomentOfMonth(LocalDateTime time) {
        return time
                .with(TemporalAdjusters.firstDayOfMonth())
                .with(LocalTime.MIN);
    }

    /**
     * 根据传入的LocalDateTime 转为对应当前月的最后一天
     *
     * @param time LocalDateTime
     * @return 当前月的最后一天
     */
    public static LocalDateTime getLastMomentOfMonth(LocalDateTime time) {
        return time
                .with(TemporalAdjusters.lastDayOfMonth())
                .with(LocalTime.MAX);
    }

    /**
     * 根据传入的时间获取所在月第一天 00:00:00
     *
     * @param time time
     * @return 所在年份的第一时刻
     */
    public static LocalDateTime getFirstMomentOfYear(LocalDateTime time) {
        return time
                .with(TemporalAdjusters.firstDayOfYear())
                .with(LocalTime.MIN);
    }

    /**
     * 根据传入的LocalDateTime 转为对应当前月的最后一天
     *
     * @param time LocalDateTime
     * @return 当前年份的最后一天
     */
    public static LocalDateTime getLastMomentOfYear(LocalDateTime time) {
        return time
                .with(TemporalAdjusters.lastDayOfYear())
                .with(LocalTime.MAX);
    }

    /**
     * 获取两个时间的差值（绝对值）
     *
     * @param startTime  startTime
     * @param endTime    endTime
     * @param chronoUnit chronoUnit
     * @return 时间差值(绝对值)
     */
    public static long getDuration(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit chronoUnit) {
        return Math.abs(startTime.until(endTime, chronoUnit));
    }

    /**
     * 获取两个时间的差值(绝对值),以纳秒的形式返回
     *
     * @param startTime startTime
     * @param endTime   endTime
     * @return 日期毫秒差值(绝对值)
     */
    public static long getDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return getDuration(startTime, endTime, ChronoUnit.NANOS);
    }

    /**
     * 计算从指定时间到现在的差值(绝对值),以毫秒的形式返回
     *
     * @param time time
     * @return 指定时间到现在的差值(绝对值)
     */
    public static long getDurationToNow(LocalDateTime time) {
        return getDuration(LocalDateTime.now(), time);
    }

    /**
     * 计算从指定时间到现在的差值(绝对值),以毫秒的形式返回
     *
     * @param time time
     * @return 指定时间到现在的差值(绝对值)
     */
    public static long getDurationToNow(LocalDateTime time, ChronoUnit chronoUnit) {
        return getDuration(LocalDateTime.now(), time, chronoUnit);
    }

    /**
     * 获取传入时间当天剩余的毫秒数（绝对值）
     *
     * @return 传入时间当天剩余的毫秒数（绝对值）
     */
    public static long getRemainingDurationOfDay(LocalDateTime time) {
        return getDuration(time, getLastMoment(time));
    }

    /**
     * 获取传入时间当天剩余的时间（绝对值）
     *
     * @return 传入时间当天剩余的时间（绝对值）
     */
    public static long getRemainingDurationOfDay(LocalDateTime time, ChronoUnit chronoUnit) {
        return getDuration(time, getLastMoment(time), chronoUnit);
    }

    /**
     * 获取传入时间所属周剩余的时间（绝对值）
     *
     * @return 传入时间所属周剩余的时间（绝对值）
     */
    public static long getRemainingDurationOfWeek(LocalDateTime time, ChronoUnit chronoUnit) {
        return getDuration(time, getLastMomentOfWeek(time), chronoUnit);
    }

    /**
     * 获取传入时间所属月剩余的时间（绝对值）
     *
     * @return 传入时间所属月剩余的时间（绝对值）
     */
    public static long getRemainingDurationOfMonth(LocalDateTime time, ChronoUnit chronoUnit) {
        return getDuration(time, getLastMomentOfMonth(time), chronoUnit);
    }

    /**
     * 获取传入时间所属月剩余的时间（绝对值）
     *
     * @return 传入时间所属月剩余的时间（绝对值）
     */
    public static long getRemainingDurationOfYear(LocalDateTime time, ChronoUnit chronoUnit) {
        return getDuration(time, getLastMomentOfYear(time), chronoUnit);
    }

    /**
     * LocalDateTime 转 毫秒
     *
     * @param time LocalDateTime
     * @return 对应的毫秒值
     */
    public static long toMilliseconds(LocalDateTime time) {
        return time
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    /**
     * 根据传入时间格式化到小时
     *
     * @param time 传入时间
     * @return 传入时间对应的小时开始时刻
     */
    public static LocalDateTime toLocalDateHour(LocalDateTime time) {
        return time
                .toLocalDate()
                .atTime(time.getHour(), 0, 0, 0);
    }

    /**
     * 根据传入时间格式化到小时  不包含日期
     *
     * @param time 传入时间
     * @return 传入时间对应的小时开始时刻
     */
    public static LocalTime toLocalTimeHour(LocalDateTime time) {
        return time.toLocalTime().withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * 获取更早的时间
     *
     * @param time        time
     * @param anotherTime anotherTime
     * @return 两个时间中更早的一个
     */
    public static LocalDateTime getEarlierTime(LocalDateTime time, LocalDateTime anotherTime) {
        if (Objects.isNull(time) || Objects.isNull(anotherTime)) {
            throw new IllegalArgumentException("time can't null");
        }
        return time.isAfter(anotherTime) ? anotherTime : time;
    }

    /**
     * 获取最早的时间
     *
     * @param times 时间数组
     * @return 最早的时间
     */
    public static LocalDateTime getEarliestTime(LocalDateTime... times) {
        if (ArrayUtils.isEmpty(times)) {
            throw new IllegalArgumentException("times can't null");
        }
        return Arrays.stream(times)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("Unable to read the valid time!"));
    }

    /**
     * 获取更晚的时间
     *
     * @param time        time
     * @param anotherTime anotherTime
     * @return 两个时间中更晚的一个
     */
    public static LocalDateTime getLaterTime(LocalDateTime time, LocalDateTime anotherTime) {
        if (Objects.isNull(time) || Objects.isNull(anotherTime)) {
            throw new IllegalArgumentException("time can't null");
        }
        return time.isAfter(anotherTime) ? time : anotherTime;
    }

    /**
     * 获取最晚的时间
     *
     * @param times times
     * @return 最晚的时间
     */
    public static LocalDateTime getLatestTime(LocalDateTime... times) {
        if (ArrayUtils.isEmpty(times)) {
            throw new IllegalArgumentException("times can't null");
        }
        return Arrays.stream(times)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("Unable to get the valid time!"));
    }

}
