package com.bitprogress.route;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class TestFeignServiceFallback implements FallbackFactory<TestFeignService> {
    /**
     * Returns an instance of the fallback appropriate for the given cause.
     *
     * @param cause cause of an exception.
     * @return fallback
     */
    @Override
    public TestFeignService create(Throwable cause) {
        return null;
    }
}
