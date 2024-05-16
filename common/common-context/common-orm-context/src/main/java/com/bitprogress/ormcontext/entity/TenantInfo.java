package com.bitprogress.ormcontext.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantInfo {

    /**
     * 当前租户ID
     */
    private Long tenantId;

    /**
     * 操作租户ID
     */
    private Long operateTenantId;

}
