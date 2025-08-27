package com.bitprogress.property;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 交换机配置
 */
@Setter
@Getter
public class ExchangeMessage {

    /**
     * 交换机名称
     */
    private String exchangeName;

    /**
     * 交换机类型
     */
    private ExchangeType exchangeType = ExchangeType.DIRECT;

    /**
     * 交换机参数
     */
    private Map<String, Object> argument;

    /**
     * 是否持久化，默认true
     */
    private boolean durable = true;

    /**
     * 是否自动删除，默认false
     */
    private boolean autoDelete = false;

    /**
     * 交换价类型枚举
     */
    public enum ExchangeType {

        /**
         * DirectExchange
         */
        DIRECT,

        /**
         * TopicExchange
         */
        TOPIC,

        /**
         * FanoutExchange
         */
        FANOUT,

        /**
         * HeadersExchange
         */
        HEADERS,

        ;

    }

}
