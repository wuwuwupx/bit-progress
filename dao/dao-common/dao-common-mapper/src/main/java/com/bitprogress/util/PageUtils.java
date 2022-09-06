package com.bitprogress.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wuwuwupx
 * Bean转换工具
 */
public class PageUtils {

    /**
     * IPage的records类型转换
     *
     * @param    page 传入的Ipage
     * @param    function <T, C> T传入类型，C返回类型
     * @return   IPage<C>
     */
    public static <T, C> IPage<C> conversionBean(IPage<T> page, Function<T, C> function) {
        List<C> collect = page.getRecords().stream().map(function).collect(Collectors.toList());
        IPage<C> resultPage = new Page();
        BeanUtils.copyProperties(page, resultPage);
        resultPage.setRecords(collect);
        return resultPage;
    }

    /**
     * list类型转换
     *
     * @param    list 传入的list
     * @param    function <T, C> T传入类型 C 返回类型
     * @return   List<C> 转换后的list
     */
    public static <T, C> List<C> conversionBean(List<T> list, Function<T, C> function) {
        return list.stream().map(function).collect(Collectors.toList());
    }

}
