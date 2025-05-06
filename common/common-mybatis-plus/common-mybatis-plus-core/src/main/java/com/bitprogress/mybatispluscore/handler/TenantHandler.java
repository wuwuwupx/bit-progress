package com.bitprogress.mybatispluscore.handler;

import com.bitprogress.util.CollectionUtils;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

import java.util.List;
import java.util.Set;

public interface TenantHandler extends InterceptorHandler<String> {

    /**
     * 数据范围字段别名设置
     * <p>columnName 或 tableAlias.${columnName}</p>
     *
     * @param table 表
     * @return 字段
     */
    default Column getAliasTenantIdColumn(Table table) {
        String tenantIdColumn = getTenantIdColumn();
        return getAliasColumn(table.getAlias(), tenantIdColumn);
    }

    /**
     * 获取租户ID字段
     *
     * @return 租户ID字段
     */
    default String getTenantIdColumn() {
        return "tenant_id";
    }

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    Expression getTenantId();

    /**
     * 构建in表达式
     *
     * @param column   字段
     * @param tenantId 租户ID
     * @return in表达式
     */
    default Expression buildEqualExpression(Column column, String tenantId) {
        return new EqualsTo(column, new StringValue(tenantId));
    }

    /**
     * 构建in表达式
     *
     * @param column    字段
     * @param tenantIds 租户ID
     * @return in表达式
     */
    default Expression buildInExpression(Column column, Set<String> tenantIds) {
        List<StringValue> values = CollectionUtils.toList(tenantIds, StringValue::new);
        return new InExpression(column, new ParenthesedExpressionList<>(values));
    }

}
