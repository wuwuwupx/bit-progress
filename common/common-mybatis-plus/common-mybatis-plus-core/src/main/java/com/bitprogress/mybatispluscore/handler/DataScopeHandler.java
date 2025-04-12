package com.bitprogress.mybatispluscore.handler;

import com.bitprogress.util.CollectionUtils;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

import java.util.List;
import java.util.Set;

public interface DataScopeHandler extends InterceptorHandler<String> {

    /**
     * 数据范围字段别名设置
     * <p>dataScope 或 tableAlias.${dataScope}</p>
     *
     * @param table 表对象
     * @return 字段
     */
    default Column getAliasDataScopeColumn(Table table) {
        String dataScopeColumn = getTableDataScopeColumn(table.getName());
        return getAliasColumn(table.getAlias(), dataScopeColumn);
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
     * 数据范围字段别名设置
     * <p>${owned} 或 tableAlias.${owned}</p>
     *
     * @param table 表对象
     * @return 字段
     */
    default Column getAliasOwnedColumn(Table table) {
        String ownedColumn = getTableOwnedColumn(table.getName());
        return getAliasColumn(table.getAlias(), ownedColumn);
    }

    /**
     * 根据表名获取用于匹配当前用户的字段
     *
     * @return 用于匹配当前用户的字段名
     */
    default String getTableOwnedColumn(String tableName) {
        return getSourceOwnedColumn();
    }

    /**
     * 获取拥有数据的字段
     *
     * @return 自身条件字段名
     */
    default String getSourceOwnedColumn() {
        return "sales_id";
    }

    /**
     * 数据范围字段别名设置
     * <p>${self} 或 tableAlias.${self}</p>
     *
     * @param table 表对象
     * @return 字段
     */
    default Column getAliasSelfColumn(Table table) {
        String selfColumn = getTableSelfColumn(table.getName());
        return getAliasColumn(table.getAlias(), selfColumn);
    }

    /**
     * 根据表名获取用于匹配当前用户的字段
     *
     * @return 用于匹配当前用户的字段名
     */
    default String getTableSelfColumn(String tableName) {
        return getSourceSelfColumn();
    }

    /**
     * 获取自身条件的字段
     *
     * @return 自身条件字段名
     */
    default String getSourceSelfColumn() {
        return "user_id";
    }

    /**
     * 获取数据范围
     *
     * @return 数据范围
     */
    Expression getDataScope();

    /**
     * 获取拥有数据的字段值
     *
     * @return 数据范围
     */
    Expression getOwnedData();

    /**
     * 获取自身数据的字段值
     *
     * @return 数据范围
     */
    Expression getSelfData();

    /**
     * 构建in表达式
     *
     * @param dataScopeColumn 数据范围字段
     * @param dataScope       数据范围
     * @return in表达式
     */
    default Expression buildEqualExpression(Column dataScopeColumn, String dataScope) {
        return new EqualsTo(dataScopeColumn, new StringValue(dataScope));
    }

    /**
     * 构建in表达式
     *
     * @param dataScopeColumn 数据范围字段
     * @param dataScopes      数据范围
     * @return in表达式
     */
    default Expression buildInExpression(Column dataScopeColumn, Set<String> dataScopes) {
        List<StringValue> values = CollectionUtils.toList(dataScopes, StringValue::new);
        return new InExpression(dataScopeColumn, new ParenthesedExpressionList<>(values));
    }

    /**
     * 构建like表达式
     *
     * @param column     数据范围字段
     * @param dataScopes 数据范围
     * @return like表达式
     */
    default Expression buildLikeExpression(Column column, Set<String> dataScopes) {
        if (CollectionUtils.isSingle(dataScopes)) {
            LikeExpression likeExpression = new LikeExpression();
            likeExpression.setLeftExpression(column);
            likeExpression.setRightExpression(new StringValue(dataScopes.iterator().next()));
            return likeExpression;
        }
        int index = 0;
        OrExpression orExpression = new OrExpression();
        for (String dataScope : dataScopes) {
            LikeExpression likeExpression = new LikeExpression();
            likeExpression.setLeftExpression(column);
            likeExpression.setRightExpression(new StringValue(dataScope));
            if (index == 0) {
                orExpression.setLeftExpression(likeExpression);
            } else if (index == 1) {
                orExpression.setRightExpression(likeExpression);
            } else {
                OrExpression newOrExpression = new OrExpression();
                newOrExpression.setLeftExpression(orExpression);
                newOrExpression.setRightExpression(likeExpression);
                orExpression = newOrExpression;
            }
            index++;
        }
        return orExpression;
    }

    /**
     * 是否忽略表，默认不忽略
     *
     * @return 是否忽略
     */
    default boolean enableInsertDataScope(String tableName) {
        return false;
    }

    /**
     * 忽略查询拥有数据，默认忽略
     *
     * @param tableName 表名
     * @return 是否忽略
     */
    default boolean enableInsertOwned(String tableName) {
        return true;
    }

    /**
     * 忽略查询自身数据，默认忽略
     *
     * @param tableName 插入字段
     * @return 是否忽略
     */
    default boolean enableInsertSelf(String tableName) {
        return true;
    }

    /**
     * 是否忽略表，默认不忽略
     *
     * @return 是否忽略
     */
    default boolean enableQueryDataScope(String tableName) {
        return false;
    }

    /**
     * 忽略查询拥有数据，默认忽略
     *
     * @param tableName 表名
     * @return 是否忽略
     */
    default boolean enableQueryOwned(String tableName) {
        return true;
    }

    /**
     * 忽略查询自身数据，默认忽略
     *
     * @param tableName 插入字段
     * @return 是否忽略
     */
    default boolean enableQuerySelf(String tableName) {
        return true;
    }

}
