package com.bitprogress.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.bitprogress.constant.RabbitMQConstant;
import com.bitprogress.property.RabbitMQC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @author wuwuwupx
 * 基础消费者
 */
@Service
public abstract class BaseConsumer {

    /**
     * 重试发送消息
     *
     * @param t
     * @param channel
     * @param message
     */
    public <T> void retryAndSendMessage(T t, Channel channel, Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();

        int retryTimes = (int) headers.get(RabbitMQConstant.RETRY_TIMES);
        int maxRetryTimes = (int) headers.get(RabbitMQConstant.MAX_RETRY_TIMES);
        retryTimes = retryTimes + 1;

        // 从redis hash中删除消费标识码
        String day = String.valueOf(headers.get(RabbitMQConstant.DAY));
        String identifier = String.valueOf(headers.get(RabbitMQConstant.IDENTIFIER));

        if (retryTimes > maxRetryTimes) {
            return;
        }
        message.getMessageProperties().setHeader(RabbitMQConstant.RETRY_TIMES, retryTimes);
        MessageProperties messageProperties = message.getMessageProperties();
        String routingKey = messageProperties.getReceivedRoutingKey();
        String exchangeName = messageProperties.getReceivedExchange();

        AMQP.BasicProperties build = new AMQP.BasicProperties().builder()
                .headers(message.getMessageProperties().getHeaders())
                .appId(messageProperties.getAppId())
                .clusterId(messageProperties.getClusterId())
                .contentEncoding(messageProperties.getContentEncoding())
                .contentType(messageProperties.getContentType())
                .correlationId(messageProperties.getCorrelationId())
                //优先级最高，Larger numbers indicate higher priority
                .priority(RabbitMQC.MAX_PRIORITY_SETTING)
                .build();
        try {
            channel.basicPublish(exchangeName, routingKey, build, message.getBody());
        } catch (IOException e) {
        }
    }

}
