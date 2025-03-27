package com.bitprogress.mybatisplus.plugins;

import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.bitprogress.basecontext.context.DispatcherContext;
import com.bitprogress.mybatisplus.handler.DataScopeLineHandler;
import com.bitprogress.mybatisplus.handler.TenantIdLineHandler;
import com.bitprogress.ormcontext.utils.TenantContextUtils;
import com.bitprogress.ormmodel.enums.DataScopeType;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.enums.TenantType;
import com.bitprogress.ormparser.context.SqlParserContext;
import com.bitprogress.ormparser.util.SqlParserUtils;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.AllValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.Values;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 先检查sql解析模式
 */
@AllArgsConstructor
public class TenantSqlInnerInterceptor extends TenantLineInnerInterceptor {

    private final TenantIdLineHandler tenantIdLineHandler;
    private final DataScopeLineHandler dataScopeLineHandler;

    /**
     * select 语句处理
     */
    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        process(SqlType.SELECT, null, index, t -> super.processSelect(select, t, sql, obj));
    }

    /**
     * insert 语句处理
     */
    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        process(SqlType.INSERT, insert.getTable(), index, t -> processInsertSql(insert, t, sql, obj));
    }

    /**
     * insert 语句处理
     */
    private void processInsertSql(Insert insert, int index, String sql, Object obj) {
        TenantType tenantType = SqlParserContext.getCurrentSqlTenantType();
        DataScopeType dataScopeType = SqlParserContext.getCurrentSqlDataScopeType();
        boolean tenantEnabled = Objects.nonNull(tenantType);
        boolean dataScopeEnabled = Objects.nonNull(dataScopeType);
        if (!tenantEnabled && !dataScopeEnabled) {
            return;
        }
        List<Column> columns = insert.getColumns();
        if (CollectionUtils.isEmpty(columns)) {
            // 针对不给列名的insert 不处理
            return;
        }
        String tenantIdColumn = tenantIdLineHandler.getTenantIdColumn();
        // insert语句需要新增的是原始数据范围列
        String tableDataScopeColumn = dataScopeLineHandler.getTableDataScopeColumn(insert.getTable().getName());
        boolean isNeedAddTenantId = tenantEnabled && !tenantIdLineHandler.ignoreInsert(columns, tenantIdColumn);
        boolean isNeedAddSourceDataScope = dataScopeEnabled
                && !dataScopeLineHandler.ignoreInsert(columns, tableDataScopeColumn);
        if (!isNeedAddTenantId && !isNeedAddSourceDataScope) {
            return;
        }
        Expression tenantId = tenantIdLineHandler.getInsertTenantId();
        Expression dataScope = dataScopeLineHandler.getCurrentDataScope();
        if (isNeedAddTenantId) {
            List<UpdateSet> duplicateUpdateColumns = insert.getDuplicateUpdateSets();
            columns.add(new Column(tenantIdColumn));
            if (CollectionUtils.isNotEmpty(duplicateUpdateColumns)) {
                duplicateUpdateColumns.add(new UpdateSet(new Column(tenantIdColumn), tenantId));
            }
        }
        if (isNeedAddSourceDataScope) {
            columns.add(new Column(tableDataScopeColumn));
            List<UpdateSet> duplicateUpdateColumns = insert.getDuplicateUpdateSets();
            if (CollectionUtils.isNotEmpty(duplicateUpdateColumns)) {
                duplicateUpdateColumns.add(new UpdateSet(new Column(tableDataScopeColumn), dataScope));
            }
        }

        Select select = insert.getSelect();
        if (select instanceof PlainSelect) { //fix github issue 4998  修复升级到4.5版本的问题
            this.processInsertSelect(select, (String) obj);
        } else if (insert.getValues() != null) {
            Values values = insert.getValues();
            ExpressionList<Expression> expressions = (ExpressionList<Expression>) values.getExpressions();
            if (expressions instanceof ParenthesedExpressionList) {
                if (isNeedAddTenantId) {
                    expressions.addExpression(tenantId);
                }
                if (isNeedAddSourceDataScope) {
                    expressions.addExpression(dataScope);
                }
            } else {
                if (CollectionUtils.isNotEmpty(expressions)) {//fix github issue 4998 jsqlparse 4.5 批量insert ItemsList不是MultiExpressionList 了，需要特殊处理
                    int len = expressions.size();
                    for (int i = 0; i < len; i++) {
                        Expression expression = expressions.get(i);
                        if (expression instanceof ParenthesedExpressionList) {
                            ParenthesedExpressionList<Expression> expressionList = (ParenthesedExpressionList<Expression>) expression;
                            if (isNeedAddTenantId) {
                                expressionList.addExpression(tenantId);
                            }
                            if (isNeedAddSourceDataScope) {
                                expressionList.addExpression(dataScope);
                            }
                        } else {
                            if (isNeedAddTenantId) {
                                expressions.add(tenantId);
                            }
                            if (isNeedAddSourceDataScope) {
                                expressions.add(dataScope);
                            }
                        }
                    }
                } else {
                    if (isNeedAddTenantId) {
                        expressions.add(tenantId);
                    }
                    if (isNeedAddSourceDataScope) {
                        expressions.add(dataScope);
                    }
                }
            }
        } else {
            throw ExceptionUtils.mpe("Failed to process multiple-table update, please exclude the tableName or statementId");
        }

    }

    /**
     * update 语句处理
     */
    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        process(SqlType.UPDATE, update.getTable(), index, t -> super.processUpdate(update, t, sql, obj));
    }

    /**
     * delete 语句处理
     */
    @Override
    protected void processDelete(Delete delete, int index, String sql, Object obj) {
        process(SqlType.DELETE, delete.getTable(), index, t -> super.processDelete(delete, t, sql, obj));
    }

    /**
     * 构建数据库表的查询条件
     *
     * @param table        表对象
     * @param where        当前where条件
     * @param whereSegment 所属Mapper对象全路径
     * @return 需要拼接的新条件（不会覆盖原有的where条件，只会在原有条件上再加条件），为 null 则不加入新的条件
     */
    @Override
    public Expression buildTableExpression(Table table, Expression where, String whereSegment) {
        TenantType tenantType = SqlParserContext.getCurrentSqlTenantType();
        DataScopeType dataScopeType = SqlParserContext.getCurrentSqlDataScopeType();
        boolean tenantEnabled = Objects.nonNull(tenantType) && !tenantIdLineHandler.ignoreTable(table.getName());
        boolean dataScopeEnabled = Objects.nonNull(dataScopeType) && !dataScopeLineHandler.ignoreTable(table.getName());
        if (!tenantEnabled && !dataScopeEnabled) {
            return null;
        }
        Expression expression = null;
        boolean init = false;
        if (tenantEnabled) {
            Column tenantIdColumn = getAliasTenantIdColumn(table);
            Expression tenantId = tenantIdLineHandler.getTenantId();
            if (Objects.isNull(tenantId)) {
                // 为空则表示没有可查询数据，当前表不需要再匹配
                return new EqualsTo(new Column("1"), new LongValue(2));
            } else {
                expression = TenantType.ALL.equals(tenantType)
                        ? new InExpression(tenantIdColumn, tenantId)
                        : new EqualsTo(tenantIdColumn, tenantId);
            }
            init = true;
        }
        if (dataScopeEnabled) {
            Column dataScopeColumn = getAliasDataScopeColumn(table);
            Expression dataScope = dataScopeLineHandler.getDataScope();
            if (dataScope instanceof NullValue) {
                // 为空则表示没有可查询数据，当前表不需要再匹配
                return new EqualsTo(new Column("1"), new LongValue(2));
            } else if (dataScope instanceof AllValue) {
                return expression;
            } else {
                Expression dataScopeExpression;
                switch (dataScopeType) {
                    // 自身和仅所属层级都是直接 equal 即可
                    case SELF, BELONG_LEVEL_CURRENT -> dataScopeExpression = new EqualsTo(dataScopeColumn, dataScope);
                    case BELONG_LEVEL -> {
                        LikeExpression likeExpression = new LikeExpression();
                        likeExpression.setLeftExpression(dataScopeColumn);
                        likeExpression.setRightExpression(dataScope);
                        dataScopeExpression = likeExpression;
                    }
                    case MANAGED_LEVEL_CURRENT, COMPOSITE_LEVEL_CURRENT ->
                            dataScopeExpression = new InExpression(dataScopeColumn, dataScope);
                    case MANAGED_LEVEL, COMPOSITE_LEVEL ->
                            dataScopeExpression = buildLikeExpressionList(dataScope, dataScopeColumn);
                    default -> throw ExceptionUtils.mpe("dataScope analysis error");
                }
                return init ? new AndExpression(expression, dataScopeExpression) : dataScopeExpression;
            }
        }
        return expression;
    }

    /**
     * 构建数据权限查询条件
     *
     * @param dataScope       数据权限集合
     * @param dataScopeColumn 数据权限字段
     * @return 条件
     */
    private Expression buildLikeExpressionList(Expression dataScope, Expression dataScopeColumn) {
        if (dataScope instanceof ExpressionList<?> dataScopes) {
            OrExpression orExpression = new OrExpression();
            for (int i = 0; i < dataScopes.size(); i++) {
                LikeExpression likeExpression = new LikeExpression();
                likeExpression.setLeftExpression(dataScopeColumn);
                likeExpression.setRightExpression(dataScopes.get(i));
                if (i == 0) {
                    orExpression.setLeftExpression(likeExpression);
                } else if (i == 1) {
                    orExpression.setRightExpression(likeExpression);
                } else {
                    OrExpression newOrExpression = new OrExpression();
                    newOrExpression.setLeftExpression(orExpression);
                    newOrExpression.setRightExpression(likeExpression);
                    orExpression = newOrExpression;
                }
            }
            return orExpression;
        }
        throw ExceptionUtils.mpe("dataScope analysis error");
    }

    /**
     * 租户字段别名设置
     * <p>tenantId 或 tableAlias.tenantId</p>
     *
     * @param table 表对象
     * @return 字段
     */
    protected Column getAliasTenantIdColumn(Table table) {
        StringBuilder column = new StringBuilder();
        if (table.getAlias() != null) {
            column.append(table.getAlias().getName()).append(StringPool.DOT);
        }
        column.append(tenantIdLineHandler.getTenantIdColumn());
        return new Column(column.toString());
    }

    /**
     * 数据范围字段别名设置
     * <p>tenantId 或 tableAlias.${dataScope}</p>
     *
     * @param table 表对象
     * @return 字段
     */
    protected Column getAliasDataScopeColumn(Table table) {
        StringBuilder column = new StringBuilder();
        if (table.getAlias() != null) {
            column.append(table.getAlias().getName()).append(StringPool.DOT);
        }
        column.append(dataScopeLineHandler.getDataScopeColumn(table.getName()));
        return new Column(column.toString());
    }

    /**
     * sql 语句处理
     *
     * @param sqlType  sql类型
     * @param index    索引
     * @param consumer sql方法
     */
    private void process(SqlType sqlType, Table table, Integer index, Consumer<Integer> consumer) {
        // 检查是否无状态调度，无状态调度线程没有上下文信息，不进行sql处理
        if (DispatcherContext.isNoneStatusDispatch()) {
            return;
        }
        // 检查是否启用了租户和数据范围
        boolean isTenantEnabled = tenantIdLineHandler.isEnabled();
        boolean isDataScopeEnabled = dataScopeLineHandler.isEnabled();
        if (!isTenantEnabled && !isDataScopeEnabled) {
            return;
        }
        // 检查 非select语句 的表是否启用
        boolean isTableTenantEnabled = !tenantIdLineHandler.ignoreTable(table, sqlType);
        boolean isTableDataScopeEnabled = !dataScopeLineHandler.ignoreTable(table, sqlType);
        if (!isTableTenantEnabled && !isTableDataScopeEnabled) {
            return;
        }
        // 检查是否使用 sql 解析模式
        boolean compositeTenantEnabled;
        boolean compositeDataScopeEnabled;
        try {
            if (SqlParserUtils.onSqlParser(sqlType)) {
                if (SqlParserUtils.ignoreProcess()) {
                    return;
                }
                // 检查是否为操作所有租户且用户可访问所有租户
                TenantType tenantType = SqlParserUtils.getTenantType();
                if (TenantContextUtils.isOperateAllTenant(tenantType)) {
                    return;
                }

                compositeTenantEnabled = SqlParserUtils.tenantEnabled() && isTableTenantEnabled;
                if (compositeTenantEnabled) {
                    // 设置当前 sql执行的租户类型
                    SqlParserContext.setCurrentSqlTenantTypeByParserMode();
                }
                // 当前租户状态为 all，但是当前用户只能访问到被允许的租户，这种情况下，方法不会直接return掉，还是需要进行下一步判断
                boolean isAllTenantType = TenantType.ALL.equals(tenantType);
                compositeDataScopeEnabled = SqlParserUtils.dataScopeEnabled()
                        && isTableDataScopeEnabled
                        && !isAllTenantType;
                if (compositeDataScopeEnabled) {
                    // 设置当前 sql执行的数据权限类型
                    SqlParserContext.setCurrentSqlDataScopeTypeByParserMode();
                }
            } else {
                // 非注解，使用 orm-context 中的信息
                // 检查是否为操作所有租户且用户可访问所有租户
                if (TenantContextUtils.isOperateAllTenant()) {
                    return;
                }

                compositeTenantEnabled = isTenantEnabled && isTableTenantEnabled;
                if (compositeTenantEnabled) {
                    SqlParserContext.setCurrentSqlTenantTypeByContext();
                }
                // 当前租户状态为 all，但是当前用户只能访问到被允许的租户，这种情况下，方法不会直接return掉，还是需要进行下一步判断

                boolean isAllTenantType = TenantType.ALL.equals(TenantContextUtils.getTenantType());
                compositeDataScopeEnabled = isDataScopeEnabled && isTableDataScopeEnabled && !isAllTenantType;
                if (compositeDataScopeEnabled) {
                    SqlParserContext.setCurrentSqlDataScopeTypeByContext();
                }
            }
            if (!compositeTenantEnabled && !compositeDataScopeEnabled) {
                return;
            }
            consumer.accept(index);
        } finally {
            // 清除信息
            SqlParserContext.clearCurrentSqlTenantType();
            SqlParserContext.clearCurrentSqlDataScopeType();
        }
    }

}
