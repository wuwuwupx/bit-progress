package com.bitprogress.securityroute.service.impl;

import com.bitprogress.securityroute.entity.UserAuthorisationInfo;
import com.bitprogress.securityroute.service.AuthorisationService;
import com.bitprogress.securityroute.service.context.UserAuthorisationContextService;
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
