package com.bitprogress.basemodel.enums.comparable;

import com.bitprogress.basemodel.enums.ComparableEnum;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;

/**
 * ComparableType，Get the larger or smaller of two values
 */
public enum ComparableType implements ComparableEnum {

    /**
     * key重复时取最小
     */
    MIN {
        /**
         * 获取收集器
         *
         * @param function 获取收集器的函数
         * @return 获取最小值的收集器
         */
        @Override
        public <T, R extends Comparable<R>> Collector<T, ?, Optional<T>> collector(Function<T, R> function) {
            return minBy(Comparator.comparing(function));
        }

        /**
         * 获取二元运算函数
         *
         * @return 获取最小值的二元运算函数
         */
        @Override
        public <T extends Comparable<T>> BinaryOperator<T> binaryOperator() {
            return BinaryOperator.minBy(Comparator.naturalOrder());
        }

        /**
         * 获取二元运算函数
         *
         * @param function 获取二元运算函数的函数
         * @return 获取最小值的二元运算函数
         */
        @Override
        public <T, R extends Comparable<R>> BinaryOperator<T> binaryOperator(Function<T, R> function) {
            return BinaryOperator.minBy(Comparator.comparing(function));
        }

    },

    /**
     * key重复时取最大
     */
    MAX {
        /**
         * 获取收集器
         *
         * @param function 获取收集器的函数
         * @return 获取最大值的收集器
         */
        @Override
        public <T, R extends Comparable<R>> Collector<T, ?, Optional<T>> collector(Function<T, R> function) {
            return maxBy(Comparator.comparing(function));
        }

        /**
         * 获取二元运算函数
         *
         * @return 获取最大值的二元运算函数
         */
        @Override
        public <T extends Comparable<T>> BinaryOperator<T> binaryOperator() {
            return BinaryOperator.maxBy(Comparator.naturalOrder());
        }

        /**
         * 获取二元运算函数
         *
         * @param function 获取二元运算函数的函数
         * @return 获取最大值的二元运算函数
         */
        @Override
        public <T, R extends Comparable<R>> BinaryOperator<T> binaryOperator(Function<T, R> function) {
            return BinaryOperator.maxBy(Comparator.comparing(function));
        }
    },

    ;

    /**
     * 获取收集器
     *
     * @param function 获取收集器的函数
     * @return 对应类型的收集器
     */
    public abstract <T, R extends Comparable<R>> Collector<T, ?, Optional<T>> collector(Function<T, R> function);

    /**
     * 获取二元运算函数
     *
     * @return 对应类型的二元运算函数
     */
    public abstract <T extends Comparable<T>> BinaryOperator<T> binaryOperator();

    /**
     * 获取二元运算函数
     *
     * @return 对应类型的二元运算函数
     */
    public abstract <T, R extends Comparable<R>> BinaryOperator<T> binaryOperator(Function<T, R> function);

}
