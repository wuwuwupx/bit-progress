package com.bitprogress.daomodel.dto;

import com.bitprogress.basemodel.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Tenant Data Transfer Object
 */
@EqualsAndHashCode(callSuper = false)
@Data
public abstract class TenantDTO extends DTO {

    /**
     * tenantId
     */
    private Long tenantId;

}
