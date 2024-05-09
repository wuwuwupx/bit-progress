package com.bitprogress.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.bitprogress.annotation.TenantType;
import com.bitprogress.context.SqlParserContext;
import com.bitprogress.context.TenantContext;
import com.bitprogress.properties.TenantProperties;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

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
     *
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
     */
    @Override
    public boolean ignoreTable(String tableName) {
        // 忽略配置的表
        if (tenantProperties.getIgnoreTables().contains(tableName)) {
            return true;
        }
        return TenantLineHandler.super.ignoreTable(tableName);
    }
}
