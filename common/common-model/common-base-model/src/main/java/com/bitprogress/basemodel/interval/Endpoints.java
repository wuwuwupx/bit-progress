package com.bitprogress.basemodel.interval;

import com.bitprogress.basemodel.Point;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.util.Objects;

/**
 * the endpoints of the interval
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
@ToString
public abstract class Endpoints<T extends Number> extends Point {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * interval point value
     * if value == null, then the endpoints = ∞
     */
    private final T value;

    /**
     * interval boundary type
     */
    private final IntervalBoundaryType boundaryType;

    /**
     * 是否开区间
     */
    public boolean isOpen() {
        return IntervalBoundaryType.OPEN.equals(this.getBoundaryType());
    }

    /**
     * 是否闭区间
     */
    public boolean isClose() {
        return IntervalBoundaryType.CLOSE.equals(this.getBoundaryType());
    }

    /**
     * 是否是无穷数
     */
    public boolean isInfinite() {
        return Objects.isNull(this.getValue());
    }

    /**
     * 获取端点的类型
     */
    public abstract EndpointsType getEndpointsType();

    /**
     * 是否包含value
     * 由于是端点的包含，左端点则判断是否小于等于value，右端点则判断是否大于等于value
     * 小于等于、大于等于 要根据端点的区间类型判断
     *
     * @param value 需要匹配的值
     */
    public boolean isContains(T value) {
        return this.isInfinite() || this.isValueContains(value);
    }

    /**
     * 是否包含value
     * 由于是端点的包含，左端点则判断是否小于等于value，右端点则判断是否大于等于value
     * 小于等于、大于等于 要根据端点的边界类型判断
     *
     * @param value 需要比较的值
     */
    public abstract boolean isValueContains(T value);

    /**
     * 是否包含端点
     * 由于是端点的包含，左端点则判断是否小于等于 传入端点，右端点则判断是否大于等于 传入端点
     * 不需要考虑端点的类型
     * 小于等于、大于等于 要根据端点的边界类型判断
     *
     * @param endpoints 需要匹配的端点
     */
    public boolean isContains(Endpoints<T> endpoints) {
        if (Objects.equals(this.getEndpointsType(), endpoints.getEndpointsType())) {
            return endpoints.isInfinite()
                    ? this.isInfinite() ? this.isClose() : this.isEndpointsContains(endpoints)
                    : this.isInfinite() || this.isEndpointsContains(endpoints);
        } else {
            return this.isInfinite() || endpoints.isInfinite() || this.isEndpointsContains(endpoints);
        }
    }

    /**
     * 是否包含端点
     * 由于是端点的包含，左端点则判断是否小于等于value，右端点则判断是否大于等于value
     * 小于等于、大于等于 要根据端点的边界类型判断
     *
     * @param endpoints 需要匹配的端点
     */
    public abstract boolean isEndpointsContains(Endpoints<T> endpoints);

    /**
     * 比较端点的值和传入的value
     *
     * @param value 传入的值
     * @return 0：相等， < 0：小于value， >0 ：大于value
     */
    public abstract int compareTo(T value);

    /**
     * 打印端点的值
     *
     * @return 展示的值
     */
    public abstract String printValue();

    /**
     * 获取区间的边界符号
     *
     * @return 区间的边界符号
     */
    public abstract String getBoundarySymbols();

}
