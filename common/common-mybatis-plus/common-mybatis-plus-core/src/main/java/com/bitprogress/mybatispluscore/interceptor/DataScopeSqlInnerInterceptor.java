package com.bitprogress.mybatispluscore.interceptor;

import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.BaseMultiTableInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.toolkit.PropertyMapper;
import com.bitprogress.basecontext.context.DispatcherContext;
import com.bitprogress.mybatispluscore.handler.DataScopeHandler;
import com.bitprogress.ormcontext.utils.TenantContextUtils;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormparser.context.SqlParserContext;
import com.bitprogress.ormparser.util.SqlParserUtils;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.sf.jsqlparser.expression.AllValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
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
import java.util.Properties;
import java.util.function.Consumer;

/**
 * 数据权限拦截器
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class DataScopeSqlInnerInterceptor extends BaseMultiTableInnerInterceptor implements InnerInterceptor {

    private DataScopeHandler dataScopeHandler;

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        if (InterceptorIgnoreHelper.willIgnoreTenantLine(ms.getId())) {
            return;
        }
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

    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        process(SqlType.INSERT, insert.getTable(), index, t -> processInsert(insert, index, sql, obj));
    }

    protected void processInsertSql(Insert insert, int index, String sql, Object obj) {
        String tableName = insert.getTable().getName();
        if (dataScopeHandler.ignoreTable(tableName)) {
            // 过滤退出执行
            return;
        }
        List<Column> columns = insert.getColumns();
        if (CollectionUtils.isEmpty(columns)) {
            // 针对不给列名的insert 不处理
            return;
        }
        String dataScopeColumn = dataScopeHandler.getTableDataScopeColumn(tableName);
        // 检查是否只针对 insert 关闭
        if (dataScopeHandler.ignoreInsert(tableName) || dataScopeHandler.ignoreInsert(columns, dataScopeColumn)) {
            return;
        }
        columns.add(new Column(dataScopeColumn));
        Expression dataScope = dataScopeHandler.getCurrentDataScope();
        // fixed gitee pulls/141 duplicate update
        List<UpdateSet> duplicateUpdateColumns = insert.getDuplicateUpdateSets();
        if (CollectionUtils.isNotEmpty(duplicateUpdateColumns)) {
            duplicateUpdateColumns.add(new UpdateSet(new Column(dataScopeColumn), dataScope));
        }

        Select select = insert.getSelect();
        if (select instanceof PlainSelect) {
            this.processInsertSelect(select, (String) obj);
        } else if (insert.getValues() != null) {
            // fixed github pull/295
            Values values = insert.getValues();
            ExpressionList<Expression> expressions = (ExpressionList<Expression>) values.getExpressions();
            if (expressions instanceof ParenthesedExpressionList) {
                expressions.addExpression(dataScope);
            } else {
                if (CollectionUtils.isNotEmpty(expressions)) {
                    //fix github issue 4998 jsqlparse 4.5 批量insert ItemsList不是MultiExpressionList 了，需要特殊处理
                    int len = expressions.size();
                    for (int i = 0; i < len; i++) {
                        Expression expression = expressions.get(i);
                        if (expression instanceof ParenthesedExpressionList) {
                            ((ParenthesedExpressionList<Expression>) expression).addExpression(dataScope);
                        } else {
                            expressions.add(dataScope);
                        }
                    }
                } else {
                    expressions.add(dataScope);
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
        process(SqlType.UPDATE, update.getTable(), index, t -> processUpdateSql(update, index, sql, obj));
    }

    /**
     * update 语句处理
     */
    protected void processUpdateSql(Update update, int index, String sql, Object obj) {
        final Table table = update.getTable();
        if (dataScopeHandler.ignoreTable(table.getName())) {
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
        if (dataScopeHandler.ignoreTable(delete.getTable().getName())) {
            // 过滤退出执行
            return;
        }
        delete.setWhere(this.andExpression(delete.getTable(), delete.getWhere(), (String) obj));
    }

    /**
     * 处理 insert into select
     * <p>
     * 进入这里表示需要 insert 的表启用了多租户,则 select 的表都启动了
     *
     * @param selectBody SelectBody
     */
    protected void processInsertSelect(Select selectBody, final String whereSegment) {
        if(selectBody instanceof PlainSelect plainSelect){
            FromItem fromItem = plainSelect.getFromItem();
            if (fromItem instanceof Table table) {
                // fixed gitee pulls/141 duplicate update
                processPlainSelect(plainSelect, whereSegment);
                appendSelectItem(plainSelect.getSelectItems(), table.getName());
            } else if (fromItem instanceof Select subSelect) {
                appendSelectItem(plainSelect.getSelectItems(), null);
                processInsertSelect(subSelect, whereSegment);
            }
        } else if(selectBody instanceof ParenthesedSelect parenthesedSelect){
            processInsertSelect(parenthesedSelect.getSelect(), whereSegment);
        }
    }

    /**
     * 追加 SelectItem
     *
     * @param selectItems SelectItem
     */
    protected void appendSelectItem(List<SelectItem<?>> selectItems, String tableName) {
        if (CollectionUtils.isEmpty(selectItems)) {
            return;
        }
        if (CollectionUtils.isSingle(selectItems)) {
            SelectItem<?> item = selectItems.getFirst();
            Expression expression = item.getExpression();
            if (expression instanceof AllColumns) {
                return;
            }
        }
        selectItems.add(new SelectItem<>(new Column(dataScopeHandler.getTableDataScopeColumn(tableName))));
    }

    @Override
    public void setProperties(Properties properties) {
        PropertyMapper.newInstance(properties).whenNotBlank("dataScopeLineHandler",
                ClassUtils::newInstance, this::setDataScopeHandler);
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
        Expression dataScopeCondition = dataScopeHandler.getDataScopeCondition(table);
        if (dataScopeCondition instanceof NullValue) {
            return new EqualsTo(new Column("1"), new LongValue(2));
        } else if (dataScopeCondition instanceof AllValue) {
            return null;
        } else {
            return dataScopeCondition;
        }

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
        if (!dataScopeHandler.isEnabled()) {
            return;
        }
        // 检查 非select语句 的表是否启用
        if (!dataScopeHandler.ignoreTable(table, sqlType)) {
            return;
        }
        // 检查是否使用 sql 解析模式
        try {
            if (SqlParserUtils.onSqlParser(sqlType)) {
                if (SqlParserUtils.ignoreProcess()) {
                    return;
                }
                // 当前租户状态为 all，但是当前用户只能访问到被允许的租户，这种情况下，方法不会直接return掉，还是需要进行下一步判断
                if (SqlParserUtils.dataScopeEnabled()) {
                    // 设置当前 sql执行的数据权限类型
                    SqlParserContext.setCurrentSqlDataScopeTypeByParserMode();
                }
            } else {
                // 非注解，使用 orm-context 中的信息
                // 检查是否为操作所有租户且用户可访问所有租户
                if (TenantContextUtils.isOperateAllTenant()) {
                    return;
                }

                SqlParserContext.setCurrentSqlDataScopeTypeByContext();
            }
            consumer.accept(index);
        } finally {
            // 清除信息
            SqlParserContext.clearCurrentSqlDataScopeType();
        }
    }

}
