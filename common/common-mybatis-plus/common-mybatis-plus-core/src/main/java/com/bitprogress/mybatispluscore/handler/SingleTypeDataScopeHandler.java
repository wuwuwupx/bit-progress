package com.bitprogress.mybatispluscore.handler;

import com.bitprogress.mybatispluscore.properties.DataScopeProperties;
import com.bitprogress.ormmodel.enums.SqlOperatorType;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.query.DataScopeQuery;
import com.bitprogress.ormparser.Service.DataScopeOrmDataService;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
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
    private final DataScopeOrmDataService ormDataService;

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
    public String getTableDataScopeColumn(String tableName) {
        return CollectionUtils.getForMap(dataScopeProperties.getDataScopeColumn(), tableName, getSourceDataScopeColumn());
    }

    /**
     * 获取自身匹配数据条件字段
     *
     * @return 自身匹配数据条件字段
     */
    @Override
    public String getTableOwnedColumn(String tableName) {
        return CollectionUtils.getForMap(dataScopeProperties.getOwnedWhereColumn(), tableName, getSourceOwnedColumn());
    }

    /**
     * 获取自身匹配数据条件字段
     *
     * @return 自身匹配数据条件字段
     */
    @Override
    public String getTableSelfColumn(String tableName) {
        return CollectionUtils.getForMap(dataScopeProperties.getSelfWhereColumn(), tableName, getSourceSelfColumn());
    }

    /**
     * 获取数据范围
     *
     * @return 数据范围
     */
    @Override
    public Expression getDataScope() {
        return new StringValue(ormDataService.getDataScope());
    }

    /**
     * 获取拥有数据的字段值
     *
     * @return 数据范围
     */
    @Override
    public Expression getOwnedData() {
        return new LongValue(ormDataService.getOwnedData());
    }

    /**
     * 获取自身数据的字段值
     *
     * @return 数据范围
     */
    @Override
    public Expression getSelfData() {
        return new LongValue(ormDataService.getSelfData());
    }

    /**
     * 获取数据范围条件
     *
     * @return 数据范围条件
     */
    @Override
    public Expression getCondition(Table table) {
        DataScopeQuery dataScopeQuery = ormDataService.getConditionQuery();
        if (dataScopeQuery.isNotNeedQuery()) {
            return new NullValue();
        }
        if (dataScopeQuery.isQueryAll()) {
            return new AllValue();
        }
        String tableName = table.getName();
        Column dataScopeColumn = getAliasDataScopeColumn(table);
        Expression expression = null;
        if (dataScopeQuery.hasRangeQuery() && enableQueryDataScope(tableName)) {
            Set<String> dataScopes = dataScopeQuery.getRangeDataScopes();
            /*
             * data_scope in (A1,A1B2)
             * data_scope = A1
             * data_scope like A1%
             * data_scope like A1% or data_scope like A2%
             */
            expression = buildLikeExpression(dataScopeColumn, dataScopes);
        }
        if (dataScopeQuery.hasExactQuery() && enableQueryDataScope(tableName)) {
            Set<String> dataScopes = dataScopeQuery.getExactDataScopes();
            SqlOperatorType operatorType = dataScopeQuery.getExactSqlOperatorType();
            /*
             * data_scope in (A1,A1B2)
             * data_scope = A1
             */
            Expression exactExpression = buildExpressionBySqlOperatorType(dataScopeColumn, dataScopes, operatorType);
            /*
             * data_scope in (A1,A1B2) or data_scope in (A1,A1B2)
             */
            expression = Objects.isNull(expression) ? exactExpression : new OrExpression(expression, exactExpression);
        }
        if (dataScopeQuery.hasOwnedQuery() && enableQueryOwned(tableName)) {
            /*
             * owned = 1L
             */
            Expression ownedExpression = buildOwnedExpression(table, dataScopeQuery.getOwnedData());
            /*
             * data_scope in (A1,A1B2) or data_scope in (A1,A1B2) or owned = 1L
             */
            expression = Objects.isNull(expression) ? ownedExpression : new OrExpression(expression, ownedExpression);
        }
        if (dataScopeQuery.hasSelfQuery() && enableQuerySelf(tableName)) {
            /*
             * self = 1L
             */
            Expression selfExpression = buildSelfExpression(table, dataScopeQuery.getSelfData());
            /*
             * data_scope in (A1,A1B2) or data_scope in (A1,A1B2) or owned = 1L or self = 1L
             */
            expression = Objects.isNull(expression) ? selfExpression : new OrExpression(expression, selfExpression);
        }
        return Objects.isNull(expression) ? new NullValue() : new ParenthesedExpressionList<>(expression);
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
     * 启用插入数据范围字段逻辑
     *
     * @param tableName 表名
     * @return 是否启用
     */
    @Override
    public boolean enableInsertDataScope(String tableName) {
        List<String> enableTables = dataScopeProperties.getEnableInsertDataScopeTables();
        List<String> ignoreTables = dataScopeProperties.getIgnoreInsertDataScopeTables();
        return CollectionUtils.isNotEmpty(enableTables)
                ? CollectionUtils.contains(enableTables, tableName)
                : CollectionUtils.notContains(ignoreTables, tableName);
    }

    /**
     * 启用插入拥有字段逻辑
     *
     * @param tableName 表名
     * @return 是否启用
     */
    @Override
    public boolean enableInsertOwned(String tableName) {
        List<String> enableTables = dataScopeProperties.getEnableInsertOwnedTables();
        List<String> ignoreTables = dataScopeProperties.getIgnoreInsertOwnedTables();
        return CollectionUtils.isNotEmpty(enableTables)
                ? CollectionUtils.contains(enableTables, tableName)
                : CollectionUtils.notContains(ignoreTables, tableName);
    }

    /**
     * 启用插入自身字段逻辑
     *
     * @param tableName 表名
     * @return 是否启用
     */
    @Override
    public boolean enableInsertSelf(String tableName) {
        List<String> enableTables = dataScopeProperties.getEnableInsertSelfTables();
        List<String> ignoreTables = dataScopeProperties.getIgnoreInsertSelfTables();
        return CollectionUtils.isNotEmpty(enableTables)
                ? CollectionUtils.contains(enableTables, tableName)
                : CollectionUtils.notContains(ignoreTables, tableName);
    }

    /**
     * 启用查询数据范围字段逻辑
     *
     * @param tableName 表名
     * @return 是否启用
     */
    @Override
    public boolean enableQueryDataScope(String tableName) {
        List<String> enableTables = dataScopeProperties.getEnableQueryDataScopeTables();
        List<String> ignoreTables = dataScopeProperties.getIgnoreQueryDataScopeTables();
        return CollectionUtils.isNotEmpty(enableTables)
                ? CollectionUtils.contains(enableTables, tableName)
                : CollectionUtils.notContains(ignoreTables, tableName);
    }

    /**
     * 启用查询拥有字段逻辑
     *
     * @param tableName 表名
     * @return 是否启用
     */
    @Override
    public boolean enableQueryOwned(String tableName) {
        List<String> enableTables = dataScopeProperties.getEnableQueryOwnedTables();
        List<String> ignoreTables = dataScopeProperties.getIgnoreQueryOwnedTables();
        return CollectionUtils.isNotEmpty(enableTables)
                ? CollectionUtils.contains(enableTables, tableName)
                : CollectionUtils.notContains(ignoreTables, tableName);
    }

    /**
     * 启用查询自身字段逻辑
     *
     * @param tableName 表名
     * @return 是否启用
     */
    @Override
    public boolean enableQuerySelf(String tableName) {
        List<String> enableTables = dataScopeProperties.getEnableQuerySelfTables();
        List<String> ignoreTables = dataScopeProperties.getIgnoreQuerySelfTables();
        return CollectionUtils.isNotEmpty(enableTables)
                ? CollectionUtils.contains(enableTables, tableName)
                : CollectionUtils.notContains(ignoreTables, tableName);
    }

    /**
     * 缓存前一sql上下文
     */
    @Override
    public void cachePreSqlContext() {
        ormDataService.cachePreSqlContext();
    }

    /**
     * 设置当前sql上下文
     *
     * @param sqlType sql类型
     * @return 是否设置成功
     */
    @Override
    public boolean setCurrentSqlContextBySqlType(SqlType sqlType) {
        return ormDataService.setCurrentSqlContextBySqlType(sqlType);
    }

    /**
     * 清除sql上下文
     */
    @Override
    public void clearCurrentSqlContext() {
        ormDataService.clearCurrentSqlContext();
    }

    /**
     * 恢复前一sql上下文
     */
    @Override
    public void restorePreSqlContext() {
        ormDataService.restorePreSqlContext();
    }

}
