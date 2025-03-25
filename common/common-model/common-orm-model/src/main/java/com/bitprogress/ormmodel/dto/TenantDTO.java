package com.bitprogress.ormmodel.dto;

import com.bitprogress.basemodel.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * Tenant Data Transfer Object
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TenantDTO extends DTO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * tenantId
     */
    private Long tenantId;

}
