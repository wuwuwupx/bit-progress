package com.bitprogress.daomodel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 带租户的数据库实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class TenantEntity extends Entity {

    /**
     * 租户ID
     */
    private Long tenantId;

}
