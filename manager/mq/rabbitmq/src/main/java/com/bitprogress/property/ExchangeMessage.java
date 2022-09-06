package com.bitprogress.property;

import java.util.Map;

/**
 * @author wuwuwupx
 * 交换机配置
 */
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

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(ExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }

    public Map<String, Object> getArgument() {
        return argument;
    }

    public void setArgument(Map<String, Object> argument) {
        this.argument = argument;
    }

    public boolean isDurable() {
        return durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

}
