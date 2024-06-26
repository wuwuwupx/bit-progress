package com.bitprogress.feignclient;

import com.bitprogress.response.model.ResultResponse;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * 服务降级
 */
@Component
public class FeignClientServiceFallback implements FallbackFactory<FeignClientService> {
    /**
     * Returns an instance of the fallback appropriate for the given cause.
     *
     * @param cause cause of an exception.
     * @return fallback
     */
    @Override
    public FeignClientService create(Throwable cause) {
        return new FeignClientService() {

            /**
             * 在URI中动态指定目标服务的feignClient -- GET
             *
             * @param uri  目标uri
             * @param data 请求参数
             * @return ResultVO<Object>
             */
            @Override
            public ResultResponse<Object> createGetFeignClient(URI uri, Object data) {
                return null;
            }

            /**
             * 在URI中动态指定目标服务的feignClient -- POST
             *
             * @param uri  目标uri
             * @param data 请求body参数
             * @return ResultVO<Object>
             */
            @Override
            public ResultResponse<Object> createPostFeignClient(URI uri, Object data) {
                return null;
            }

            /**
             * 在URI中动态指定目标服务的feignClient -- PUT
             *
             * @param uri  目标uri
             * @param data 请求body参数
             * @return ResultVO<Object>
             */
            @Override
            public ResultResponse<Object> createPutFeignClient(URI uri, Object data) {
                return null;
            }

            /**
             * 在URI中动态指定目标服务的feignClient -- DELETE
             *
             * @param uri  目标uri
             * @param data 请求参数
             * @return ResultVO<Object>
             */
            @Override
            public ResultResponse<Object> createDeleteFeignClient(URI uri, Object data) {
                return null;
            }

        };
    }
}
