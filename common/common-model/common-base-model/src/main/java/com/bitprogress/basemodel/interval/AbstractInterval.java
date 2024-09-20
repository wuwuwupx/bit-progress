package com.bitprogress.basemodel.interval;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serial;
import java.util.Objects;
import java.util.function.Function;

/**
 * 抽象区间
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
public abstract class AbstractInterval<T extends Number> extends Interval {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 左端点
     */
    private final LeftEndpoints<T> leftEndpoints;

    /**
     * 右端点
     */
    private final RightEndpoints<T> rightEndpoints;

    /**
     * 左端点值
     */
    public T getLeftEndpointsValue() {
        return leftEndpoints.getValue();
    }

    /**
     * 右端点值
     */
    public T getRightEndpointsValue() {
        return rightEndpoints.getValue();
    }

    /**
     * 是否开区间
     */
    public boolean isOpen() {
        return leftEndpoints.isOpen() && rightEndpoints.isOpen();
    }

    /**
     * 是否闭区间
     */
    public boolean isClose() {
        return leftEndpoints.isClose() && rightEndpoints.isClose();
    }

    /**
     * 是否半开区间
     */
    public boolean isSemiOpen() {
        return (leftEndpoints.isOpen() && rightEndpoints.isClose())
                || (leftEndpoints.isClose() && rightEndpoints.isOpen());
    }

    /**
     * 判断元素是否在区间内
     *
     * @return true：在区间内；false：不在区间内
     */
    public boolean isContains(T value) {
        if (Objects.isNull(value)) {
            return false;
        }
        // 比较左端点
        boolean isLeftContains = leftEndpoints.isContains(value);
        return isLeftContains && rightEndpoints.isContains(value);
    }

    /**
     * 判断端点是否在区间内
     *
     * @return true：在区间内；false：不在区间内
     */
    public boolean isContains(Endpoints<T> endpoints) {
        if (Objects.isNull(endpoints)) {
            return false;
        }
        // 比较左端点
        boolean isLeftContains = leftEndpoints.isContains(endpoints);
        return isLeftContains && rightEndpoints.isContains(endpoints);
    }

    /**
     * 判断传入区间是否包含在当前区间内
     *
     * @return true：在区间内；false：不在区间内
     */
    public boolean isContains(AbstractInterval<T> interval) {
        return Objects.nonNull(interval)
                && this.isContains(interval.getLeftEndpoints())
                && this.isContains(interval.getRightEndpoints());
    }

    /**
     * 判断当前区间是否被包含在传入区间内
     *
     * @return true：在区间内；false：不在区间内
     */
    public boolean isIncluded(AbstractInterval<T> interval) {
        return Objects.nonNull(interval)
                && Objects.nonNull(interval.getLeftEndpoints())
                && Objects.nonNull(interval.getRightEndpoints())
                && interval.getLeftEndpoints().isContains(this.getLeftEndpoints())
                && interval.getRightEndpoints().isContains(this.getRightEndpoints());
    }

    /**
     * 判断当前区间是否与传入区间相交
     *
     * @return true：相交；false：不相交
     */
    public boolean isIntersect(AbstractInterval<T> interval) {
        if (Objects.isNull(interval)
                || Objects.isNull(interval.getLeftEndpoints())
                || Objects.isNull(interval.getRightEndpoints())) {
            return false;
        }
        return this.getLeftEndpoints().isContains(interval.getLeftEndpoints())
                ? this.getRightEndpoints().isContains(interval.getLeftEndpoints())
                : interval.getRightEndpoints().isContains(this.getLeftEndpoints());
    }

    /**
     * 获取区间字符串
     * (x,y)、(x,y]、[x,y)、[x,y]
     *
     * @param toStringFunction 转换函数
     */
    public String getIntervalString(Function<T, String> toStringFunction) {
        return leftEndpoints.getBoundarySymbols()
                + toStringFunction.apply(leftEndpoints.getValue())
                + ","
                + toStringFunction.apply(rightEndpoints.getValue())
                + rightEndpoints.getBoundarySymbols();
    }

    /**
     * 获取区间字符串
     * (x,y)、(x,y]、[x,y)、[x,y]
     */
    public String getIntervalString() {
        return leftEndpoints.getBoundarySymbols()
                + leftEndpoints.printValue()
                + ","
                + rightEndpoints.printValue()
                + rightEndpoints.getBoundarySymbols();
    }

}
