package com.bitprogress.basemodel.enums.comparable;

import com.bitprogress.basemodel.enums.ComparableEnum;

import java.util.Comparator;
import java.util.function.Function;

public enum OrderType implements ComparableEnum {

    /**
     * 升序排序
     */
    ASC {
        /**
         * 获取排序方法
         *
         * @return 升序排序方法
         */
        @Override
        public <T extends Comparable<T>> Comparator<T> comparator() {
            return Comparator.naturalOrder();
        }

        /**
         * 根据 comparable函数获取排序方法
         *
         * @param comparableFunction 获取比较元素函数
         * @return 对应的排序方法
         */
        @Override
        public <T, R extends Comparable<R>> Comparator<T> comparator(Function<T, R> comparableFunction) {
            return Comparator.comparing(comparableFunction);
        }
    },

    /**
     * 降序排序
     */
    DESC {
        /**
         * 获取排序方法
         *
         * @return 降序排序方法
         */
        @Override
        public <T extends Comparable<T>> Comparator<T> comparator() {
            return Comparator.reverseOrder();
        }

        /**
         * 根据 comparable函数获取排序方法
         *
         * @param comparableFunction 获取比较元素函数
         * @return 对应的排序方法
         */
        @Override
        public <T, R extends Comparable<R>> Comparator<T> comparator(Function<T, R> comparableFunction) {
            return Comparator.comparing(comparableFunction, Comparator.reverseOrder());
        }
    },

    ;

    /**
     * 获取排序方法
     *
     * @return 对应的排序方法
     */
    public abstract <T extends Comparable<T>> Comparator<T> comparator();

    /**
     * 根据 comparable函数获取排序方法
     *
     * @param comparableFunction 获取比较元素函数
     * @return 对应的排序方法
     */
    public abstract <T, R extends Comparable<R>> Comparator<T> comparator(Function<T, R> comparableFunction);

}
