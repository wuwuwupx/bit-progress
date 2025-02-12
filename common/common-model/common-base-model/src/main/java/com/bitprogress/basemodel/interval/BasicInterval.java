package com.bitprogress.basemodel.interval;

import com.bitprogress.basemodel.Interval;
import com.bitprogress.basemodel.endpoint.interval.IntervalEndpoint;
import com.bitprogress.basemodel.endpoint.interval.LeftIntervalEndpoint;
import com.bitprogress.basemodel.endpoint.interval.RightIntervalEndpoint;
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
public abstract class BasicInterval<T> extends Interval {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 左端点
     */
    private final LeftIntervalEndpoint<T> leftEndpoint;

    /**
     * 右端点
     */
    private final RightIntervalEndpoint<T> rightEndpoint;

    /**
     * 左端点值
     */
    public T getLeftEndpointValue() {
        return leftEndpoint.getValue();
    }

    /**
     * 右端点值
     */
    public T getRightEndpointValue() {
        return rightEndpoint.getValue();
    }

    /**
     * 是否开区间
     */
    public boolean isOpen() {
        return leftEndpoint.isOpen() && rightEndpoint.isOpen();
    }

    /**
     * 是否闭区间
     */
    public boolean isClose() {
        return leftEndpoint.isClose() && rightEndpoint.isClose();
    }

    /**
     * 是否半开区间
     */
    public boolean isSemiOpen() {
        return (leftEndpoint.isOpen() && rightEndpoint.isClose()) || (leftEndpoint.isClose() && rightEndpoint.isOpen());
    }

    /**
     * 判断元素是否在区间内
     *
     * @return true：在区间内；false：不在区间内
     */
    public boolean contains(T value) {
        if (Objects.isNull(value)) {
            return false;
        }
        // 比较左端点
        boolean isLeftContains = leftEndpoint.contains(value);
        return isLeftContains && rightEndpoint.contains(value);
    }

    /**
     * 判断端点是否在区间内
     *
     * @return true：在区间内；false：不在区间内
     */
    public boolean contains(IntervalEndpoint<T> intervalEndpoint) {
        if (Objects.isNull(intervalEndpoint)) {
            return false;
        }
        // 比较左端点
        boolean leftContains = leftEndpoint.contains(intervalEndpoint);
        return leftContains && rightEndpoint.contains(intervalEndpoint);
    }

    /**
     * 判断传入区间是否包含在当前区间内
     *
     * @return true：在区间内；false：不在区间内
     */
    public boolean contains(BasicInterval<T> interval) {
        return Objects.nonNull(interval)
                && this.contains(interval.getLeftEndpoint())
                && this.contains(interval.getRightEndpoint());
    }

    /**
     * 判断当前区间是否被包含在传入区间内
     *
     * @return true：在区间内；false：不在区间内
     */
    public boolean isContainedIn(BasicInterval<T> interval) {
        return Objects.nonNull(interval)
                && Objects.nonNull(interval.getLeftEndpoint())
                && Objects.nonNull(interval.getRightEndpoint())
                && interval.getLeftEndpoint().contains(this.getLeftEndpoint())
                && interval.getRightEndpoint().contains(this.getRightEndpoint());
    }

    /**
     * 判断当前区间是否与传入区间相交
     *
     * @return true：相交；false：不相交
     */
    public boolean isIntersect(BasicInterval<T> interval) {
        if (Objects.isNull(interval)
                || Objects.isNull(interval.getLeftEndpoint())
                || Objects.isNull(interval.getRightEndpoint())) {
            return false;
        }
        return this.getLeftEndpoint().contains(interval.getLeftEndpoint())
                ? this.getRightEndpoint().contains(interval.getLeftEndpoint())
                : interval.getRightEndpoint().contains(this.getLeftEndpoint());
    }

    /**
     * 获取区间字符串
     * (x,y)、(x,y]、[x,y)、[x,y]
     *
     * @param toStringFunction 转换函数
     */
    public String getIntervalString(Function<T, String> toStringFunction) {
        return leftEndpoint.getBoundarySymbols()
                + toStringFunction.apply(leftEndpoint.getValue())
                + ","
                + toStringFunction.apply(rightEndpoint.getValue())
                + rightEndpoint.getBoundarySymbols();
    }

    /**
     * 获取区间字符串
     * (x,y)、(x,y]、[x,y)、[x,y]
     */
    public String getIntervalString() {
        return leftEndpoint.getBoundarySymbols()
                + leftEndpoint.printValue()
                + ","
                + rightEndpoint.printValue()
                + rightEndpoint.getBoundarySymbols();
    }

}
