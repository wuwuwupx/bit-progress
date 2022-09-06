####后续拓展消息中心的配置
####后续拓展基础生产者和基础消费者的方法
####前仅支持一个消息中心
- 配置方式如下
```yaml
spring:
  rabbitmq:
    host: 193.112.251.82
    port: 5671
    username: luweirabbitmq
    password: 3TI4FevIFfziMo03
    listener:
      simple:
        acknowledge-mode: manual
        retry:
          enabled: true
        prefetch: 1
    requested-heartbeat: 60
    # 配置交换机
    exchange:
      # exchange1Exchange 为交换机在IOC容器中的beanName
      exchange1:
        exchange-name: testexchange
        exchange-type: direct
        argument:
          argument1: argument1
        durable: true
        autoDelete: false
    # 配置队列
    queue:
      # queue1Queue 为队列在IOC容器中的beanName
      queue1:
        queue-name: testqueue
        argument:
          argument1: argument1
    # 配置绑定信息
    binding:
      # binding1Binding 为绑定信息在IOC容器中的beanName
      binding1:
        # 绑定的目标，目标类型为交换机时配置目标交换机的 exchange-name，目标类型为队列时配置目标队列的 queue-name
        destination-name: testqueue
        # 目标的类型，exchange 为交换机 queue 为队列
        destination-type: queue
        # 绑定的交换机，即消息发出的交换机
        exchange-name: testexchange
        # 路由key
        routing-key: testroutingKey
```
- 在上面配置中，配置一个交换机、一个队列和一个绑定信息
- 消息的发出和接收如下
```java
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * 消息生产者
 */
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * testexchange  exchangeName
     * testroutingKey  routingKey
     * testMessage  发送的消息内容
     * 消息将从 testexchange（交换机）发出，并发送到 绑定了testexchange（交换机）和路由key为 testroutingKey 的 destination
     * 在配置的binding中，绑定了testexchange（交换机）和路由key为 testroutingKey的destination类型是queue，所以将发送到 testqueue
     */
    public void sendMessage() {
        rabbitTemplate.convertAndSend("testexchange", "testroutingKey", "testMessage", message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            messageProperties.setHeader(RabbitMQConstant.RETRY_TIMES, 0);
            messageProperties.setPriority(RabbitMQC.MIN_PRIORITY_SETTING);
            return message;
        });
    }

}

/**
 * 消息消费者
 */
public class Consumer {

    /**
     * 消息消费
     * 监听队列名为 testqueue 的队列
     * 在 channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); 应答后才会继续接收消息
     * 
     * @param s  消息内容
     * @param channel  channel
     * @param message  message
     */
    @RabbitListener(queues = "testqueue")
    public void consumerMessage(String s, Channel channel, Message message) {
        try {
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
    
}
```
#### ExchangeType
- DirectExchange
  1. 查出绑定了Exchange和RoutingKey的Binding（RoutingKey需要完整匹配）
  2. 转发到Binding绑定的Destination <br>
  2.1. 如果Binding绑定的Destination只有一个，而此Destination被多个消费者绑定，则轮询推送对应的消费者，即每次只发送一次 <br>
  2.2. 如果有多个Binding绑定的Destination有多个，则会推送到所有的Destination
- TopicExchange
  1. 查出绑定了Exchange和RoutingKey的Binding（RoutingKey通过特殊方式匹配）<br>
  1.1. Binding的RoutingKey可以带上"#"和"*" <br>
  1.2. "#"匹配一个或多个词，"*"只能匹配一个词 <br>
  1.3. aaa.bbb.ccc 可以匹配到 aaa.bbb.ccc、aaa.bbb.#、aaa.bbb.*、aaa.#
  2. 转发到Binding绑定的Destination
- FanoutExchange
  1. 查出绑定了Exchange的Binding（RoutingKey不需要匹配）
  2. 转发到Binding绑定的Destination
- HeadersExchange
  1. 查出绑定了Exchange的Binding（RoutingKey不需要匹配）
  2. 匹配Binding的header <br>
  2.1. Binding的header中的key为x-match为特殊参数，value为all则要完整匹配header，value为any则匹配部分header <br>
  2.2. {"key":"value"}匹配{"x-match":"all","key":"value","key1":"value1"}失败
  2.3. 匹配{"x-match":"any","key":"value","key1":"value1"}则成功