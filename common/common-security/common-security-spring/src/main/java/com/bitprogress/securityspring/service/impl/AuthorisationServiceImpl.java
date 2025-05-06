package com.bitprogress.securityspring.service.impl;

import com.bitprogress.securityspring.entity.UserAuthorisationInfo;
import com.bitprogress.securityspring.context.UserAuthorisationContextService;
import com.bitprogress.securityspring.service.AuthorisationService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorisationServiceImpl implements AuthorisationService {

    private final UserAuthorisationContextService userAuthorisationContextService;

    /**
     * 获取用户权限信息
     *
     * @return 用户权限信息
     */
    @Override
    public UserAuthorisationInfo getUserAuthorisation() {
        return userAuthorisationContextService.getContextInfo();
    }

}
