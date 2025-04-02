package com.bitprogress.basemodel.coordinate.enums;

import com.bitprogress.basemodel.enums.MessageEnum;
import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * coordinate axis type
 */
@Getter
@AllArgsConstructor
public enum AxisType implements CoordinateEnum, ValueEnum, MessageEnum {

    /**
     * horizontal axis
     * 水平轴，横坐标，Abscissa
     */
    HORIZONTAL(0, "水平轴"),

    /**
     * vertical axis
     * 垂直轴，纵坐标，Ordinate
     */
    VERTICAL(1, "垂直轴"),

    /**
     * depth axis
     * 深度轴，深度坐标
     */
    DEPTH(2, "深度轴"),

    ;

    private final Integer value;

    private final String message;

}
