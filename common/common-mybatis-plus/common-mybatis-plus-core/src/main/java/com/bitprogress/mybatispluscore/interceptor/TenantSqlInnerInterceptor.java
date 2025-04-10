package com.bitprogress.mybatispluscore.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.bitprogress.basecontext.context.DispatcherContext;
import com.bitprogress.mybatispluscore.handler.TenantIdLineHandler;
import com.bitprogress.ormcontext.utils.TenantContextUtils;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.enums.TenantType;
import com.bitprogress.ormparser.context.SqlParserContext;
import com.bitprogress.ormparser.util.SqlParserUtils;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * 租户拦截器
 */
@AllArgsConstructor
public class TenantSqlInnerInterceptor extends TenantLineInnerInterceptor {

    private final TenantIdLineHandler tenantIdLineHandler;

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
        process(SqlType.INSERT, insert.getTable(), index, t -> super.processInsert(insert, t, sql, obj));
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
        boolean tenantEnabled = Objects.nonNull(tenantType) && !tenantIdLineHandler.ignoreTable(table.getName());
        if (!tenantEnabled) {
            return null;
        }
        Column tenantIdColumn = getAliasTenantIdColumn(table);
        Expression tenantId = tenantIdLineHandler.getTenantId();
        if (tenantId instanceof NullValue) {
            // 为空则表示没有可查询数据，当前表不需要再匹配
            return new EqualsTo(new Column("1"), new LongValue(2));
        } else {
            return TenantType.ALL.equals(tenantType)
                    ? new InExpression(tenantIdColumn, tenantId)
                    : new EqualsTo(tenantIdColumn, tenantId);
        }
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
        if (!isTenantEnabled) {
            return;
        }
        // 检查 非select语句 的表是否启用
        boolean isTableTenantEnabled = !tenantIdLineHandler.ignoreTable(table, sqlType);
        if (!isTableTenantEnabled) {
            return;
        }
        // 检查是否使用 sql 解析模式
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
                if (!SqlParserUtils.tenantEnabled()) {
                    return;
                }
                // 设置当前 sql执行的租户类型
                SqlParserContext.setCurrentSqlTenantTypeByParserMode();
            } else {
                // 非注解，使用 orm-context 中的信息
                // 检查是否为操作所有租户且用户可访问所有租户
                if (TenantContextUtils.isOperateAllTenant()) {
                    return;
                }
                SqlParserContext.setCurrentSqlTenantTypeByContext();
            }
            consumer.accept(index);
        } finally {
            // 清除信息
            SqlParserContext.clearCurrentSqlTenantType();
        }
    }

}
