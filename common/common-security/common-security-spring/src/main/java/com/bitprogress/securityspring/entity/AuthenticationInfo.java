package com.bitprogress.securityspring.entity;

import com.bitprogress.basemodel.info.ContextInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthenticationInfo extends ContextInfo {

    /**
     * 是否认证
     */
    private Boolean isAuthentication;

}
