package com.bitprogress.mybatispluscore.handler;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.bitprogress.mybatispluscore.properties.DataScopeProperties;
import com.bitprogress.ormcontext.info.SingleTypeDataScopeInfo;
import com.bitprogress.ormcontext.service.impl.SingleTypeDataScopeContextService;
import com.bitprogress.ormcontext.utils.DataScopeContextUtils;
import com.bitprogress.ormmodel.enums.DataScopeType;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 单一类型的数据范围处理器
 */
@AllArgsConstructor
public class SingleTypeDataScopeHandler implements DataScopeHandler {

    private final DataScopeProperties dataScopeProperties;
    private final SingleTypeDataScopeContextService dataScopeContextService;

    /**
     * 是否启用
     *
     * @return 是否启用
     */
    @Override
    public boolean isEnabled() {
        return dataScopeProperties.getEnabled();
    }

    /**
     * 获取数据范围字段
     *
     * @return 数据范围字段
     */
    @Override
    public String getDataScopeColumn(String tableName, DataScopeType dataScopeType) {
        if (DataScopeType.SELF.equals(dataScopeType)) {
            return getTableSelfWhereColumn(tableName);
        }
        return getTableDataScopeColumn(tableName);
    }

    /**
     * 获取数据范围字段
     *
     * @return 数据范围字段
     */
    @Override
    public String getTableDataScopeColumn(String tableName) {
        return CollectionUtils.getForMap(dataScopeProperties.getDataScopeColumn(), tableName, getSourceDataScopeColumn());
    }

    /**
     * 获取自身匹配数据条件字段
     *
     * @return 自身匹配数据条件字段
     */
    @Override
    public String getTableSelfWhereColumn(String tableName) {
        return CollectionUtils.getForMap(dataScopeProperties.getSelfWhereColumn(), tableName, getSourceSelfWhereColumn());
    }

    /**
     * 获取数据范围
     *
     * @return 数据范围
     */
    @Override
    public Expression getCurrentDataScope() {
        return new StringValue(DataScopeContextUtils.getDataScope());
    }

    /**
     * 获取数据范围条件
     *
     * @return 数据范围条件
     */
    @Override
    public Expression getDataScopeCondition(Table table) {
        DataScopeType dataScopeType = dataScopeContextService.getCurrentDataScopeType();
        SingleTypeDataScopeInfo dataScopeInfo = dataScopeContextService.getDataScopeInfo();
        // 开启实际却没有信息
        if (Objects.isNull(dataScopeInfo)) {
            return new NullValue();
        }
        Column aliasDataScopeColumn = getAliasDataScopeColumn(table, dataScopeType);
        switch (dataScopeType) {
            case SELF -> {
                return new EqualsTo(aliasDataScopeColumn, new LongValue(dataScopeInfo.getUserId()));
            }
            case BELONG_LEVEL_CURRENT -> {
                return new EqualsTo(aliasDataScopeColumn, new StringValue(dataScopeInfo.getDataScope()));
            }
            case BELONG_LEVEL -> {
                StringValue value = new StringValue(dataScopeInfo.getDataScope() + StringPool.PERCENT);
                LikeExpression likeExpression = new LikeExpression();
                likeExpression.setLeftExpression(aliasDataScopeColumn);
                likeExpression.setRightExpression(value);
                return likeExpression;
            }
            case MANAGED_LEVEL_CURRENT -> {
                Set<String> dataScopes = dataScopeInfo.getManagedDataScopes();
                if (CollectionUtils.isEmpty(dataScopes)) {
                    return new NullValue();
                }
                List<StringValue> values = CollectionUtils.toList(dataScopes, StringValue::new);
                ParenthesedExpressionList<StringValue> value = new ParenthesedExpressionList<>(values);
                return new InExpression(aliasDataScopeColumn, value);
            }
            case MANAGED_LEVEL -> {
                Set<String> dataScopes = dataScopeInfo.getManagedDataScopes();
                if (CollectionUtils.isEmpty(dataScopes)) {
                    return new NullValue();
                }
                if (CollectionUtils.isSingle(dataScopes)) {
                    return buildLikeExpression(aliasDataScopeColumn, dataScopes.iterator().next());
                }
                int index = 0;
                OrExpression orExpression = new OrExpression();
                for (String dataScope : dataScopes) {
                    LikeExpression likeExpression = buildLikeExpression(aliasDataScopeColumn, dataScope);
                    if (index == 0) {
                        orExpression.setLeftExpression(likeExpression);
                    } else if (index == 1) {
                        orExpression.setRightExpression(likeExpression);
                    } else {
                        OrExpression newOrExpression = new OrExpression();
                        newOrExpression.setLeftExpression(orExpression);
                        newOrExpression.setRightExpression(likeExpression);
                    }
                    index++;
                }
                return new ParenthesedExpressionList<>(orExpression);
            }
            case COMPOSITE_LEVEL_CURRENT -> {
                Set<String> dataScopes = CollectionUtils
                        .newSet(DataScopeContextUtils.getDataScopes(), DataScopeContextUtils.getDataScope());
                if (CollectionUtils.isEmpty(dataScopes)) {
                    return new NullValue();
                }
                List<StringValue> values = CollectionUtils.toList(dataScopes, StringValue::new);
                ParenthesedExpressionList<StringValue> value = new ParenthesedExpressionList<>(values);
                return new InExpression(aliasDataScopeColumn, value);
            }
            case COMPOSITE_LEVEL -> {
                Set<String> dataScopes = CollectionUtils
                        .add(DataScopeContextUtils.getDataScopes(), DataScopeContextUtils.getDataScope());
                if (CollectionUtils.isEmpty(dataScopes)) {
                    return new NullValue();
                }
                if (CollectionUtils.isSingle(dataScopes)) {
                    return buildLikeExpression(aliasDataScopeColumn, dataScopes.iterator().next());
                }
                int index = 0;
                OrExpression orExpression = new OrExpression();
                for (String dataScope : dataScopes) {
                    LikeExpression likeExpression = buildLikeExpression(aliasDataScopeColumn, dataScope);
                    if (index == 0) {
                        orExpression.setLeftExpression(likeExpression);
                    } else if (index == 1) {
                        orExpression.setRightExpression(likeExpression);
                    } else {
                        OrExpression newOrExpression = new OrExpression();
                        newOrExpression.setLeftExpression(orExpression);
                        newOrExpression.setRightExpression(likeExpression);
                    }
                    index++;
                }
                return new ParenthesedExpressionList<>(orExpression);
            }
            case ALL -> {
                return new AllValue();
            }

        }
        return new NullValue();
    }

