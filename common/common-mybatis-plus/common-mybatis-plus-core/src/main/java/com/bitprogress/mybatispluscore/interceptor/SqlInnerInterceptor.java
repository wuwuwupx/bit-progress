package com.bitprogress.mybatispluscore.interceptor;

import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.BaseMultiTableInnerInterceptor;
import com.bitprogress.basecontext.context.DispatcherContext;
import com.bitprogress.mybatispluscore.handler.InterceptorHandler;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.util.CollectionUtils;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.WithItem;
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

public abstract class SqlInnerInterceptor extends BaseMultiTableInnerInterceptor {

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

    protected void processSelectSql(Select select, int index, String sql, Object obj) {
        final String whereSegment = (String) obj;
        processSelectBody(select, whereSegment);
        List<WithItem<?>> withItemsList = select.getWithItemsList();
        if (!CollectionUtils.isEmpty(withItemsList)) {
            withItemsList.forEach(withItem -> processSelectBody(withItem.getSelect(), whereSegment));
        }
    }

    /**
     * update 语句处理
     */
    protected void processUpdateSql(Update update,
                                    int index,
                                    String sql,
                                    Object obj,
                                    InterceptorHandler<?> interceptorHandler) {
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
    protected void processDeleteSql(Delete delete,
                                    int index,
                                    String sql,
                                    Object obj,
                                    InterceptorHandler<?> interceptorHandler) {
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
    protected void process(SqlType sqlType,
                           Table table,
                           Integer index,
                           Consumer<Integer> consumer,
                           InterceptorHandler<?> interceptorHandler) {
        // 检查是否无状态调度，无状态调度线程没有上下文信息，不进行sql处理
        if (DispatcherContext.isNoneStatusDispatch()) {
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
        // 检查是否使用 sql 解析模式
        try {
            if (!interceptorHandler.setCurrentConditionType(sqlType)) {
                consumer.accept(index);
            }
        } finally {
            // 清除信息
            interceptorHandler.clearCurrentConditionType();
        }
    }

}
