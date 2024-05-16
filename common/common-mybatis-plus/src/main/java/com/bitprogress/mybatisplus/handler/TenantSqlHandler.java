package com.bitprogress.mybatisplus.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.bitprogress.mybatisplus.annotation.TenantType;
import com.bitprogress.mybatisplus.context.SqlParserContext;
import com.bitprogress.ormcontext.context.TenantContext;
import com.bitprogress.mybatisplus.properties.TenantProperties;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import java.util.List;

@AllArgsConstructor
public class TenantSqlHandler implements TenantLineHandler {

    private final TenantProperties tenantProperties;

    /**
     * 获取租户 ID 值表达式，只支持单个 ID 值
     *
     * @return 租户 ID 值表达式
     */
    @Override
    public Expression getTenantId() {
        TenantType tenantType = SqlParserContext.getCurrentSqlTenantType();
        Long tenantId = TenantType.OPERATE.equals(tenantType)
                ? TenantContext.getOperateTenantId()
                : TenantContext.getTenantId();
        return new LongValue(tenantId);
    }

    /**
     * 根据表名判断是否忽略拼接多租户条件
     * <p>
     * 默认都要进行解析并拼接多租户条件
     * - 启用表列表不为空，则只需要判断表名是否启用
     * - 启用表列表为空，则判断是否在白名单中
     *
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
     */
    @Override
    public boolean ignoreTable(String tableName) {
        List<String> enableTables = tenantProperties.getEnableTables();
        List<String> ignoreTables = tenantProperties.getIgnoreTables();
        return CollectionUtils.isNotEmpty(enableTables)
                ? CollectionUtils.notContains(enableTables, tableName)
                : CollectionUtils.contains(ignoreTables, tableName);
    }
}