    /**
     * 构建like表达式
     *
     * @param aliasDataScopeColumn 别名数据范围字段
     * @param dataScope            数据范围
     * @return like表达式
     */
    private LikeExpression buildLikeExpression(Column aliasDataScopeColumn, String dataScope) {
        LikeExpression likeExpression = new LikeExpression();
        likeExpression.setLeftExpression(aliasDataScopeColumn);
        StringValue value = new StringValue(dataScope + StringPool.PERCENT);
        likeExpression.setRightExpression(value);
        return likeExpression;
    }

    /**
     * 根据表名判断是否忽略拼接数据范围条件
     * <p>
     * 默认都要进行解析并拼接数据范围
     * - 启用表列表不为空，则只需要判断表名是否启用
     * - 启用表列表为空，则判断是否在白名单中
     *
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接数据范围
     */
    @Override
    public boolean ignoreTable(String tableName) {
        List<String> enableTables = dataScopeProperties.getEnableTables();
        List<String> ignoreTables = dataScopeProperties.getIgnoreTables();
        return CollectionUtils.isNotEmpty(enableTables)
                ? CollectionUtils.notContains(enableTables, tableName)
                : CollectionUtils.contains(ignoreTables, tableName);
    }

    /**
     * 根据表名判断是否忽略拼接数据范围条件
     * <p>
     * 默认都要进行解析并拼接数据范围
     * - 启用表列表不为空，则只需要判断表名是否启用
     * - 启用表列表为空，则判断是否在白名单中
     *
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接数据范围
     */
    @Override
    public boolean ignoreTable(Table table, SqlType sqlType) {
        return !SqlType.SELECT.equals(sqlType) && ignoreTable(table.getName());
    }

    /**
     * 忽略插入租户字段逻辑
     *
     * @param tableName 表名
     * @return 是否忽略
     */
    @Override
    public boolean ignoreInsert(String tableName) {
        List<String> enableTables = dataScopeProperties.getEnableInsertTables();
        List<String> ignoreTables = dataScopeProperties.getIgnoreInsertTables();
        return CollectionUtils.isNotEmpty(enableTables)
                ? CollectionUtils.notContains(enableTables, tableName)
                : CollectionUtils.contains(ignoreTables, tableName);
    }

}
