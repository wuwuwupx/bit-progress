package com.bitprogress.daomodel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * tenantId name
 * Data Transfer Object
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameTenantDTO extends TenantDTO {

    /**
     * name
     */
    private String name;

}
