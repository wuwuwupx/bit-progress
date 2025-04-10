package com.bitprogress.ormcontext.service.impl;

import com.bitprogress.ormcontext.info.SingleTypeDataScopeInfo;
import com.bitprogress.ormcontext.query.DataScopeQuery;
import com.bitprogress.ormcontext.service.DataScopeQueryService;
import com.bitprogress.ormmodel.enums.DataScopeType;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class SingleTypeDataScopeQueryService implements DataScopeQueryService {

    private final SingleTypeDataScopeContextService dataScopeContextService;

    /**
     * 获取数据范围查询信息
     * 针对于数据查询而言
     *
     * @return 数据范围查询信息
     */
    @Override
    public DataScopeQuery getDataScopeQuery() {
        DataScopeQuery query = new DataScopeQuery();
        DataScopeType dataScopeType = dataScopeContextService.getCurrentDataScopeType();
        SingleTypeDataScopeInfo dataScopeInfo = dataScopeContextService.getDataScopeInfo();
        Set<String> belongDataScopes = dataScopeInfo.getBelongDataScopes();
        boolean isNeedQueryBelong = CollectionUtils.isNotEmpty(belongDataScopes);
        if (Objects.isNull(dataScopeType)) {
        }
        return null;
    }

}
