package com.bitprogress.ormparser.Service;

import com.bitprogress.ormmodel.query.TenantQuery;

public interface TenantOrmDataService extends OrmDataService<TenantQuery> {

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    Long getTenantId();

}
