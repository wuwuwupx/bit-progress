package com.bitprogress.mybatispluscore.interceptor;

import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.BaseMultiTableInnerInterceptor;
import com.bitprogress.mybatispluscore.handler.InterceptorHandler;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.systemcontext.service.DispatcherContextService;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.AllValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
public abstract class SqlInnerInterceptor extends BaseMultiTableInnerInterceptor {

    protected final InterceptorHandler<?> interceptorHandler;

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        // 原有的过滤逻辑不能自由定制，不采用
//        if (InterceptorIgnoreHelper.willIgnoreTenantLine(ms.getId())) {
//            return;
//        }
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        mpBs.sql(parserSingle(mpBs.sql(), null));
    }

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct == SqlCommandType.INSERT || sct == SqlCommandType.UPDATE || sct == SqlCommandType.DELETE) {
            if (InterceptorIgnoreHelper.willIgnoreTenantLine(ms.getId())) {
                return;
            }
            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            mpBs.sql(parserMulti(mpBs.sql(), null));
        }
    }

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        process(SqlType.SELECT, null, index, t -> processSelectSql(select, index, sql, obj));
    }

    protected void processSelectSql(Select select, int index, String sql, Object obj) {
        final String whereSegment = (String) obj;
        processSelectBody(select, whereSegment);
        List<WithItem<?>> withItemsList = select.getWithItemsList();
        if (!CollectionUtils.isEmpty(withItemsList)) {
            withItemsList.forEach(withItem -> processSelectBody(withItem.getSelect(), whereSegment));
        }
    }

    /**
     * 处理 insert 语句
     */
    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        process(SqlType.INSERT, insert.getTable(), index, t -> processInsertSql(insert, index, sql, obj));
    }

    /**
     * 处理 insert 语句
     */
    protected void processInsertSql(Insert insert, int index, String sql, Object obj) {
        String tableName = insert.getTable().getName();
        if (interceptorHandler.ignoreTable(tableName) || interceptorHandler.ignoreInsert(tableName)) {
            // 过滤退出执行
            return;
        }
        List<Column> columns = insert.getColumns();
        if (CollectionUtils.isEmpty(columns)) {
            // 针对不给列名的insert 不处理
            return;
        }
        String column = interceptorHandler.getInsertColumn();
        Expression value = interceptorHandler.getInsertValue();
        if (!interceptorHandler.enableInsertColumn(columns, column)) {
            return;
        }

        columns.add(new Column(column));
        List<UpdateSet> duplicateUpdateColumns = insert.getDuplicateUpdateSets();
        if (CollectionUtils.isNotEmpty(duplicateUpdateColumns)) {
            duplicateUpdateColumns.add(new UpdateSet(new Column(column), value));
        }

        Select select = insert.getSelect();
        if (select instanceof PlainSelect) {
            this.processInsertSelect(select, (String) obj);
        } else if (insert.getValues() != null) {
            // fixed github pull/295
            Values values = insert.getValues();
            ExpressionList<Expression> expressions = (ExpressionList<Expression>) values.getExpressions();
            if (expressions instanceof ParenthesedExpressionList) {
                expressions.addExpression(value);
            } else {
                if (CollectionUtils.isNotEmpty(expressions)) {
                    //fix github issue 4998 jsqlparse 4.5 批量insert ItemsList不是MultiExpressionList 了，需要特殊处理
                    int len = expressions.size();
                    for (int i = 0; i < len; i++) {
                        Expression expression = expressions.get(i);
                        if (expression instanceof ParenthesedExpressionList expressionList) {
                            expressionList.addExpression(value);
                        } else {
                            expressions.addExpression(value);
                        }
                    }
                } else {
                    expressions.addExpression(value);
                }
            }
        } else {
            throw ExceptionUtils.mpe("Failed to process multiple-table update, please exclude the tableName or statementId");
        }
    }

    /**
     * 处理 insert into select
     * <p>
     * 进入这里表示需要 insert 的表启用了多租户,则 select 的表都启动了
     *
     * @param selectBody SelectBody
     */
    protected void processInsertSelect(Select selectBody, final String whereSegment) {
        if (selectBody instanceof PlainSelect plainSelect) {
            FromItem fromItem = plainSelect.getFromItem();
            if (fromItem instanceof Table) {
                // fixed gitee pulls/141 duplicate update
                processPlainSelect(plainSelect, whereSegment);
                appendSelectItem(plainSelect.getSelectItems());
            } else if (fromItem instanceof Select subSelect) {
                appendSelectItem(plainSelect.getSelectItems());
                processInsertSelect(subSelect, whereSegment);
            }
        } else if (selectBody instanceof ParenthesedSelect parenthesedSelect) {
            processInsertSelect(parenthesedSelect.getSelect(), whereSegment);

        }
    }

    /**
     * 追加 SelectItem
     *
     * @param selectItems SelectItem
     */
    protected void appendSelectItem(List<SelectItem<?>> selectItems) {
        if (com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isEmpty(selectItems)) {
            return;
        }
        if (selectItems.size() == 1) {
            SelectItem<?> item = selectItems.getFirst();
            Expression expression = item.getExpression();
            if (expression instanceof AllColumns) {
                return;
            }
        }
        selectItems.add(new SelectItem<>(new Column(interceptorHandler.getInsertColumn())));
    }

    /**
     * update 语句处理
     */
    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        process(SqlType.UPDATE, update.getTable(), index, t -> processUpdateSql(update, index, sql, obj));
    }

    /**
     * update 语句处理
     */
    protected void processUpdateSql(Update update, int index, String sql, Object obj) {
        final Table table = update.getTable();
        if (interceptorHandler.ignoreTable(table.getName())) {
            // 过滤退出执行
            return;
        }
        List<UpdateSet> sets = update.getUpdateSets();
        if (!CollectionUtils.isEmpty(sets)) {
            sets.forEach(us -> us.getValues().forEach(ex -> {
                if (ex instanceof Select) {
                    processSelectBody(((Select) ex), (String) obj);
                }
            }));
        }
        update.setWhere(this.andExpression(table, update.getWhere(), (String) obj));
    }

    /**
     * delete 语句处理
     */
    @Override
    protected void processDelete(Delete delete, int index, String sql, Object obj) {
        process(SqlType.DELETE, delete.getTable(), index, t -> processDeleteSql(delete, index, sql, obj));
    }

    /**
     * delete 语句处理
     */
    protected void processDeleteSql(Delete delete, int index, String sql, Object obj) {
        if (interceptorHandler.ignoreTable(delete.getTable().getName())) {
            // 过滤退出执行
            return;
        }
        delete.setWhere(this.andExpression(delete.getTable(), delete.getWhere(), (String) obj));
    }

    /**
     * sql 语句处理
     *
     * @param sqlType  sql类型
     * @param index    索引
     * @param consumer sql方法
     */
    protected void process(SqlType sqlType, Table table, Integer index, Consumer<Integer> consumer) {
        // 检查是否无状态调度，无状态调度线程没有上下文信息，不进行sql处理
        if (DispatcherContextService.isNoneStatusDispatch()) {
            return;
        }
        // 检查是否启用了租户和数据范围
        if (!interceptorHandler.isEnabled()) {
            return;
        }
        // 检查 非select语句 的表是否启用
        if (interceptorHandler.ignoreTable(table, sqlType)) {
            return;
        }
        // 处理上下文
        interceptorHandler.cachePreSqlContext();
        try {
            if (interceptorHandler.setCurrentSqlContextBySqlType(sqlType)) {
                consumer.accept(index);
            }
        } finally {
            // 清除信息
            interceptorHandler.clearCurrentSqlContext();
            interceptorHandler.restorePreSqlContext();
        }
    }

    /**
     * 数据范围条件表达式
     *
     * @param table        表对象
     * @param where        当前where条件
     * @param whereSegment 所属Mapper对象全路径（在原租户拦截器功能中，这个参数并不需要参与相关判断）
     * @return 数据范围条件表达式
     * @see BaseMultiTableInnerInterceptor#buildTableExpression(Table, Expression, String)
     */
    @Override
    public Expression buildTableExpression(final Table table, final Expression where, final String whereSegment) {
        Expression condition = interceptorHandler.getCondition(table);
        if (condition instanceof NullValue) {
            return new EqualsTo(new Column("1"), new StringValue("2"));
        } else if (condition instanceof AllValue) {
            return null;
        } else {
            return condition;
        }

    }

}
