package com.bitprogress.mybatispluscore.interceptor;

import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.BaseMultiTableInnerInterceptor;
import com.baomidou.mybatisplus.extension.toolkit.PropertyMapper;
import com.bitprogress.mybatispluscore.handler.TenantHandler;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import lombok.Setter;
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

import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * 租户拦截器
 */
@Setter
@AllArgsConstructor
public class TenantSqlInnerInterceptor extends SqlInnerInterceptor {

    private TenantHandler tenantHandler;

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        process(SqlType.SELECT, null, index, t -> processSelectSql(select, index, sql, obj), tenantHandler);
    }

    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        process(SqlType.INSERT, insert.getTable(), index, t -> processInsertSql(insert, index, sql, obj), tenantHandler);
    }

    protected void processInsertSql(Insert insert, int index, String sql, Object obj) {
        String tableName = insert.getTable().getName();
        if (tenantHandler.ignoreTable(tableName)) {
            // 过滤退出执行
            return;
        }
        List<Column> columns = insert.getColumns();
        if (CollectionUtils.isEmpty(columns)) {
            // 针对不给列名的insert 不处理
            return;
        }
        String column = tenantHandler.getTenantIdColumn();
        Expression tenantId = tenantHandler.getTenantId();
        if (!tenantHandler.enableInsertColumn(columns, column)) {
            return;
        }

        columns.add(new Column(column));
        List<UpdateSet> duplicateUpdateColumns = insert.getDuplicateUpdateSets();
        if (CollectionUtils.isNotEmpty(duplicateUpdateColumns)) {
            duplicateUpdateColumns.add(new UpdateSet(new Column(column), tenantId));
        }

        Select select = insert.getSelect();
        if (select instanceof PlainSelect) {
            this.processInsertSelect(select, (String) obj);
        } else if (insert.getValues() != null) {
            // fixed github pull/295
            Values values = insert.getValues();
            ExpressionList<Expression> expressions = (ExpressionList<Expression>) values.getExpressions();
            if (expressions instanceof ParenthesedExpressionList) {
                expressions.addExpression(tenantId);
            } else {
                if (CollectionUtils.isNotEmpty(expressions)) {
                    //fix github issue 4998 jsqlparse 4.5 批量insert ItemsList不是MultiExpressionList 了，需要特殊处理
                    int len = expressions.size();
                    for (int i = 0; i < len; i++) {
                        Expression expression = expressions.get(i);
                        if (expression instanceof ParenthesedExpressionList expressionList) {
                            expressionList.addExpression(tenantId);
                        } else {
                            expressions.addExpression(tenantId);
                        }
                    }
                } else {
                    expressions.addExpression(tenantId);
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
        selectItems.add(new SelectItem<>(new Column(tenantHandler.getTenantIdColumn())));
    }

    /**
     * update 语句处理
     */
    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        Consumer<Integer> consumer = t -> processUpdateSql(update, index, sql, obj, tenantHandler);
        process(SqlType.UPDATE, update.getTable(), index, consumer, tenantHandler);
    }

    /**
     * delete 语句处理
     */
    @Override
    protected void processDelete(Delete delete, int index, String sql, Object obj) {
        Consumer<Integer> consumer = t -> processDeleteSql(delete, index, sql, obj, tenantHandler);
        process(SqlType.DELETE, delete.getTable(), index, consumer, tenantHandler);
    }

    @Override
    public void setProperties(Properties properties) {
        PropertyMapper.newInstance(properties).whenNotBlank("tenantHandler",
                ClassUtils::newInstance, this::setTenantHandler);
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
        Expression dataScopeCondition = tenantHandler.getCondition(table);
        if (dataScopeCondition instanceof NullValue) {
            return new EqualsTo(new Column("1"), new LongValue(2));
        } else if (dataScopeCondition instanceof AllValue) {
            return null;
        } else {
            return dataScopeCondition;
        }

    }

}
