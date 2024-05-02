package com.bitprogress.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bitprogress.qo.PageQO;
import com.bitprogress.vo.PageVO;

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
        return new Page<>(qo.getPageIndex(), qo.getPageSize());
    }

    /**
     * Bean转换
     *
     * @param sourcePage 源分页数据
     * @param function   转换方法
     * @return 转换后的分页数据
     */
    public static <T, R> PageVO<R> conversionBean(IPage<T> sourcePage, Function<T, R> function) {
        List<T> resources = sourcePage.getRecords();
        return CollectionUtils.isEmpty(resources)
                ? PageVO.empty()
                : PageVO.of(CollectionUtils.toList(resources, function), sourcePage.getTotal());
    }

    /**
     * List转换
     *
     * @param sourcePage 源分页数据
     * @param function   转换方法
     * @return 转换后的分页数据
     */
    public static <T, R> PageVO<R> conversionList(IPage<T> sourcePage, Function<List<T>, List<R>> function) {
        List<T> sourceRecords = sourcePage.getRecords();
        return CollectionUtils.isEmpty(sourceRecords)
                ? PageVO.empty()
                : PageVO.of(function.apply(sourceRecords), sourcePage.getTotal());
    }

    /**
     * Bean转换
     *
     * @param sourcePage 源分页数据
     * @param function   转换方法
     * @return 转换后的分页数据
     */
    public static <T, R> PageVO<R> conversionBean(PageVO<T> sourcePage, Function<T, R> function) {
        List<T> resources = sourcePage.getRecords();
        return CollectionUtils.isEmpty(resources)
                ? PageVO.empty()
                : PageVO.of(CollectionUtils.toList(resources, function), sourcePage.getTotal());
    }

    /**
     * List转换
     *
     * @param sourcePage 源分页数据
     * @param function   转换方法
     * @return 转换后的分页数据
     */
    public static <T, R> PageVO<R> conversionList(PageVO<T> sourcePage, Function<List<T>, List<R>> function) {
        List<T> sourceRecords = sourcePage.getRecords();
        return CollectionUtils.isEmpty(sourceRecords)
                ? PageVO.empty()
                : PageVO.of(function.apply(sourceRecords), sourcePage.getTotal());
    }

    /**
     * List转换
     *
     * @param sourcePage 源分页数据
     * @param function   转换方法
     * @return 转换后的分页数据
     */
    public static <T, R, U> PageVO<R> conversionList(PageVO<T> sourcePage,
                                                     BiFunction<List<T>, U, List<R>> function,
                                                     U u) {
        List<T> sourceRecords = sourcePage.getRecords();
        return CollectionUtils.isEmpty(sourceRecords)
                ? PageVO.empty()
                : PageVO.of(function.apply(sourceRecords, u), sourcePage.getTotal());
    }

}
