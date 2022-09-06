package com.bitprogress.constant;

/**
 * @author wpx
 */
public class BaseRedisKeyPrefix {


    /** ************************************************************************************************************** */

    /**
     * RabbitMQ 相关
     */

    /**
     * RabbitMQ 消息标识码 自增生成RabbitMQ的标识码
     * Redis value increment
     */
    public static String rabbitMQIdentifier(int day) {
        return "RabbitMQIdentifier:" + day;
    }

    /**
     * RabbitMQ 已消费消息标识码  键值对（key -- value -- 都是标识码）
     * Redis hash
     * 消息消费前从hash中查询是否存在标识码，存在则标识消息已消费，不存在则表示未消费
     * 消息消费后将标识码保存到hash中
     * 改为以天为key
     */
    public static String rabbitMQConsumedIdentifier(String day){
        return "RabbitMQConsumedIdentifier:" + day;
    }

    /** ************************************************************************************************************** */

    /**
     * 短信验证码
     *
     * @param phone
     */
    public static String smsCaptcha(String phone) {
        return "SmsCaptcha:" + phone;
    }

    /**
     * 存放验证码的key
     *
     * @param uuid
     */
    public static String picCaptcha(String uuid) {
        return "PicCaptcha:" + uuid;
    }

    /* ====================================== 应用信息 ======================================= */

    /**
     * 存放应用信息的key   hash（key：应用信息ID，value：应用信息）
     */
    public static String applicationMessage() {
        return "ApplicationMessage:";
    }

    /**
     * 存放应用主题消息信息的key   hash（key：topic，value：应用信息）
     */
    public static String applicationTopicMessage() {
        return "ApplicationTopicMessage:";
    }

    /**
     * 获取accessToken的key
     * value
     */
    public static String accessTokenKey(String appSign) {
        return "AccessTokenKey:" + appSign;
    }

    /**
     * 微信应用信息存放key
     * hash hashKey：appSign
     */
    public static String wechatAppKey() {
        return "WechatAppKey:";
    }

}
