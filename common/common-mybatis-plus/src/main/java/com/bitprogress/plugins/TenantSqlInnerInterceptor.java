package com.bitprogress.plugins;

import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.bitprogress.annotation.SqlType;
import com.bitprogress.context.DispatcherContext;
import com.bitprogress.context.SqlParserContext;
import com.bitprogress.handler.TenantSqlHandler;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import java.util.function.Consumer;

/**
 * 先检查sql解析模式
 */
public class TenantSqlInnerInterceptor extends TenantLineInnerInterceptor {

    public TenantSqlInnerInterceptor(TenantSqlHandler tenantSqlHandler) {
        super(tenantSqlHandler);
    }

    /**
     * select 语句处理
     */
    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        process(SqlType.SELECT, index, t -> super.processSelect(select, t, sql, obj));
    }

    /**
     * insert 语句处理
     */
    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        process(SqlType.INSERT, index, t -> super.processInsert(insert, t, sql, obj));
    }

    /**
     * update 语句处理
     */
    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        process(SqlType.UPDATE, index, t -> super.processUpdate(update, t, sql, obj));
    }

    /**
     * delete 语句处理
     */
    @Override
    protected void processDelete(Delete delete, int index, String sql, Object obj) {
        process(SqlType.DELETE, index, t -> super.processDelete(delete, t, sql, obj));
    }

    /**
     * sql 语句处理
     *
     * @param sqlType  sql类型
     * @param index    索引
     * @param consumer sql方法
     */
    private void process(SqlType sqlType, Integer index, Consumer<Integer> consumer) {
        // 检查是否系统调度，系统调度线程没有上下文信息，不进行sql处理
        if (DispatcherContext.isSystemSchedule()) {
            return;
        }
        // 检查是否使用 sql 解析模式
        if (SqlParserContext.onSqlParser(sqlType)) {
            if (SqlParserContext.ignoreProcess()) {
                return;
            }
            // 设置当前 sql执行的租户类型
            SqlParserContext.setCurrentSqlTenantType();
        }
        try {
            consumer.accept(index);
        } finally {
            // 清除租户类型
            SqlParserContext.clearCurrentSqlTenantType();
        }
    }

}
