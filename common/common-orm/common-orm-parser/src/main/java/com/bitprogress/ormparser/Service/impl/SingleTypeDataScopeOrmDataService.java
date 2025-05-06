package com.bitprogress.ormparser.Service.impl;

import com.bitprogress.ormcontext.service.impl.SingleTypeDataScopeContextService;
import com.bitprogress.ormmodel.enums.DataScopeType;
import com.bitprogress.ormmodel.enums.QueryMode;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.info.user.SingleTypeDataScopeInfo;
import com.bitprogress.ormmodel.query.DataScopeQuery;
import com.bitprogress.ormparser.Service.DataScopeOrmDataService;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class SingleTypeDataScopeOrmDataService implements DataScopeOrmDataService {

    private final SingleTypeDataScopeContextService dataScopeContextService;

    /**
     * 缓存前一sql上下文
     */
    @Override
    public void cachePreSqlContext() {
        dataScopeContextService.cachePreSqlContext();
    }

    /**
     * 设置当前sql上下文
     *
     * @param sqlType sql类型
     * @return 是否设置成功
     */
    @Override
    public boolean setCurrentSqlContextBySqlType(SqlType sqlType) {
        return dataScopeContextService.setCurrentSqlContextBySqlType(sqlType);
    }

    /**
     * 清除sql上下文
     */
    @Override
    public void clearCurrentSqlContext() {
        dataScopeContextService.clearCurrentSqlContext();
    }

    /**
     * 恢复前一sql上下文
     */
    @Override
    public void restorePreSqlContext() {
        dataScopeContextService.restorePreSqlContext();
    }

    /**
     * 获取数据范围查询信息
     * 针对于数据查询而言
     *
     * @return 数据范围查询信息
     */
    @Override
    public DataScopeQuery getConditionQuery() {
        DataScopeQuery query = new DataScopeQuery();
        DataScopeType dataScopeType = dataScopeContextService.getCurrentSqlDataScopeType();
        SingleTypeDataScopeInfo dataScopeInfo = dataScopeContextService.getUserInfo();
        if (Objects.isNull(dataScopeInfo)) {
            query.setIsNotNeedQuery(true);
            return query;
        }
        String selfData = dataScopeInfo.getSelfData();
        String ownedData = dataScopeInfo.getOwnedData();
        boolean hasSelf = Objects.nonNull(selfData);
        boolean hasOwned = Objects.nonNull(ownedData);
        // 设置拥有数据和自身数据
        query.setHasOwnedQuery(hasOwned);
        query.setHasSelfQuery(hasSelf);
        if (hasOwned) {
            query.setOwnedData(ownedData);
        }
        if (hasSelf) {
            query.setSelfData(selfData);
        }
        // 没有类型，匹配自身数据
        if (Objects.isNull(dataScopeType) || DataScopeType.SELF.equals(dataScopeType)) {
            query.setIsNotNeedQuery(!hasOwned && !hasSelf);
            query.setIsQueryAll(false);
            query.setHasRangeQuery(false);
            query.setHasExactQuery(false);
            return query;
        }
        if (DataScopeType.ALL.equals(dataScopeType)) {
            query.setIsNotNeedQuery(false);
            query.setIsQueryAll(true);
            return query;
        }
        QueryMode queryMode = dataScopeContextService.getCurrentSqlQueryMode();
        query.setQueryMode(queryMode);
        query.setIsQueryAll(false);
        Set<String> managedDataScopes = dataScopeInfo.getManagedDataScopes();
        Set<String> belongDataScopes = dataScopeInfo.getBelongDataScopes();
        boolean hasManaged = CollectionUtils.isNotEmpty(managedDataScopes);
        boolean hasBelong = CollectionUtils.isNotEmpty(belongDataScopes);

        boolean isNotNeedQuery;
        boolean hasRangeQuery;
        boolean hasExactQuery;
        switch (dataScopeType) {
            case BELONG_LEVEL_CURRENT, MANAGED_LEVEL_CURRENT, COMPOSITE_LEVEL_CURRENT -> {
                hasRangeQuery = false;
                Set<String> dataScopes = DataScopeType.COMPOSITE_LEVEL_CURRENT.equals(dataScopeType)
                        ? CollectionUtils.newSet(managedDataScopes, belongDataScopes)
                        : DataScopeType.BELONG_LEVEL_CURRENT.equals(dataScopeType) ? belongDataScopes : managedDataScopes;
                hasExactQuery = CollectionUtils.isNotEmpty(dataScopes);
                isNotNeedQuery = !hasExactQuery && !hasOwned && !hasSelf;
                if (hasExactQuery) {
                    query.setExactDataScopes(dataScopes);
                }
            }
            case BELONG_LEVEL, MANAGED_LEVEL, COMPOSITE_LEVEL -> {
                hasExactQuery = false;
                Set<String> dataScopes = DataScopeType.COMPOSITE_LEVEL.equals(dataScopeType)
                        ? CollectionUtils.newSet(managedDataScopes, belongDataScopes)
                        : DataScopeType.BELONG_LEVEL.equals(dataScopeType) ? belongDataScopes : managedDataScopes;
                hasRangeQuery = CollectionUtils.isNotEmpty(dataScopes);
                isNotNeedQuery = !hasRangeQuery && !hasOwned && !hasSelf;
                if (hasRangeQuery) {
                    query.setRangeDataScopes(dataScopes);
                }
            }
            case BELONG_LEVEL_MANAGED_CURRENT -> {
                isNotNeedQuery = !hasManaged && !hasBelong && !hasOwned && !hasSelf;
                hasRangeQuery = hasBelong;
                hasExactQuery = hasManaged;
                if (hasManaged) {
                    query.setExactDataScopes(managedDataScopes);
                }
                if (hasBelong) {
                    query.setRangeDataScopes(belongDataScopes);
                }
            }
            case MANAGED_LEVEL_BELONG_CURRENT -> {
                isNotNeedQuery = !hasManaged && !hasBelong && !hasOwned && !hasSelf;
                hasRangeQuery = hasManaged;
                hasExactQuery = hasBelong;
                if (hasManaged) {
                    query.setRangeDataScopes(managedDataScopes);
                }
                if (hasBelong) {
                    query.setExactDataScopes(belongDataScopes);
                }
            }
            // 默认情况，仅匹配自身数据
            default -> {
                isNotNeedQuery = !hasOwned && !hasSelf;
                hasRangeQuery = false;
                hasExactQuery = false;
            }
        }
        query.setIsNotNeedQuery(isNotNeedQuery);
        query.setHasRangeQuery(hasRangeQuery);
        query.setHasExactQuery(hasExactQuery);
        return query;
    }

    /**
     * 获取当前用户的数据范围
     *
     * @return 数据范围
     */
    @Override
    public String getDataScope() {
        SingleTypeDataScopeInfo dataScopeInfo = dataScopeContextService.getUserInfo();
        return Objects.nonNull(dataScopeInfo) ? dataScopeInfo.getDataScope() : null;
    }

    /**
     * 获取当前用户标识拥有数据的值
     *
     * @return 用户标识拥有数据的值
     */
    @Override
    public String getOwnedData() {
        SingleTypeDataScopeInfo dataScopeInfo = dataScopeContextService.getUserInfo();
        return Objects.nonNull(dataScopeInfo) ? dataScopeInfo.getOwnedData() : null;
    }

    /**
     * 获取当前用户标识自身数据的值
     *
     * @return 用户标识自身数据的值
     */
    @Override
    public String getSelfData() {
        SingleTypeDataScopeInfo dataScopeInfo = dataScopeContextService.getUserInfo();
        return Objects.nonNull(dataScopeInfo) ? dataScopeInfo.getSelfData() : null;
    }

}
