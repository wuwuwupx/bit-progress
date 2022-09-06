package com.bitprogress;

import com.bitprogress.model.ResultVO;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author wuwuwupx
 */
@Component
public class FeignEurekaProServiceFallback implements FallbackFactory<FeignEurekaProService> {

    /**
     * Returns an instance of the fallback appropriate for the given cause.
     *
     * @param cause cause of an exception.
     * @return fallback
     */
    @Override
    public FeignEurekaProService create(Throwable cause) {
        return new FeignEurekaProService() {
            @Override
            public ResultVO<Long> get(Long id) {
                return null;
            }
        };
    }
}
