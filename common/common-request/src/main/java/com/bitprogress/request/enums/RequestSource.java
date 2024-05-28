package com.bitprogress.request.enums;

import com.bitprogress.basemodel.enums.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求来源
 */
@AllArgsConstructor
@Getter
public enum RequestSource implements ValueEnum {

    /**
     * Gateway 转发
     */
    GATEWAY_ROUTE(0),

    /**
     * feign
     */
    FEIGN(1),

    ;

    private final Integer value;

    public static RequestSource getByValue(Integer value) {
        for (RequestSource requestSource : RequestSource.values()) {
            if (requestSource.getValue().equals(value)) {
                return requestSource;
            }
        }
        return null;
    }

}
