package com.bitprogress.mybatispluscore.handler;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.bitprogress.ormmodel.enums.SqlOperatorType;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.util.CollectionUtils;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public interface InterceptorHandler<T> {

    /**
     * 是否启用
     *
     * @return 是否启用
     */
    boolean isEnabled();

    /**
     * 数据范围字段别名设置
     * <p>columnName 或 tableAlias.${columnName}</p>
     *
     * @param alias 别名
     * @return 字段
     */
    default Column getAliasColumn(Alias alias, String columnName) {
        StringBuilder column = new StringBuilder();
        if (Objects.nonNull(alias)) {
            column.append(alias.getName()).append(StringPool.DOT);
        }
        column.append(columnName);
        return new Column(column.toString());
    }

    /**
     * 获取数据范围条件
     *
     * @param table 表
     * @return 数据范围条件
     */
    Expression getCondition(Table table);

    /**
     * 根据查询类型构建表达式
     *
     * @param column  字段
     * @param dataSet 数据
     * @return in表达式
     */
    default Expression buildExpressionBySqlOperatorType(Column column, Set<T> dataSet, SqlOperatorType sqlOperatorType) {
        switch (sqlOperatorType) {
            case EQUAL -> {
                return buildEqualExpression(column, dataSet);
            }
            case IN -> {
                return buildInExpression(column, dataSet);
            }
            case LIKE -> {
                return buildLikeExpression(column, dataSet);
            }
            default -> throw new IllegalStateException("Unexpected value: " + sqlOperatorType);
        }
    }

    /**
     * 构建 = 表达式
     *
     * @param column     字段
     * @param dataScopes 数据范围
     * @return = 表达式
     */
    default Expression buildEqualExpression(Column column, Set<T> dataScopes) {
        return buildEqualExpression(column, dataScopes.iterator().next());
    }

    /**
     * 构建 = 表达式
     *
     * @param column 字段
     * @param data   数据范围
     * @return = 表达式
     */
    Expression buildEqualExpression(Column column, T data);

    /**
     * 构建in表达式
     *
     * @param column  字段
     * @param dataSet 数据列表
     * @return in表达式
     */
    Expression buildInExpression(Column column, Set<T> dataSet);

    /**
     * 构建like表达式
     *
     * @param column  字段
     * @param dataSet 数据
     * @return like表达式
     */
    Expression buildLikeExpression(Column column, Set<T> dataSet);

    /**
     * 是否忽略表，默认不忽略
     *
     * @param tableName 表名
     * @return 是否忽略
     */
    default boolean ignoreTable(String tableName) {
        return false;
    }

    /**
     * 是否忽略表，默认不忽略
     *
     * @return 是否忽略
     */
    default boolean ignoreTable(Table table, SqlType sqlType) {
        return false;
    }

    /**
     * 启用插入字段逻辑
     *
     * @param columns 表名
     * @param column  字段
     * @return 是否启用
     */
    default boolean enableInsertColumn(List<Column> columns, String column) {
        return CollectionUtils.noneMatch(columns, i -> i.getColumnName().equalsIgnoreCase(column));
    }

    /**
     * 设置当前条件类型
     *
     * @param sqlType sql类型
     * @return 是否设置成功
     */
    boolean setCurrentConditionType(SqlType sqlType);

    /**
     * 清除当前条件类型
     */
    void clearCurrentConditionType();

}
