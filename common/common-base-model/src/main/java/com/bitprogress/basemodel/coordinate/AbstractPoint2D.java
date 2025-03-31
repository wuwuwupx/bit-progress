package com.bitprogress.basemodel.coordinate;

import com.bitprogress.basemodel.Point;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serial;
import java.util.function.Function;

/**
 * 抽象坐标点
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
public abstract class AbstractPoint2D<T extends Number> extends Point {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 横坐标
     */
    private final Abscissa<T> abscissa;

    /**
     * 纵坐标
     */
    private final Ordinate<T> ordinate;

    /**
     * 获取横坐标索引
     */
    public T getAbscissaIndex() {
        return abscissa.getIndex();
    }

    /**
     * 获取纵坐标索引
     */
    public T getOrdinateIndex() {
        return ordinate.getIndex();
    }

    /**
     * 获取坐标字符串
     * (x,y)
     */
    public String getCoordinateString(Function<T, String> toStringFunction) {
        return "("
                + toStringFunction.apply(abscissa.getIndex())
                + ","
                + toStringFunction.apply(ordinate.getIndex())
                + ")";
    }

    /**
     * 获取坐标字符串
     * (x,y)
     */
    public String getCoordinateString() {
        return "("
                + abscissa.printValue()
                + ","
                + ordinate.printValue()
                + ")";
    }

}
