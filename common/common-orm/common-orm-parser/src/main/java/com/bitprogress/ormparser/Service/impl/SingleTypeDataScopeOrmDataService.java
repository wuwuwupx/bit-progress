package com.bitprogress.ormparser.Service.impl;

import com.bitprogress.ormcontext.service.impl.SingleTypeDataScopeContextService;
import com.bitprogress.ormmodel.info.user.SingleTypeDataScopeInfo;
import com.bitprogress.ormmodel.enums.DataScopeType;
import com.bitprogress.ormmodel.enums.SqlOperatorType;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.query.DataScopeQuery;
import com.bitprogress.ormparser.Service.DataScopeOrmDataService;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.DataScopeUtils;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class SingleTypeDataScopeOrmDataService implements DataScopeOrmDataService {

    private final SingleTypeDataScopeContextService dataScopeContextService;

    /**
     * 设置当前数据范围类型
     *
     * @param sqlType sql类型
     * @return 是否设置成功
     */
    @Override
    public Boolean setCurrentConditionType(SqlType sqlType) {
        return dataScopeContextService.setCurrentConditionTypeBySqlType(sqlType);
    }

    /**
     * 清除当前数据范围类型
     */
    @Override
    public void clearCurrentConditionType() {
        dataScopeContextService.clearCurrentConditionType();
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
        DataScopeType dataScopeType = dataScopeContextService.getCurrentConditionType();
        SingleTypeDataScopeInfo dataScopeInfo = dataScopeContextService.getUserInfo();
        if (Objects.isNull(dataScopeInfo)) {
            query.setIsNotNeedQuery(true);
            return query;
        }
        Long selfData = dataScopeInfo.getSelfData();
        Long ownedData = dataScopeInfo.getOwnedData();
        boolean hasSelf = Objects.nonNull(selfData);
        boolean hasOwned = Objects.nonNull(ownedData);
        // 设置拥有数据和自身数据
        query.setIsQueryOwned(hasOwned);
        query.setIsQuerySelf(hasSelf);
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
            query.setIsQueryLimit(false);
            query.setIsQueryBelong(false);
            return query;
        }
        if (DataScopeType.ALL.equals(dataScopeType)) {
            query.setIsNotNeedQuery(false);
            query.setIsQueryAll(true);
            return query;
        }
        query.setIsQueryAll(false);
        Set<String> managedDataScopes = dataScopeInfo.getManagedDataScopes();
        Set<String> belongDataScopes = dataScopeInfo.getBelongDataScopes();
        boolean hasManaged = CollectionUtils.isNotEmpty(managedDataScopes);
        boolean hasBelong = CollectionUtils.isNotEmpty(belongDataScopes);
        switch (dataScopeType) {
            case BELONG_LEVEL_CURRENT, BELONG_LEVEL -> {
                query.setIsNotNeedQuery(!hasBelong && !hasOwned && !hasSelf);
                query.setIsQueryLimit(false);
                query.setIsQueryBelong(hasBelong);
                if (hasBelong) {
                    Set<String> finalBelongDataScopes = DataScopeType.BELONG_LEVEL.equals(dataScopeType)
                            ? DataScopeUtils.compressDataScopes(belongDataScopes)
                            : belongDataScopes;
                    SqlOperatorType sqlOperatorType = DataScopeType.BELONG_LEVEL.equals(dataScopeType)
                            ? SqlOperatorType.LIKE
                            : CollectionUtils.isSingle(belongDataScopes) ? SqlOperatorType.EQUAL : SqlOperatorType.IN;
                    query.setBelongSqlOperatorType(sqlOperatorType);
                    query.setBelongDataScopes(finalBelongDataScopes);
                }
            }
            case MANAGED_LEVEL_CURRENT, MANAGED_LEVEL -> {
                query.setIsNotNeedQuery(!hasManaged && !hasOwned && !hasSelf);
                query.setIsQueryLimit(hasManaged);
                query.setIsQueryBelong(false);
                if (hasManaged) {
                    Set<String> finalManagedDataScopes = DataScopeType.MANAGED_LEVEL.equals(dataScopeType)
                            ? DataScopeUtils.compressDataScopes(managedDataScopes)
                            : managedDataScopes;
                    SqlOperatorType sqlOperatorType = DataScopeType.MANAGED_LEVEL.equals(dataScopeType)
                            ? SqlOperatorType.LIKE
                            : CollectionUtils.isSingle(managedDataScopes) ? SqlOperatorType.EQUAL : SqlOperatorType.IN;
                    query.setLimitSqlOperatorType(sqlOperatorType);
                    query.setDataScopes(finalManagedDataScopes);
                }
            }
            case BELONG_LEVEL_MANAGED_CURRENT, MANAGED_LEVEL_BELONG_CURRENT -> {
                query.setIsNotNeedQuery(!hasManaged && !hasBelong && !hasOwned && !hasSelf);
                query.setIsQueryLimit(hasManaged);
                query.setIsQueryBelong(hasBelong);
                if (hasManaged) {
                    SqlOperatorType sqlOperatorType = DataScopeType.MANAGED_LEVEL_BELONG_CURRENT.equals(dataScopeType)
                            ? SqlOperatorType.LIKE
                            : CollectionUtils.isSingle(managedDataScopes) ? SqlOperatorType.EQUAL : SqlOperatorType.IN;
                    Set<String> dataScopes = DataScopeType.MANAGED_LEVEL_BELONG_CURRENT.equals(dataScopeType)
                            ? DataScopeUtils.compressDataScopes(managedDataScopes)
                            : managedDataScopes;
                    query.setLimitSqlOperatorType(sqlOperatorType);
                    query.setDataScopes(dataScopes);
                }
                if (hasBelong) {
                    SqlOperatorType sqlOperatorType = DataScopeType.BELONG_LEVEL_MANAGED_CURRENT.equals(dataScopeType)
                            ? SqlOperatorType.LIKE
                            : CollectionUtils.isSingle(managedDataScopes) ? SqlOperatorType.EQUAL : SqlOperatorType.IN;
                    Set<String> dataScopes = DataScopeType.BELONG_LEVEL_MANAGED_CURRENT.equals(dataScopeType)
                            ? DataScopeUtils.compressDataScopes(managedDataScopes)
                            : managedDataScopes;
                    query.setBelongSqlOperatorType(sqlOperatorType);
                    query.setBelongDataScopes(dataScopes);
                }
            }
            case COMPOSITE_LEVEL_CURRENT, COMPOSITE_LEVEL -> {
                boolean hasComposite = hasManaged || hasBelong;
                query.setIsNotNeedQuery(!hasComposite && !hasOwned && !hasSelf);
                query.setIsQueryLimit(hasComposite);
                query.setIsQueryBelong(false);
                if (hasComposite) {
                    Set<String> compositeDataScopes = DataScopeType.COMPOSITE_LEVEL.equals(dataScopeType)
                            ? DataScopeUtils.compressDataScopes(managedDataScopes, belongDataScopes)
                            : CollectionUtils.newSet(managedDataScopes, belongDataScopes);
                    SqlOperatorType sqlOperatorType = DataScopeType.COMPOSITE_LEVEL.equals(dataScopeType)
                            ? SqlOperatorType.LIKE
                            : CollectionUtils.isSingle(compositeDataScopes) ? SqlOperatorType.EQUAL : SqlOperatorType.IN;
                    query.setLimitSqlOperatorType(sqlOperatorType);
                    query.setDataScopes(compositeDataScopes);
                }
            }
            // 默认情况，仅匹配自身数据
            default -> {
                query.setIsNotNeedQuery(!hasOwned && !hasSelf);
                query.setIsQueryLimit(false);
                query.setIsQueryBelong(false);
                return query;
            }
        }
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
    public Long getOwnedData() {
        SingleTypeDataScopeInfo dataScopeInfo = dataScopeContextService.getUserInfo();
        return Objects.nonNull(dataScopeInfo) ? dataScopeInfo.getOwnedData() : null;
    }

    /**
     * 获取当前用户标识自身数据的值
     *
     * @return 用户标识自身数据的值
     */
    @Override
    public Long getSelfData() {
        SingleTypeDataScopeInfo dataScopeInfo = dataScopeContextService.getUserInfo();
        return Objects.nonNull(dataScopeInfo) ? dataScopeInfo.getSelfData() : null;
    }

}
