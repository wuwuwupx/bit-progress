package com.bitprogress.producer;

import com.bitprogress.constant.RabbitMQConstant;
import com.bitprogress.property.RabbitMQC;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author wuwuwupx
 * 生产者基础类
 */
@Component
public abstract class BaseProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 创建并发送消息，使用默认的重试次数
     *
     * @param data
     * @param exchangeName
     * @param routingKey
     * @param <T>
     */
    public <T> void createAndSendMessage(T data, String exchangeName, String routingKey) {
        createAndSendMessage(data, exchangeName, routingKey, null);
    }

    /**
     * 创建并发送消息
     *
     * @param data
     * @param exchangeName
     * @param routingKey
     * @param maxRetryTimes
     * @param <T>
     */
    public <T> void createAndSendMessage(T data, String exchangeName, String routingKey, Integer maxRetryTimes) {
        int day = LocalDateTime.now().getDayOfYear();

        rabbitTemplate.convertAndSend(exchangeName, routingKey, data, message -> {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setHeader(RabbitMQConstant.RETRY_TIMES, 0);
                    messageProperties.setHeader(RabbitMQConstant.MAX_RETRY_TIMES, maxRetryTimes);
                    messageProperties.setHeader(RabbitMQConstant.DAY, day);
                    messageProperties.setPriority(RabbitMQC.MIN_PRIORITY_SETTING);
                    return message;
                }
        );
    }

}
