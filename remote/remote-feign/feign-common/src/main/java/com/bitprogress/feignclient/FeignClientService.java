package com.bitprogress.feignclient;

import com.bitprogress.model.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * 基础的feign调用方法，从URI中解析服务名
 *
 * @author wuwuwupx
 */
@FeignClient(name = FeignClientService.FEIGN_NAME, fallbackFactory = FeignClientServiceFallback.class)
public interface FeignClientService {

    String FEIGN_NAME = "feign-common";

    /**
     * 在URI中动态指定目标服务的feignClient -- GET
     *
     * @param uri  目标uri
     * @param data 请求参数
     * @return ResultVO<Object>
     */
    @GetMapping
    ResultVO<Object> createGetFeignClient(URI uri, @SpringQueryMap Object data);

    /**
     * 在URI中动态指定目标服务的feignClient -- POST
     *
     * @param uri  目标uri
     * @param data 请求body参数
     * @return ResultVO<Object>
     */
    @PostMapping
    ResultVO<Object> createPostFeignClient(URI uri, @RequestBody Object data);

    /**
     * 在URI中动态指定目标服务的feignClient -- PUT
     *
     * @param uri  目标uri
     * @param data 请求body参数
     * @return ResultVO<Object>
     */
    @PutMapping
    ResultVO<Object> createPutFeignClient(URI uri, @RequestBody Object data);

    /**
     * 在URI中动态指定目标服务的feignClient -- DELETE
     *
     * @param uri  目标uri
     * @param data 请求参数
     * @return ResultVO<Object>
     */
    @DeleteMapping
    ResultVO<Object> createDeleteFeignClient(URI uri, @SpringQueryMap Object data);

}
