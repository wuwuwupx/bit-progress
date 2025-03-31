package com.bitprogress.mybatispluscore.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bitprogress.basemodel.qo.OrderColumnItem;
import com.bitprogress.basemodel.qo.PageQO;
import com.bitprogress.basemodel.vo.PageVO;
import com.bitprogress.util.CollectionUtils;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PageUtils {

    /**
     * 获取mybatis-plus分页信息
     *
     * @param qo 分页查询信息
     * @return mybatis-plus分页对象
     */
    public static <T> Page<T> toPage(PageQO qo) {
        Page<T> page = new Page<>(qo.getPageIndex(), qo.getPageSize());
        List<OrderColumnItem> orders = qo.getOrders();
        if (CollectionUtils.isNotEmpty(orders)) {
            page.setOrders(CollectionUtils.toList(orders, item ->
                    new OrderItem().setColumn(item.getColumn()).setAsc(item.getAsc())));
        }
        return page;
    }

    /**
     * Bean转换
     *
     * @param sourcePage 源分页数据
     * @param function   转换方法
     * @return 转换后的分页数据
     */
    public static <T, R> PageVO<R> convertBean(IPage<T> sourcePage, Function<T, R> function) {
        List<T> resources = sourcePage.getRecords();
        long total = sourcePage.getTotal();
        long size = sourcePage.getSize();
        long current = sourcePage.getCurrent();
        return CollectionUtils.isEmpty(resources)
                ? PageVO.empty(total, size, current)
                : PageVO.of(CollectionUtils.toList(resources, function), total, size, current);
    }

    /**
     * List转换
     *
     * @param sourcePage 源分页数据
     * @param function   转换方法
     * @return 转换后的分页数据
     */
    public static <T, R> PageVO<R> convertList(IPage<T> sourcePage, Function<List<T>, List<R>> function) {
        List<T> sourceRecords = sourcePage.getRecords();
        long total = sourcePage.getTotal();
        long size = sourcePage.getSize();
        long current = sourcePage.getCurrent();
        return CollectionUtils.isEmpty(sourceRecords)
                ? PageVO.empty(total, size, current)
                : PageVO.of(function.apply(sourceRecords), total, size, current);
    }

    /**
     * Bean转换
     *
     * @param sourcePage 源分页数据
     * @param function   转换方法
     * @return 转换后的分页数据
     */
    public static <T, R> PageVO<R> convertBean(PageVO<T> sourcePage, Function<T, R> function) {
        List<T> resources = sourcePage.getRecords();
        long total = sourcePage.getTotal();
        long size = sourcePage.getSize();
        long current = sourcePage.getCurrent();
        return CollectionUtils.isEmpty(resources)
                ? PageVO.empty(total, size, current)
                : PageVO.of(CollectionUtils.toList(resources, function), total, size, current);
    }

    /**
     * List转换
     *
     * @param sourcePage 源分页数据
     * @param function   转换方法
     * @return 转换后的分页数据
     */
    public static <T, R> PageVO<R> convertList(PageVO<T> sourcePage, Function<List<T>, List<R>> function) {
        List<T> sourceRecords = sourcePage.getRecords();
        long total = sourcePage.getTotal();
        long size = sourcePage.getSize();
        long current = sourcePage.getCurrent();
        return CollectionUtils.isEmpty(sourceRecords)
                ? PageVO.empty(total, size, current)
                : PageVO.of(function.apply(sourceRecords), total, size, current);
    }

    /**
     * List转换
     *
     * @param sourcePage 源分页数据
     * @param function   转换方法
     * @return 转换后的分页数据
     */
    public static <T, R, U> PageVO<R> convertList(PageVO<T> sourcePage,
                                                  BiFunction<List<T>, U, List<R>> function,
                                                  U u) {
        List<T> sourceRecords = sourcePage.getRecords();
        long total = sourcePage.getTotal();
        long size = sourcePage.getSize();
        long current = sourcePage.getCurrent();
        return CollectionUtils.isEmpty(sourceRecords)
                ? PageVO.empty(total, size, current)
                : PageVO.of(function.apply(sourceRecords, u), total, size, current);
    }

}
