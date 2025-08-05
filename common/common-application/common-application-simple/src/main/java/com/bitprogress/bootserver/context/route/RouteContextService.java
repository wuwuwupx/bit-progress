package com.bitprogress.bootserver.context.route;

import com.bitprogress.basecontext.service.SetContextService;
import com.bitprogress.securityroute.entity.ApiRoute;

public interface RouteContextService<T extends ApiRoute> extends SetContextService<T> {

}
