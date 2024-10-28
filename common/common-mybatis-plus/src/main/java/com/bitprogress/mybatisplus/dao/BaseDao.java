package com.bitprogress.mybatisplus.dao;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.DeleteJoinWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.github.yulichang.wrapper.UpdateJoinWrapper;
import org.apache.ibatis.binding.MapperMethod;

import java.util.List;

/**
 * 集成mybatis-plus-join，增加连表能力
 */
public abstract class BaseDao<M extends MPJBaseMapper<T>, T> extends MPJBaseServiceImpl<M, T> {

    /**
     * 获取连表查询的wrapper
     */
    public MPJLambdaWrapper<T> lambdaJoinQuery() {
        return JoinWrappers.lambda(getEntityClass());
    }

    /**
     * 获取连表更新的wrapper
     */
    public UpdateJoinWrapper<T> lambdaJoinUpdate() {
        return JoinWrappers.update(getEntityClass());
    }

    /**
     * 获取连表删除的wrapper
     */
    public DeleteJoinWrapper<T> lambdaJoinDelete() {
        return JoinWrappers.delete(getEntityClass());
    }

    /**
     * 批量更新指定字段
     *
     * @param list        数据集合
     * @param setFunction 设置字段
     * @param eqFunction  条件字段
     */
    public boolean updateBatchColumn(List<T> list, SFunction<T, ?> setFunction, SFunction<T, ?> eqFunction) {
        return updateBatchColumn(list, 1000, setFunction, eqFunction);
    }

    /**
     * 批量更新指定字段
     *
     * @param list        数据集合
     * @param batchSize   批量大小
     * @param setFunction 设置字段
     * @param eqFunction  条件字段
     */
    public boolean updateBatchColumn(List<T> list,
                                     int batchSize,
                                     SFunction<T, ?> setFunction,
                                     SFunction<T, ?> eqFunction) {
        String sqlStatement = getSqlStatement(SqlMethod.UPDATE);
        return executeBatch(list, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, null);
            AbstractWrapper<T, SFunction<T, ?>, LambdaUpdateWrapper<T>> wrapper = lambdaUpdate()
                    .set(setFunction, setFunction.apply(entity))
                    .eq(eqFunction, eqFunction.apply(entity))
                    .getWrapper();
            param.put(Constants.WRAPPER, wrapper);
            sqlSession.update(sqlStatement, param);
        });
    }

    /**
     * 批量更新多个指定字段和多条件拼接
     *
     * @param list         数据集合
     * @param batchSize    批量大小
     * @param setFunctions 设置字段
     * @param eqFunctions  条件字段
     */
    public boolean updateBatchColumn(List<T> list,
                                     int batchSize,
                                     List<SFunction<T, ?>> setFunctions,
                                     List<SFunction<T, ?>> eqFunctions) {
        String sqlStatement = getSqlStatement(SqlMethod.UPDATE);
        return executeBatch(list, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, null);
            LambdaUpdateChainWrapper<T> updateWrapper = lambdaUpdate();
            setFunctions.forEach(setFunction -> updateWrapper.set(setFunction, setFunction.apply(entity)));
            eqFunctions.forEach(eqFunction -> updateWrapper.eq(eqFunction, eqFunction.apply(entity)));
            param.put(Constants.WRAPPER, updateWrapper.getWrapper());
            sqlSession.update(sqlStatement, param);
        });
    }

}
