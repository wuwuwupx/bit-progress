package com.bitprogress.mybatispluscore.handler;

import com.bitprogress.mybatispluscore.properties.TenantProperties;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.query.TenantQuery;
import com.bitprogress.ormparser.Service.TenantOrmDataService;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.AllValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class DefaultTenantHandler implements TenantHandler {

    private final TenantProperties tenantProperties;
    private final TenantOrmDataService tenantOrmDataService;

    /**
     * 是否启用
     *
     * @return 是否启用
     */
    @Override
    public boolean isEnabled() {
        return tenantProperties.getEnabled();
    }

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    @Override
    public Expression getTenantId() {
        return new LongValue(tenantOrmDataService.getTenantId());
    }

    /**
     * 获取数据范围条件
     *
     * @param table 表
     * @return 数据范围条件
     */
    @Override
    public Expression getCondition(Table table) {
        TenantQuery conditionQuery = tenantOrmDataService.getConditionQuery();
        if (conditionQuery.isNotNeedQuery()) {
            return new NullValue();
        }
        if (conditionQuery.isQueryAll()) {
            return new AllValue();
        }
        Column tenantIdColumn = getAliasTenantIdColumn(table);
        return buildExpressionByQueryType(tenantIdColumn, conditionQuery.getTenantIds(), conditionQuery.getQueryType());
    }

    /**
     * 构建like表达式
     *
     * @param column  字段
     * @param dataSet 数据
     * @return like表达式
     */
    @Override
    public Expression buildLikeExpression(Column column, Set<Long> dataSet) {
        return null;
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
        List<String> enableTables = tenantProperties.getEnableTables();
        List<String> ignoreTables = tenantProperties.getIgnoreTables();
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
     * 设置当前条件类型
     *
     * @param sqlType sql类型
     * @return 是否设置成功
     */
    @Override
    public boolean setCurrentConditionType(SqlType sqlType) {
        return tenantOrmDataService.setCurrentConditionType(sqlType);
    }

    /**
     * 清除当前条件类型
     */
    @Override
    public void clearCurrentConditionType() {
        tenantOrmDataService.clearCurrentConditionType();
    }

}
