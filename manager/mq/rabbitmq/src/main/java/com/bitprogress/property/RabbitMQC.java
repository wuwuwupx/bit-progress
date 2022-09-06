package com.bitprogress.property;

public interface RabbitMQC {

    //设置队列中消息ttl参数，时间单位毫秒，Number
    String QUEUE_MESSAGE_TTL = "x-message-ttl";
    //最大优先级
    String MAX_PRIORITY = "x-max-priority";
    Integer MIN_PRIORITY_SETTING = 1;
    Integer MAX_PRIORITY_SETTING = 10;

    /**
     * 交换机
     */
    String EXCHANGE_NAME = "";

    /**
     * 队列名
     */
    String QUEUE_NAME = "";

    /**
     * routingKey
     */
    String ROUTING_KEY = "";

}
