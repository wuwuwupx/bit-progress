package com.bitprogress.basemodel.interval;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;

/**
 * 右端点
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
public abstract class RightEndpoints<T extends Number> extends Endpoints<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    public RightEndpoints(T value, IntervalBoundaryType boundaryType) {
        super(value, boundaryType);
    }

    /**
     * 获取端点类型
     */
    public EndpointsType getEndpointsType() {
        return EndpointsType.RIGHT;
    }

    /**
     * 是否包含value
     * 由于是端点的包含，左端点则判断是否小于等于value，右端点则判断是否大于等于value
     * 小于等于、大于等于 要根据端点的区间类型判断
     *
     * @param value 需要比较的值
     */
    @Override
    public boolean isValueContains(T value) {
        int compare = this.compareTo(value);
        return this.isOpen() ? compare > 0 : compare >= 0;
    }

    /**
     * 是否包含端点
     * 由于忽略端点的类型，所以直接判断value是否包含
     * 由于是端点的包含，左端点则判断是否小于等于value，右端点则判断是否大于等于value
     * 小于等于、大于等于 要根据端点的边界类型判断
     *
     * @param endpoints 需要匹配的端点
     */
    @Override
    public boolean isEndpointsContains(Endpoints<T> endpoints) {
        int compare = this.compareTo(endpoints.getValue());
        if (compare == 0) {
            if (EndpointsType.LEFT.equals(endpoints.getEndpointsType())) {
                return this.isClose() && endpoints.isClose();
            } else {
                return this.isClose() || endpoints.isOpen();
            }
        } else {
            return compare > 0;
        }
    }

    /**
     * 打印端点的值
     *
     * @return 展示的值
     */
    @Override
    public String printValue() {
        return this.isInfinite() ? "+∞" : this.getValue().toString();
    }

    /**
     * 获取区间的边界符号
     *
     * @return 区间的边界符号
     */
    @Override
    public String getBoundarySymbols() {
        return this.getBoundaryType().getRightSymbols();
    }

}
