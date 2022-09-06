package com.bitprogress.constant;

/**
 * @author wuwuwupx
 * RabbitMQ常量
 */
public class RabbitMQConstant {

    /**
     * CollectionFactory的beanName
     */
    public static final String CONNECTION_BEAN_NAME = "Connection";

    /**
     * RabbitTemplate的beanName
     */
    public static final String TEMPLATE_BEAN_NAME = "RabbitTemplate";

    /**
     * RabbitTemplate的beanName
     */
    public static final String LISTENER_CONTAINER_BEAN_NAME = "ListenerContainer";

    /**
     * 交换机beanName
     */
    public static final String EXCHANGE_BEAN_NAME = "Exchange";

    /**
     * 队列beanName
     */
    public static final String QUEUE_BEAN_NAME = "Queue";

    /**
     * Binding beanName
     */
    public static final String BINDING_BEAN_NAME = "Binding";

    /**
     * 重试次数
     */
    public static final String RETRY_TIMES = "retryTimes";

    /**
     * 最大重试次数
     */
    public static final String MAX_RETRY_TIMES = "maxRetryTimes";

    /**
     * 消息唯一标识码
     */
    public static final String IDENTIFIER = "identifier";

    /**
     * 消息唯一标识码日期
     */
    public static final String DAY = "day";

}
