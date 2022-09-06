package com.bitprogress.quartzjob;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author wuwuwupx
 * 定时任务服务降级
 */
@Component
public class QuartzJobFeignServiceFallback implements FallbackFactory<QuartzJobFeignService> {

    /**
     * Returns an instance of the fallback appropriate for the given cause.
     *
     * @param cause cause of an exception.
     * @return fallback
     */
    @Override
    public QuartzJobFeignService create(Throwable cause) {
        return null;
    }

}
