package com.bitprogress.ormcontext.service;

import com.bitprogress.ormcontext.query.DataScopeQuery;

/**
 * 组织数据范围查询的服务
 */
public interface DataScopeQueryService {

    /**
     * 获取数据范围查询信息
     * 针对于数据查询而言
     *
     * @return 数据范围查询信息
     */
    DataScopeQuery getDataScopeQuery();

}
