package com.bitprogress.mybatisplus.handler;

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
     * 获取数据范围
     *
     * @return 数据范围
     */
    Expression getDataScope();

    /**
     * 获取可查询数据范围列表
     *
     * @return 数据范围
     */
    List<Expression> getDataScopes();

    /**
     * 获取数据范围字段
     *
     * @return 数据范围字段
     */
    default String getDataScopeColumn(String tableName) {
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
