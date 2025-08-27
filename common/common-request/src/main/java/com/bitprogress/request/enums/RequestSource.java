package com.bitprogress.request.enums;

import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求来源
 * 一般是在会进行预处理的网关、中间件中添加，用于记录请求来源
 * 与调度类型进行配合
 */
@AllArgsConstructor
@Getter
public enum RequestSource implements ValueEnum {

    /**
     * 直接请求，未经过任何会标记来源预处理的网关和中间件等
     * 相当于默认值
     */
    DIRECT(0),

    /**
     * Nginx 转发
     */
    NGINX(1),

    /**
     * Gateway 转发
     */
    GATEWAY(2),

    /**
     * feign
     */
    FEIGN(3),

    /**
     * dubbo
     */
    DUBBO(4),

    ;

    private final Integer value;

}
