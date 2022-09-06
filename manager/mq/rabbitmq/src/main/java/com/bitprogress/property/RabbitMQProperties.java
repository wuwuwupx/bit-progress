package com.bitprogress.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author wuwuwupx
 * RabbitMQ配置
 */
@Configuration
@ConfigurationProperties(prefix = RabbitMQProperties.PREFIX)
public class RabbitMQProperties {

    public static final String PREFIX = "spring.rabbitmq";

    private Map<String, ConnectionMessage> connection;

    /**
     * 交换机配置
     */
    private Map<String, ExchangeMessage> exchange;

    /**
     * 队列配置
     */
    private Map<String, QueueMessage> queue;

    /**
     * 绑定配置
     */
    private Map<String, BindingMessage> binding;

    public Map<String, ConnectionMessage> getConnection() {
        return connection;
    }

    public void setConnection(Map<String, ConnectionMessage> connection) {
        this.connection = connection;
    }

    public Map<String, ExchangeMessage> getExchange() {
        return exchange;
    }

    public void setExchange(Map<String, ExchangeMessage> exchange) {
        this.exchange = exchange;
    }

    public Map<String, QueueMessage> getQueue() {
        return queue;
    }

    public void setQueue(Map<String, QueueMessage> queue) {
        this.queue = queue;
    }

    public Map<String, BindingMessage> getBinding() {
        return binding;
    }

    public void setBinding(Map<String, BindingMessage> binding) {
        this.binding = binding;
    }

}
