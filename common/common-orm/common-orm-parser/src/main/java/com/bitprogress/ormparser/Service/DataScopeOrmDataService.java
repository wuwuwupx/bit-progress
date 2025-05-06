package com.bitprogress.ormparser.Service;

import com.bitprogress.ormmodel.query.DataScopeQuery;

/**
 * 组织数据范围查询的服务
 */
public interface DataScopeOrmDataService extends OrmDataService<DataScopeQuery> {

    /**
     * 获取当前用户的数据范围
     *
     * @return 数据范围
     */
    String getDataScope();

    /**
     * 获取当前用户标识拥有数据的值
     *
     * @return 用户标识拥有数据的值
     */
    String getOwnedData();

    /**
     * 获取当前用户标识自身数据的值
     *
     * @return 用户标识自身数据的值
     */
    String getSelfData();

}
