package com.bitprogress.util;

import com.bitprogress.basemodel.interval.IntervalBoundaryType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;

public class BigDecimalUtils {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    /**
     * 比较两个数是否相同
     */
    public static boolean isSame(BigDecimal x, BigDecimal y) {
        if (Objects.isNull(x)) {
            return Objects.isNull(y);
        }
        return Objects.nonNull(y) && x.compareTo(y) == 0;
    }

    /**
     * 目标值是否在区间内，默认闭区间
     *
     * @param source 源数据
     * @param pointX 区间端点值
     * @param pointY 区间端点值
     * @return true：目标值在区间内，false：目标值不在区间内（包括目标值为空和区间不完整的情况）
     */
    public static boolean isBetween(BigDecimal source, BigDecimal pointX, BigDecimal pointY) {
        return isBetweenClose(source, pointX, pointY);
    }

    /**
     * 目标值是否在区间内, 闭区间
     *
     * @param source 源数据
     * @param pointX 区间端点值
     * @param pointY 区间端点值
     * @return true：目标值在区间内，false：目标值不在区间内（包括目标值为空和区间不完整的情况）
     */
    public static boolean isBetweenClose(BigDecimal source, BigDecimal pointX, BigDecimal pointY) {
        return isBetween(source, pointX, pointY, IntervalBoundaryType.CLOSE, IntervalBoundaryType.CLOSE);
    }

    /**
     * 目标值是否在区间内, 开区间
     *
     * @param source 源数据
     * @param pointX 区间端点值
     * @param pointY 区间端点值
     * @return true：目标值在区间内，false：目标值不在区间内（包括目标值为空和区间不完整的情况）
     */
    public static boolean isBetweenOpen(BigDecimal source, BigDecimal pointX, BigDecimal pointY) {
        return isBetween(source, pointX, pointY, IntervalBoundaryType.OPEN, IntervalBoundaryType.OPEN);
    }

    /**
     * 目标值是否在区间内, 左开右闭
     *
     * @param source 源数据
     * @param pointX 区间端点值
     * @param pointY 区间端点值
     * @return true：目标值在区间内，false：目标值不在区间内（包括目标值为空和区间不完整的情况）
     */
    public static boolean isBetweenLORC(BigDecimal source, BigDecimal pointX, BigDecimal pointY) {
        return isBetween(source, pointX, pointY, IntervalBoundaryType.OPEN, IntervalBoundaryType.CLOSE);
    }

    /**
     * 目标值是否在区间内, 左闭右开
     *
     * @param source 源数据
     * @param pointX 区间端点值
     * @param pointY 区间端点值
     * @return true：目标值在区间内，false：目标值不在区间内（包括目标值为空和区间不完整的情况）
     */
    public static boolean isBetweenLCRO(BigDecimal source, BigDecimal pointX, BigDecimal pointY) {
        return isBetween(source, pointX, pointY, IntervalBoundaryType.CLOSE, IntervalBoundaryType.OPEN);
    }

    /**
     * 目标值是否在区间内
     *
     * @param source 源数据
     * @param pointX 区间端点值
     * @param pointY 区间端点值
     * @param left   左界类型
     * @param right  右界类型
     * @return true：目标值在区间内，false：目标值不在区间内（包括目标值为空和区间不完整的情况）
     */
    public static boolean isBetween(BigDecimal source,
                                    BigDecimal pointX,
                                    BigDecimal pointY,
                                    IntervalBoundaryType left,
                                    IntervalBoundaryType right) {
        if (Objects.isNull(source) || Objects.isNull(pointX) || Objects.isNull(pointY)) {
            return false;
        }
        // 比较端点值设置区间的最小值和最大值
        boolean minX = pointX.compareTo(pointY) < 0;
        BigDecimal min = minX ? pointX : pointY;
        BigDecimal max = minX ? pointY : pointX;
        int minCompare = source.compareTo(min);
        boolean leftResult = IntervalBoundaryType.OPEN.equals(left) ? minCompare > 0 : minCompare >= 0;
        if (!leftResult) {
            return false;
        }
        int maxCompare = source.compareTo(max);
        return IntervalBoundaryType.OPEN.equals(right) ? maxCompare < 0 : maxCompare <= 0;
    }

    /**
     * 获取最小的值
     *
     * @param x 值x
     * @param y 值y
     * @return 最小的值
     */
    public static BigDecimal min(BigDecimal x, BigDecimal y) {
        if (Objects.isNull(x) || Objects.isNull(y)) {
            return null;
        }
        return x.compareTo(y) > 0 ? y : x;
    }

    /**
     * 获取最大的值
     *
     * @param x 值x
     * @param y 值y
     * @return 最大的值
     */
    public static BigDecimal max(BigDecimal x, BigDecimal y) {
        if (Objects.isNull(x) || Objects.isNull(y)) {
            return null;
        }
        return x.compareTo(y) > 0 ? x : y;
    }

    /**
     * 求和
     *
     * @param params 求和参数
     * @return 计算结果
     */
    public static BigDecimal addAll(BigDecimal... params) {
        return CollectionUtils.sumBigDecimal(params);
    }

    /**
     * 求和
     *
     * @param params 求和参数
     * @return 计算结果
     */
    public static BigDecimal addAll(String... params) {
        return CollectionUtils.sumBigDecimal(BigDecimal::new, params);
    }

    /**
     * 比例计算
     * 数值为空、除零异常 则返回0
     *
     * @param numerator   分子
     * @param denominator 分母
     * @return 计算结果
     */
    public static BigDecimal ratioCalculate(BigDecimal numerator, BigDecimal denominator) {
        if (Objects.isNull(numerator) || Objects.isNull(denominator) || isSame(denominator, BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        return numerator.divide(denominator);
    }

    /**
     * 比例计算
     * 数值为空、除零异常 则返回0
     *
     * @param numerator   分子
     * @param denominator 分母
     * @return 计算结果
     */
    public static BigDecimal ratioCalculate(Number numerator,
                                            Number denominator,
                                            int scale,
                                            RoundingMode roundingMode) {
        if (Objects.isNull(numerator) || Objects.isNull(denominator)) {
            return BigDecimal.ZERO;
        }
        BigDecimal divisor = new BigDecimal(String.valueOf(denominator));
        if (isSame(divisor, BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(String.valueOf(numerator)).divide(divisor, scale, roundingMode);
    }

    /**
     * 比例计算
     * 数值为空、除零异常 则返回0
     *
     * @param numerator   分子
     * @param denominator 分母
     * @return 计算结果
     */
    public static BigDecimal ratioCalculate(BigDecimal numerator,
                                            BigDecimal denominator,
                                            int scale,
                                            RoundingMode roundingMode) {
        if (Objects.isNull(numerator) || Objects.isNull(denominator) || isSame(denominator, BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        return numerator.divide(denominator, scale, roundingMode);
    }

}
