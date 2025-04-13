package com.bitprogress.util;

/**
 * 数组工具类
 */
public class ArrayUtils {

    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * An empty immutable {@code char} array.
     */
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];

    /**
     * 数组为空
     *
     * @param array 传入数组
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组为空
     *
     * @param array 传入数组
     */
    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组为空
     *
     * @param array 传入数组
     */
    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组为空
     *
     * @param array 传入数组
     */
    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组不为空
     *
     * @param array 传入数组
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组不为空
     *
     * @param array 传入数组
     */
    public static boolean isNotEmpty(byte[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组不为空
     *
     * @param array 传入数组
     */
    public static boolean isNotEmpty(long[] array) {
        return !isEmpty(array);
    }

    /**
     * 检查数组是否包含某个元素
     *
     * @param array        需要检查的数组
     * @param objectToFind 需要检查的元素
     * @return true：包含，false：不包含
     */
    public static boolean contains(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind) != -1;
    }

    /**
     * 获取数组中元素的索引
     * 不存在则返回 -1
     *
     * @param array        需要检查的数组
     * @param objectToFind 需要检查的元素
     * @return 元素在数组中的索引
     */
    public static int indexOf(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    /**
     * 获取数组中元素的索引
     * 不存在则返回 -1
     *
     * @param array        需要检查的数组
     * @param objectToFind 需要检查的元素
     * @param startIndex   检查数组的开始索引
     * @return 元素在数组中的索引
     */
    public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array != null) {
            if (startIndex < 0) {
                startIndex = 0;
            }

            int i;
            if (objectToFind == null) {
                for (i = startIndex; i < array.length; ++i) {
                    if (array[i] == null) {
                        return i;
                    }
                }
            } else {
                for (i = startIndex; i < array.length; ++i) {
                    if (objectToFind.equals(array[i])) {
                        return i;
                    }
                }
            }

        }
        return -1;
    }

}
