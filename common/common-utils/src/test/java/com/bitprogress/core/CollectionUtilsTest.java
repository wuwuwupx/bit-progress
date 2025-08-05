package com.bitprogress.core;

import com.bitprogress.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtilsTest {

    public static void main(String[] args) {
        Collection<Integer> list = CollectionUtils.asSet(2, 1, 4, 7, 2,6,8,2);
        List<List<Integer>> lists = CollectionUtils.cutList(list, 3);
        lists.forEach(System.out::println);
    }

}
