package com.bitprogress.mybatispluscore.interceptor;

import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.BaseMultiTableInnerInterceptor;
import com.baomidou.mybatisplus.extension.toolkit.PropertyMapper;
import com.bitprogress.mybatispluscore.handler.DataScopeHandler;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import lombok.Setter;
import net.sf.jsqlparser.expression.*;
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
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * 数据权限拦截器
 */
@Setter
@AllArgsConstructor
public class DataScopeSqlInnerInterceptor extends SqlInnerInterceptor {

    private DataScopeHandler dataScopeHandler;

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        process(SqlType.SELECT, null, index, t -> processSelectSql(select, index, sql, obj), dataScopeHandler);
    }

    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        Table table = insert.getTable();
        process(SqlType.INSERT, table, index, t -> processInsertSql(insert, index, sql, obj), dataScopeHandler);
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
        boolean enableDataScope = dataScopeHandler.enableInsertDataScope(tableName)
                && dataScopeHandler.enableInsertColumn(columns, dataScopeColumn);
        Expression dataScope = dataScopeHandler.getDataScope();

        String ownedColumn = dataScopeHandler.getTableOwnedColumn(tableName);
        boolean enableOwned = dataScopeHandler.enableInsertOwned(tableName)
                && dataScopeHandler.enableInsertColumn(columns, ownedColumn);
        Expression ownedData = dataScopeHandler.getOwnedData();

        String selfColumn = dataScopeHandler.getTableSelfColumn(tableName);
        boolean enableSelf = dataScopeHandler.enableInsertSelf(tableName)
                && dataScopeHandler.enableInsertColumn(columns, selfColumn);
        Expression selfData = dataScopeHandler.getSelfData();

        if (!enableDataScope && !enableOwned && !enableSelf) {
            return;
        }

        if (enableDataScope) {
            columns.add(new Column(dataScopeColumn));
            List<UpdateSet> duplicateUpdateColumns = insert.getDuplicateUpdateSets();
            if (CollectionUtils.isNotEmpty(duplicateUpdateColumns)) {
                duplicateUpdateColumns.add(new UpdateSet(new Column(dataScopeColumn), dataScope));
            }
        }
        if (enableOwned) {
            columns.add(new Column(ownedColumn));
            List<UpdateSet> duplicateUpdateColumns = insert.getDuplicateUpdateSets();
            if (CollectionUtils.isNotEmpty(duplicateUpdateColumns)) {
                duplicateUpdateColumns.add(new UpdateSet(new Column(ownedColumn), ownedData));
            }
        }
        if (enableSelf) {
            columns.add(new Column(selfColumn));
            List<UpdateSet> duplicateUpdateColumns = insert.getDuplicateUpdateSets();
            if (CollectionUtils.isNotEmpty(duplicateUpdateColumns)) {
                duplicateUpdateColumns.add(new UpdateSet(new Column(selfColumn), selfData));
            }
        }

        Select select = insert.getSelect();
        if (select instanceof PlainSelect) {
            this.processInsertSelect(select, (String) obj, enableDataScope, enableOwned, enableSelf);
        } else if (insert.getValues() != null) {
            // fixed github pull/295
            Values values = insert.getValues();
            ExpressionList<Expression> expressions = (ExpressionList<Expression>) values.getExpressions();
            if (expressions instanceof ParenthesedExpressionList) {
                if (enableDataScope) {
                    expressions.addExpression(dataScope);
                }
                if (enableOwned) {
                    expressions.addExpression(ownedData);
                }
                if (enableSelf) {
                    expressions.addExpression(selfData);
                }
            } else {
                if (CollectionUtils.isNotEmpty(expressions)) {
                    //fix github issue 4998 jsqlparse 4.5 批量insert ItemsList不是MultiExpressionList 了，需要特殊处理
                    int len = expressions.size();
                    for (int i = 0; i < len; i++) {
                        Expression expression = expressions.get(i);
                        if (expression instanceof ParenthesedExpressionList expressionList) {
                            if (enableDataScope) {
                                expressionList.addExpression(dataScope);
                            }
                            if (enableOwned) {
                                expressionList.addExpression(ownedData);
                            }
                            if (enableSelf) {
                                expressionList.addExpression(selfData);
                            }
                        } else {
                            if (enableDataScope) {
                                expressions.addExpression(dataScope);
                            }
                            if (enableOwned) {
                                expressions.addExpression(ownedData);
                            }
                            if (enableSelf) {
                                expressions.addExpression(selfData);
                            }
                        }
                    }
                } else {
                    if (enableDataScope) {
                        expressions.addExpression(dataScope);
                    }
                    if (enableOwned) {
                        expressions.addExpression(ownedData);
                    }
                    if (enableSelf) {
                        expressions.addExpression(selfData);
                    }
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
     * <p>
     * select a.id, a.data from
     * (select id, data from table_a where tenant_id = ?) a
     *
     * @param selectBody SelectBody
     */
    protected Map<String, String> processInsertSelect(Select selectBody,
                                                      final String whereSegment,
                                                      boolean enableDataScope,
                                                      boolean enableOwned,
                                                      boolean enableSelf) {
        if (selectBody instanceof PlainSelect plainSelect) {
            FromItem fromItem = plainSelect.getFromItem();
            if (fromItem instanceof Table table) {
                // fixed gitee pulls/141 duplicate update
                processPlainSelect(plainSelect, whereSegment);
                return appendTableItem(plainSelect.getSelectItems(), table, enableDataScope, enableOwned, enableSelf);
            } else if (fromItem instanceof Select subSelect) {
                // 先递归处理底层的 select
                Map<String, String> infoMap = processInsertSelect(subSelect, whereSegment, enableDataScope,
                        enableOwned, enableSelf);
                appendSelectItem(plainSelect.getSelectItems(), infoMap, subSelect.getAlias(), enableDataScope,
                        enableOwned, enableSelf);
                return infoMap;
            }
        } else if (selectBody instanceof ParenthesedSelect parenthesedSelect) {
            Select select = parenthesedSelect.getSelect();
            return processInsertSelect(select, whereSegment, enableDataScope, enableOwned, enableSelf);
        }
        return CollectionUtils.emptyMap();
    }

    /**
     * 追加 SelectItem
     *
     * @param selectItems SelectItem
     */
    protected Map<String, String> appendTableItem(List<SelectItem<?>> selectItems,
                                                  Table table,
                                                  boolean enableDataScope,
                                                  boolean enableOwned,
                                                  boolean enableSelf) {
        if (CollectionUtils.isEmpty(selectItems)) {
            return CollectionUtils.emptyMap();
        }
        if (CollectionUtils.isSingle(selectItems)) {
            SelectItem<?> item = selectItems.getFirst();
            Expression expression = item.getExpression();
            if (expression instanceof AllColumns) {
                return CollectionUtils.emptyMap();
            }
        }
        Map<String, String> infoMap = CollectionUtils.emptyMap();
        Alias alias = table.getAlias();
        infoMap.put("aliasName", Objects.isNull(alias) ? null : alias.getName());
        if (enableDataScope) {
            String dataScopeColumnName = dataScopeHandler.getTableDataScopeColumn(table.getName());
            Column aliasColumn = dataScopeHandler.getAliasColumn(alias, dataScopeColumnName);
            selectItems.add(new SelectItem<>(aliasColumn));
            infoMap.put("dataScopeColumn", dataScopeColumnName);
        }
        if (enableOwned) {
            String ownedColumnName = dataScopeHandler.getTableOwnedColumn(table.getName());
            Column aliasColumn = dataScopeHandler.getAliasColumn(alias, ownedColumnName);
            selectItems.add(new SelectItem<>(aliasColumn));
            infoMap.put("ownedColumn", ownedColumnName);
        }
        if (enableSelf) {
            String selfColumnName = dataScopeHandler.getTableSelfColumn(table.getName());
            Column aliasColumn = dataScopeHandler.getAliasColumn(alias, selfColumnName);
            selectItems.add(new SelectItem<>(aliasColumn));
            infoMap.put("selfColumn", selfColumnName);
        }
        return infoMap;
    }

    /**
     * 追加 SelectItem
     *
     * @param selectItems SelectItem
     */
    protected void appendSelectItem(List<SelectItem<?>> selectItems,
                                    Map<String, String> infoMap,
                                    Alias alias,
                                    boolean enableDataScope,
                                    boolean enableOwned,
                                    boolean enableSelf) {
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
        infoMap.put("aliasName", Objects.isNull(alias) ? null : alias.getName());
        if (enableDataScope) {
            String dataScopeColumnName = infoMap.get("dataScopeColumn");
            Column aliasColumn = dataScopeHandler.getAliasColumn(alias, dataScopeColumnName);
            selectItems.add(new SelectItem<>(aliasColumn));
        }
        if (enableOwned) {
            String ownedColumnName = infoMap.get("ownedColumn");
            Column aliasColumn = dataScopeHandler.getAliasColumn(alias, ownedColumnName);
            selectItems.add(new SelectItem<>(aliasColumn));
        }
        if (enableSelf) {
            String selfColumnName = infoMap.get("selfColumn");
            Column aliasColumn = dataScopeHandler.getAliasColumn(alias, selfColumnName);
            selectItems.add(new SelectItem<>(aliasColumn));
        }
    }

    /**
     * update 语句处理
     */
    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        Table table = update.getTable();
        Consumer<Integer> consumer = t -> processUpdateSql(update, index, sql, obj, dataScopeHandler);
        process(SqlType.UPDATE, table, index, consumer, dataScopeHandler);
    }

    /**
     * delete 语句处理
     */
    @Override
    protected void processDelete(Delete delete, int index, String sql, Object obj) {
        Table table = delete.getTable();
        Consumer<Integer> consumer = t -> processDeleteSql(delete, index, sql, obj, dataScopeHandler);
        process(SqlType.DELETE, table, index, consumer, dataScopeHandler);
    }

    @Override
    public void setProperties(Properties properties) {
        PropertyMapper.newInstance(properties).whenNotBlank("dataScopeHandler",
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
        Expression dataScopeCondition = dataScopeHandler.getCondition(table);
        if (dataScopeCondition instanceof NullValue) {
            return new EqualsTo(new Column("1"), new LongValue(2));
        } else if (dataScopeCondition instanceof AllValue) {
            return null;
        } else {
            return dataScopeCondition;
        }

    }

}
