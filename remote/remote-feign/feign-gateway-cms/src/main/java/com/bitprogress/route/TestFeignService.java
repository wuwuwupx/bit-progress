package com.bitprogress.route;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.login.TokenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wuwuwupx
 */
@FeignClient(name = "gateway-cms", path = "rest/gatewayCms/test", fallbackFactory = TestFeignServiceFallback.class)
public interface TestFeignService {

    @GetMapping
    ResultVO<String> getToken(@SpringQueryMap TokenDTO o);

}
