package com.bitprogress.securityspring.context;

import com.bitprogress.basecontext.service.InfoContextService;
import com.bitprogress.securityroute.entity.BaseUserInfo;

public interface UserInfoContextService<T extends BaseUserInfo> extends InfoContextService<T> {
}
