package com.bitprogress.securityroute.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserAuthorisationInfo extends BaseUserInfo {

    /**
     * 权限字符
     */
    private Set<String> permissions;

    /**
     * 角色字符
     */
    private Set<String> roleKeys;

}
