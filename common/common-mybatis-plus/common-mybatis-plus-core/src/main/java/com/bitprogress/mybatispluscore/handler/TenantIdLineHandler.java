package com.bitprogress.mybatispluscore.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.bitprogress.mybatispluscore.properties.TenantProperties;
import com.bitprogress.ormcontext.utils.TenantContextUtils;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.enums.TenantType;
import com.bitprogress.ormparser.context.SqlParserContext;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.schema.Table;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class TenantIdLineHandler implements TenantLineHandler {

    private final TenantProperties tenantProperties;

    /**
     * 是否启用
     *
     * @return 是否启用
     */
    public boolean isEnabled() {
        return tenantProperties.getEnabled();
    }

    /**
     * 获取租户 ID 值表达式，只支持单个 ID 值
     *
     * @return 租户 ID 值表达式
     */
    public Expression getInsertTenantId() {
        TenantType tenantType = SqlParserContext.getCurrentSqlTenantType();
        Long tenantId = TenantType.OPERATE.equals(tenantType)
                ? TenantContextUtils.getOperateTenantId()
                : TenantContextUtils.getTenantId();
        return new LongValue(tenantId);
    }

    /**
     * 获取租户 ID
     *
     * @return 租户 ID 值表达式
     */
    @Override
    public Expression getTenantId() {
        TenantType tenantType = SqlParserContext.getCurrentSqlTenantType();
        switch(tenantType) {
            case ALL -> {
                // 对于没有可操作租户的用户，应该不返回任何数据
                Set<Long> operateTenantIds = TenantContextUtils.getOperateTenantIds();
                if (CollectionUtils.isEmpty(operateTenantIds)) {
                    return new NullValue();
                }
                return new ParenthesedExpressionList<>(CollectionUtils.toList(operateTenantIds, LongValue::new));
            }
            case CURRENT -> {
                return new LongValue(TenantContextUtils.getTenantIdOrDefault());
            }
            case OPERATE -> {
                return new LongValue(TenantContextUtils.getOperateTenantIdOrDefault());
            }
            default -> {
                return new NullValue();
            }
        }
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

    /**
     * 根据 表名 和 sql类型 判断是否忽略拼接多租户条件
     * 因为select不能直接获取 table
     * <p>
     * 默认都要进行解析并拼接多租户条件
     * - 启用表列表不为空，则只需要判断表名是否启用
     * - 启用表列表为空，则判断是否在白名单中
     *
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
     */
    public boolean ignoreTable(Table table, SqlType sqlType) {
        if (SqlType.SELECT.equals(sqlType)) {
            return false;
        }
        String tableName = table.getName();
        List<String> enableTables = tenantProperties.getEnableTables();
        List<String> ignoreTables = tenantProperties.getIgnoreTables();
        return CollectionUtils.isNotEmpty(enableTables)
                ? CollectionUtils.notContains(enableTables, tableName)
                : CollectionUtils.contains(ignoreTables, tableName);
    }

}
