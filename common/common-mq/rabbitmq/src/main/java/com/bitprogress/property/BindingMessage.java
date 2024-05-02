package com.bitprogress.property;

import java.util.Map;

/**
 * @author wuwuwupx
 * 绑定配置
 */
public class BindingMessage {

    /**
     * 目标名称（可以是队列 也可以是交换机）
     */
    private String destinationName;

    /**
     * 绑定类型
     */
    private DestinationType destinationType = DestinationType.QUEUE;

    /**
     * 交换机名称
     */
    private String exchangeName;

    /**
     * 路由key
     */
    private String routingKey;

    /**
     * 绑定参数
     */
    private Map<String, Object> argument;

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public DestinationType getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Map<String, Object> getArgument() {
        return argument;
    }

    public void setArgument(Map<String, Object> argument) {
        this.argument = argument;
    }

    /**
     * The binding destination.
     */
    public enum DestinationType {

        /**
         * Queue destination.
         */
        QUEUE,

        /**
         * Exchange destination.
         */
        EXCHANGE;
    }

}
