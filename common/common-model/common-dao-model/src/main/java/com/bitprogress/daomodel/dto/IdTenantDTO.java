package com.bitprogress.daomodel.dto;

import com.bitprogress.basemodel.dto.IdDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * Tenant Data Transfer Object
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class IdTenantDTO extends IdDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * tenantId
     */
    private Long tenantId;

}
