package com.bitprogress;

import com.bitprogress.model.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuwuwupx
 */
@FeignClient(name = "eureka-pro", path = "rest", fallbackFactory = FeignEurekaProServiceFallback.class)
public interface FeignEurekaProService {

    @GetMapping("test")
    ResultVO<Long> get(@RequestParam("id") Long id);

}
