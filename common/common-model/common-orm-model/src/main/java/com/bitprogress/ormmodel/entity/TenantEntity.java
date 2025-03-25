package com.bitprogress.ormmodel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 带租户的数据库实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class TenantEntity extends Entity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 租户ID
     */
    private Long tenantId;

}
