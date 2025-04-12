package com.bitprogress.ormparser.Service.impl;

import com.bitprogress.exception.util.Assert;
import com.bitprogress.ormcontext.service.TenantContextService;
import com.bitprogress.ormmodel.enums.QueryType;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.enums.TenantType;
import com.bitprogress.ormmodel.info.parser.UserTenantInfo;
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
     * 设置当前数据范围类型
     *
     * @param sqlType sql类型
     * @return 是否设置成功
     */
    @Override
    public Boolean setCurrentConditionType(SqlType sqlType) {
        return tenantContextService.setCurrentConditionTypeBySqlType(sqlType);
    }

    /**
     * 清除当前数据范围类型
     */
    @Override
    public void clearCurrentConditionType() {
        tenantContextService.clearCurrentConditionType();
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
        TenantType conditionType = tenantContextService.getCurrentConditionType();
        Long tenantId = userTenantInfo.getTenantId();
        boolean hasTenant = Objects.nonNull(tenantId);
        if (Objects.isNull(conditionType) || TenantType.CURRENT.equals(conditionType)) {
            query.setIsQueryAll(false);
            query.setIsNotNeedQuery(!hasTenant);
            if (hasTenant) {
                query.setQueryType(QueryType.EQUAL);
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
                    query.setQueryType(QueryType.EQUAL);
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
                        query.setQueryType(QueryType.IN);
                    }
                }
            }
            default -> {
                query.setIsQueryAll(false);
                query.setIsNotNeedQuery(!hasTenant);
                if (hasTenant) {
                    query.setQueryType(QueryType.EQUAL);
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
        TenantType conditionType = tenantContextService.getCurrentConditionType();
        return TenantType.OPERATE.equals(conditionType) ? userInfo.getOperateTenantId() : userInfo.getTenantId();
    }

}
