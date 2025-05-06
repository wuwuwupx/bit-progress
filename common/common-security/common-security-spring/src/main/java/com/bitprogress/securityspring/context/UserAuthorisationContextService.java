package com.bitprogress.securityspring.context;

import com.bitprogress.securityspring.entity.UserAuthorisationInfo;

public interface UserAuthorisationContextService extends UserInfoContextService<UserAuthorisationInfo> {

    /**
     * 获取上下文信息类型
     *
     * @return 上下文信息类型
     */
    @Override
    default Class<UserAuthorisationInfo> getInfoClass() {
        return UserAuthorisationInfo.class;
    }

}
