package com.bitprogress.ormcontext.entity;

import com.bitprogress.basemodel.Info;
import com.bitprogress.ormmodel.enums.TenantType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantInfo extends Info {

    /**
     * 当前租户ID
     */
    private Long tenantId;

    /**
     * 操作租户ID
     */
    private Long operateTenantId;

    /**
     * 租户类型
     */
    private TenantType tenantType;

}
