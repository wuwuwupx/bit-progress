package com.bitprogress.daomodel.dto;

import com.bitprogress.basemodel.dto.NameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * tenantId name
 * Data Transfer Object
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameTenantDTO extends NameDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * tenant id
     */
    private Long tenantId;

}
