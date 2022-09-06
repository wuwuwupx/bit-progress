package com.bitprogress.register;

import com.rabbitmq.client.ConnectionFactory;
import com.bitprogress.property.*;
import com.bitprogress.util.CollectionUtils;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Map;

import static com.bitprogress.constant.RabbitMQConstant.*;

/**
 * @author wuwuwupx
 * RabbitMQ注册
 */
@Component
public class RabbitMQRegister implements BeanFactoryAware, InstantiationAwareBeanPostProcessor {

    @Autowired
    private RabbitMQProperties rabbitMQProperties;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;

        // 创建rabbit多数据源
        Map<String, ConnectionMessage> connectionMessages = rabbitMQProperties.getConnection();
        registerConnection(connectionMessages, listableBeanFactory);

        // 向IOC容器中注册交换机
        Map<String, ExchangeMessage> exchangeMessages = rabbitMQProperties.getExchange();
        registerExchange(exchangeMessages, listableBeanFactory);

        //  向IOC容器中注册队列
        Map<String, QueueMessage> queueMessages = rabbitMQProperties.getQueue();
        registerQueue(queueMessages, listableBeanFactory);

        //  向IOC容器中注册绑定信息
        Map<String, BindingMessage> bindingMessages = rabbitMQProperties.getBinding();
        registerBinding(bindingMessages, listableBeanFactory);

    }

    /**
     * 创建rabbit多数据源
     *
     * @param messages
     * @param beanFactory
     */
    public void registerConnection(Map<String, ConnectionMessage> messages, DefaultListableBeanFactory beanFactory) {
        if (CollectionUtils.nonEmpty(messages)) {
            messages.forEach((name, connectionMessage) -> {
                // CachingConnectionFactory
                String connectionBeanName = name + CONNECTION_BEAN_NAME;
                RabbitConnectionFactoryBean factoryBean = getRabbitConnectionFactoryBean(connectionMessage);
                ConnectionFactory connectionFactory = null;
                try {
                    connectionFactory = factoryBean.getObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CachingConnectionFactory factory = new CachingConnectionFactory(connectionFactory);
                PropertyMapper property = PropertyMapper.get();
                property.from(connectionMessage::determineAddresses).to(factory::setAddresses);
                property.from(connectionMessage::getAddressShuffleMode).whenNonNull().to(factory::setAddressShuffleMode);
                property.from(connectionMessage::isPublisherReturns).to(factory::setPublisherReturns);
                property.from(connectionMessage::getPublisherConfirmType).whenNonNull().to(factory::setPublisherConfirmType);
                ConnectionMessage.Cache.Channel channel = connectionMessage.getCache().getChannel();
                property.from(channel::getSize).whenNonNull().to(factory::setChannelCacheSize);
                property.from(channel::getCheckoutTimeout).whenNonNull().as(Duration::toMillis)
                        .to(factory::setChannelCheckoutTimeout);
                ConnectionMessage.Cache.Connection connection = connectionMessage.getCache().getConnection();
                property.from(connection::getMode).whenNonNull().to(factory::setCacheMode);
                property.from(connection::getSize).whenNonNull().to(factory::setConnectionCacheSize);

                // RabbitTemplate
                String rabbitTemplateBeanName = name + TEMPLATE_BEAN_NAME;
                RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
                beanFactory.registerSingleton(rabbitTemplateBeanName, rabbitTemplate);

                String listenerContainerBeanName = name + LISTENER_CONTAINER_BEAN_NAME;
                SimpleRabbitListenerContainerFactory listenerContainer = getListenerContainer(factory, connectionMessage);
                beanFactory.registerSingleton(listenerContainerBeanName, listenerContainer);

            });

        }
    }

    /**
     * 向IOC容器中注册交换机
     *
     * @param messages
     */
    private void registerExchange(Map<String, ExchangeMessage> messages, DefaultListableBeanFactory beanFactory) {
        if (CollectionUtils.nonEmpty(messages)) {
            messages.forEach((name, exchangeMessage) -> {
                String exchangeBeanName = name + EXCHANGE_BEAN_NAME;
                String exchangeName = exchangeMessage.getExchangeName();
                ExchangeMessage.ExchangeType exchangeType = exchangeMessage.getExchangeType();
                boolean durable = exchangeMessage.isDurable();
                boolean autoDelete = exchangeMessage.isAutoDelete();
                Map<String, Object> argument = exchangeMessage.getArgument();
                AbstractExchange exchange;
                switch (exchangeType) {
                    case TOPIC: {
                        exchange = new TopicExchange(exchangeName, durable, autoDelete, argument);
                        break;
                    }
                    case FANOUT: {
                        exchange = new FanoutExchange(exchangeName, durable, autoDelete, argument);
                        break;
                    }
                    case HEADERS: {
                        exchange = new HeadersExchange(exchangeName, durable, autoDelete, argument);
                        break;
                    }
                    default: {
                        exchange = new DirectExchange(exchangeName, durable, autoDelete, argument);
                        break;
                    }
                }
                beanFactory.registerSingleton(exchangeBeanName, exchange);
            });
        }
    }

    /**
     * 向IOC容器中注册队列
     *
     * @param queueMessages
     * @param beanFactory
     */
    private void registerQueue(Map<String, QueueMessage> queueMessages, DefaultListableBeanFactory beanFactory) {
        if (CollectionUtils.nonEmpty(queueMessages)) {
            queueMessages.forEach((name, queueMessage) -> {
                String queueBeanName = name + QUEUE_BEAN_NAME;
                String queueName = queueMessage.getQueueName();
                Map<String, Object> argument = queueMessage.getArgument();
                QueueBuilder builder = QueueBuilder.durable(queueName);
                if (CollectionUtils.nonEmpty(argument)) {
                    builder.withArguments(argument);
                }
                Queue queue = builder.build();
                beanFactory.registerSingleton(queueBeanName, queue);
            });
        }
    }

    /**
     * 向IOC容器中注册绑定信息
     *
     * @param bindingMessages
     * @param beanFactory
     */
    private void registerBinding(Map<String, BindingMessage> bindingMessages, DefaultListableBeanFactory beanFactory) {
        if (CollectionUtils.nonEmpty(bindingMessages)) {
            bindingMessages.forEach((name, bindingMessage) -> {
                String bindingBeanName = name + BINDING_BEAN_NAME;
                String destinationName = bindingMessage.getDestinationName();
                BindingMessage.DestinationType bindingDestinationType = bindingMessage.getDestinationType();
                String exchangeName = bindingMessage.getExchangeName();
                String routingKey = bindingMessage.getRoutingKey();
                Map<String, Object> argument = bindingMessage.getArgument();
                Binding.DestinationType destinationType;
                switch (bindingDestinationType) {
                    case EXCHANGE: {
                        destinationType = Binding.DestinationType.EXCHANGE;
                        break;
                    }
                    default: {
                        destinationType = Binding.DestinationType.QUEUE;
                        break;
                    }
                }
                Binding binding = new Binding(destinationName, destinationType, exchangeName, routingKey, argument);
                beanFactory.registerSingleton(bindingBeanName, binding);
            });
        }
    }

    /**
     * 创建 RabbitConnectionFactoryBean
     *
     * @param message
     */
    private RabbitConnectionFactoryBean getRabbitConnectionFactoryBean(ConnectionMessage message) {
        RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();
        PropertyMapper map = PropertyMapper.get();
        map.from(message::determineHost).whenNonNull().to(factory::setHost);
        map.from(message::determinePort).to(factory::setPort);
        map.from(message::determineUsername).whenNonNull().to(factory::setUsername);
        map.from(message::determinePassword).whenNonNull().to(factory::setPassword);
        map.from(message::determineVirtualHost).whenNonNull().to(factory::setVirtualHost);
        map.from(message::getRequestedHeartbeat).whenNonNull().asInt(Duration::getSeconds)
                .to(factory::setRequestedHeartbeat);
        map.from(message::getRequestedChannelMax).to(factory::setRequestedChannelMax);
        ConnectionMessage.Ssl ssl = message.getSsl();
        if (ssl.determineEnabled()) {
            factory.setUseSSL(true);
            map.from(ssl::getAlgorithm).whenNonNull().to(factory::setSslAlgorithm);
            map.from(ssl::getKeyStoreType).to(factory::setKeyStoreType);
            map.from(ssl::getKeyStore).to(factory::setKeyStore);
            map.from(ssl::getKeyStorePassword).to(factory::setKeyStorePassphrase);
            map.from(ssl::getTrustStoreType).to(factory::setTrustStoreType);
            map.from(ssl::getTrustStore).to(factory::setTrustStore);
            map.from(ssl::getTrustStorePassword).to(factory::setTrustStorePassphrase);
            map.from(ssl::isValidateServerCertificate)
                    .to((validate) -> factory.setSkipServerCertificateValidation(!validate));
            map.from(ssl::getVerifyHostname).to(factory::setEnableHostnameVerification);
        }
        map.from(message::getConnectionTimeout).whenNonNull().asInt(Duration::toMillis)
                .to(factory::setConnectionTimeout);
        map.from(message::getChannelRpcTimeout).whenNonNull().asInt(Duration::toMillis)
                .to(factory::setChannelRpcTimeout);
        factory.afterPropertiesSet();
        return factory;
    }

    private SimpleRabbitListenerContainerFactory getListenerContainer(CachingConnectionFactory factory,
                                                                      ConnectionMessage connectionMessage) {
        SimpleRabbitListenerContainerFactory listenerContainer = new SimpleRabbitListenerContainerFactory();
        listenerContainer.setConnectionFactory(factory);
        PropertyMapper map = PropertyMapper.get();
        ConnectionMessage.SimpleContainer container = connectionMessage.getListener().getSimple();
        Assert.notNull(listenerContainer, "listenerContainer must not be null");
        listenerContainer.setAutoStartup(container.isAutoStartup());
        if (container.getAcknowledgeMode() != null) {
            listenerContainer.setAcknowledgeMode(container.getAcknowledgeMode());
        }
        if (container.getPrefetch() != null) {
            listenerContainer.setPrefetchCount(container.getPrefetch());
        }
        if (container.getDefaultRequeueRejected() != null) {
            listenerContainer.setDefaultRequeueRejected(container.getDefaultRequeueRejected());
        }
        if (container.getIdleEventInterval() != null) {
            listenerContainer.setIdleEventInterval(container.getIdleEventInterval().toMillis());
        }
        listenerContainer.setMissingQueuesFatal(container.isMissingQueuesFatal());
        listenerContainer.setDeBatchingEnabled(container.isDeBatchingEnabled());
        map.from(container::getConcurrency).whenNonNull().to(listenerContainer::setConcurrentConsumers);
        map.from(container::getMaxConcurrency).whenNonNull().to(listenerContainer::setMaxConcurrentConsumers);
        map.from(container::getBatchSize).whenNonNull().to(listenerContainer::setBatchSize);
        map.from(container::isConsumerBatchEnabled).to(listenerContainer::setConsumerBatchEnabled);
        return listenerContainer;
    }

}
