package com.bitprogress.ormparser.Service.impl;

import com.bitprogress.exception.util.Assert;
import com.bitprogress.ormcontext.service.TenantContextService;
import com.bitprogress.ormmodel.enums.SqlOperatorType;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.enums.TenantType;
import com.bitprogress.ormmodel.info.user.UserTenantInfo;
import com.bitprogress.ormmodel.query.TenantQuery;
import com.bitprogress.ormparser.Service.TenantOrmDataService;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class TenantOrmDataServiceImpl implements TenantOrmDataService {

    private TenantContextService tenantContextService;

    /**
     * 缓存前一sql上下文
     */
    @Override
    public void cachePreSqlContext() {
        tenantContextService.cachePreSqlContext();
    }

    /**
     * 设置当前sql上下文
     *
     * @param sqlType sql类型
     * @return 是否设置成功
     */
    @Override
    public boolean setCurrentSqlContextBySqlType(SqlType sqlType) {
        return tenantContextService.setCurrentSqlContextBySqlType(sqlType);
    }

    /**
     * 清除sql上下文
     */
    @Override
    public void clearCurrentSqlContext() {
        tenantContextService.clearCurrentSqlContext();
    }

    /**
     * 恢复前一sql上下文
     */
    @Override
    public void restorePreSqlContext() {
        tenantContextService.restorePreSqlContext();
    }

    /**
     * 获取数据范围查询信息
     * 针对于数据查询而言
     *
     * @return 数据范围查询信息
     */
    @Override
    public TenantQuery getConditionQuery() {
        TenantQuery query = new TenantQuery();
        UserTenantInfo userTenantInfo = tenantContextService.getUserInfo();
        if (Objects.isNull(userTenantInfo)) {
            query.setIsNotNeedQuery(true);
            return query;
        }
        TenantType conditionType = tenantContextService.getCurrentSqlTenantType();
        Long tenantId = userTenantInfo.getTenantId();
        boolean hasTenant = Objects.nonNull(tenantId);
        if (Objects.isNull(conditionType) || TenantType.CURRENT.equals(conditionType)) {
            query.setIsQueryAll(false);
            query.setIsNotNeedQuery(!hasTenant);
            if (hasTenant) {
                query.setSqlOperatorType(SqlOperatorType.EQUAL);
                query.setTenantIds(CollectionUtils.asSet(tenantId));
            }
            return query;
        }
        switch (conditionType) {
            case OPERATE -> {
                Long operateTenantId = userTenantInfo.getOperateTenantId();
                boolean hasOperateTenant = Objects.nonNull(operateTenantId);
                query.setIsQueryAll(false);
                query.setIsNotNeedQuery(!hasOperateTenant);
                if (hasOperateTenant) {
                    query.setSqlOperatorType(SqlOperatorType.EQUAL);
                    query.setTenantIds(CollectionUtils.asSet(operateTenantId));
                }
            }
            case ALL -> {
                if (userTenantInfo.isCanOperateAllTenant()) {
                    query.setIsNotNeedQuery(false);
                    query.setIsQueryAll(true);
                } else {
                    query.setIsQueryAll(false);
                    Set<Long> operateTenantIds = userTenantInfo.getOperateTenantIds();
                    boolean hasOperateTenant = CollectionUtils.isNotEmpty(operateTenantIds);
                    query.setIsNotNeedQuery(!hasTenant && !hasOperateTenant);
                    if (hasTenant || hasOperateTenant) {
                        query.setTenantIds(CollectionUtils.newSet(operateTenantIds, tenantId));
                        query.setSqlOperatorType(SqlOperatorType.IN);
                    }
                }
            }
            default -> {
                query.setIsQueryAll(false);
                query.setIsNotNeedQuery(!hasTenant);
                if (hasTenant) {
                    query.setSqlOperatorType(SqlOperatorType.EQUAL);
                    query.setTenantIds(CollectionUtils.asSet(tenantId));
                }
            }
        }
        return query;
    }

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    @Override
    public Long getTenantId() {
        UserTenantInfo userInfo = tenantContextService.getUserInfo();
        Assert.notNull(userInfo, "获取用户租户信息失败");
        TenantType conditionType = tenantContextService.getCurrentSqlTenantType();
        return TenantType.OPERATE.equals(conditionType) ? userInfo.getOperateTenantId() : userInfo.getTenantId();
    }

}
