package com.bitprogress.mybatispluscore.handler;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.bitprogress.ormmodel.enums.DataScopeType;
import com.bitprogress.ormmodel.enums.SqlType;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

import java.util.List;

public interface DataScopeLineHandler {

    /**
     * 是否启用
     *
     * @return 是否启用
     */
    boolean isEnabled();

    /**
     * 数据范围字段别名设置
     * <p>tenantId 或 tableAlias.${dataScope}</p>
     *
     * @param table 表对象
     * @return 字段
     */
    default Column getAliasDataScopeColumn(Table table, DataScopeType dataScopeType) {
        StringBuilder column = new StringBuilder();
        if (table.getAlias() != null) {
            column.append(table.getAlias().getName()).append(StringPool.DOT);
        }
        column.append(getDataScopeColumn(table.getName(), dataScopeType));
        return new Column(column.toString());
    }

    /**
     * 获取数据范围字段
     *
     * @return 数据范围字段
     */
    default String getDataScopeColumn(String tableName, DataScopeType dataScopeType) {
        return "data_scope";
    }

    /**
     * 获取数据范围字段
     * 根据表名获取
     *
     * @return 数据范围字段
     */
    default String getTableDataScopeColumn(String tableName) {
        return getSourceDataScopeColumn();
    }

    /**
     * 获取数据范围字段
     *
     * @return 数据范围字段
     */
    default String getSourceDataScopeColumn() {
        return "data_scope";
    }

    /**
     * 根据表名获取用于匹配当前用户的字段
     *
     * @return 用于匹配当前用户的字段名
     */
    default String getTableSelfWhereColumn(String tableName) {
        return getSourceSelfWhereColumn();
    }

    /**
     * 获取自身条件的字段
     *
     * @return 自身条件字段名
     */
    default String getSourceSelfWhereColumn() {
        return "user_id";
    }

    /**
     * 获取数据范围
     *
     * @return 数据范围
     */
    Expression getCurrentDataScope();

    /**
     * 获取数据范围条件
     *
     * @return 数据范围条件
     */
    Expression getDataScopeCondition(Table table);

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
     * 是否忽略表，默认不忽略
     *
     * @return 是否忽略
     */
    default boolean ignoreInsert(String tableName) {
        return false;
    }

    /**
     * 忽略插入租户字段逻辑
     *
     * @param columns         插入字段
     * @param dataScopeColumn 数据范围 字段
     * @return 是否忽略
     */
    default boolean ignoreInsert(List<Column> columns, String dataScopeColumn) {
        return columns.stream().map(Column::getColumnName).anyMatch(i -> i.equalsIgnoreCase(dataScopeColumn));
    }

}
