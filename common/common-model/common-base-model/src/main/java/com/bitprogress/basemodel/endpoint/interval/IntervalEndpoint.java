package com.bitprogress.basemodel.endpoint.interval;

import com.bitprogress.basemodel.endpoint.Endpoint;
import com.bitprogress.basemodel.endpoint.interval.enums.BoundaryType;
import com.bitprogress.basemodel.endpoint.interval.enums.EndpointType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.util.Objects;

/**
 * Interval Endpoint
 * 区间端点
 * - 端点类型
 * - 边界属性：开闭
 * - 值属性：是否无穷
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
public abstract class IntervalEndpoint<T> extends Endpoint<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * interval boundary type
     */
    private final BoundaryType boundaryType;

    /**
     * Is it an infinite number
     */
    private final Boolean isInfinite;

    public IntervalEndpoint(T value, BoundaryType boundaryType) {
        super(value);
        this.boundaryType = boundaryType;
        this.isInfinite = null == value;
    }

    /**
     * 获取端点类型
     */
    public abstract EndpointType getEndpointType();

    /**
     * 是否开区间
     */
    public boolean isOpen() {
        return BoundaryType.OPEN.equals(this.getBoundaryType());
    }

    /**
     * 是否闭区间
     */
    public boolean isClose() {
        return BoundaryType.CLOSE.equals(this.getBoundaryType());
    }

    /**
     * 是否是无穷数
     */
    public boolean isInfinite() {
        return this.getIsInfinite();
    }

    /**
     * 判断值包含
     * 当前端点为无穷且为闭区间时，才无需判断进行下一步值判断
     * 由于是端点的包含，左端点则判断是否小于等于value，右端点则判断是否大于等于value
     * 小于等于、大于等于 要根据端点的区间类型判断
     *
     * @param value 需要匹配的值
     */
    public boolean contains(T value) {
        return (this.isInfinite() && this.isClose()) || this.valueContains(value);
    }

    /**
     * 是否包含value
     * 值包含无法判断无穷端点，需要走特殊接口
     * 小于等于、大于等于 要根据端点的区间类型判断
     *
     * @param value 需要比较的值
     */
    public abstract boolean valueContains(T value);

    /**
     * 是否包含端点
     * - 端点类型相同的情况下
     *   - 传入端点为无穷时
     *     - 当前端点为无穷，且当前端点为闭区间或传入端点为开区间时，返回包含
     *   - 传入端点不为无穷时
     *     - 当前端点为无穷时，返回包含
     *     - 当前端点不为无穷时，进行下一步判断
     * - 端点类型不同的情况下
     *   - 当前端点为无穷或传入端点为无穷，返回包含
     *   - 当前端点不为无穷且传入端点不为无穷时，进行下一步判断
     * 由于是端点的包含，左端点则判断是否小于等于 传入端点，右端点则判断是否大于等于 传入端点
     * 不需要考虑端点的类型
     * 小于等于、大于等于 要根据端点的边界类型判断
     *
     * @param endpoint 需要匹配的端点
     */
    public boolean contains(IntervalEndpoint<T> endpoint) {
        if (Objects.equals(this.getEndpointType(), endpoint.getEndpointType())) {
            return endpoint.isInfinite()
                    ? this.isInfinite() && (this.isClose() || endpoint.isOpen())
                    : this.isInfinite() || this.endpointContains(endpoint);
        } else {
            return this.isInfinite() || endpoint.isInfinite() || this.endpointContains(endpoint);
        }
    }

    /**
     * 是否包含端点
     * 由于是端点的包含，左端点则判断是否小于等于value，右端点则判断是否大于等于value
     * 小于等于、大于等于 要根据端点的边界类型判断
     *
     * @param endpoint 需要匹配的端点
     */
    public abstract boolean endpointContains(IntervalEndpoint<T> endpoint);

    /**
     * 是否包含正无穷端点
     */
    public boolean containsPositiveInfinity() {
        return EndpointType.LEFT.equals(this.getEndpointType()) || (isClose() && isInfinite());
    }

    /**
     * 是否包含负无穷端点
     */
    public boolean containsNegativeInfinity() {
        return EndpointType.RIGHT.equals(this.getEndpointType()) || (isClose() && isInfinite());
    }

    /**
     * 获取端点的边界符号
     *
     * @return 端点的边界符号
     */
    public abstract String getBoundarySymbols();

}
