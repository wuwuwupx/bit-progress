package com.bitprogress.constant;

/**
 * @author wuwuwupx
 * create on 2021/7/19 0:08
 * 微信的请求URL
 */
public class WechatPayUrl {

    /**
     * jsapi支付的请求地址
     * POST
     */
    public static final String JS_API_UNIFIED_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";

    /**
     * APP支付的请求地址
     * POST
     */
    public static final String APP_PAY_UNIFIED_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/app";

    /**
     * H5支付的请求地址
     * POST
     */
    public static final String H5_PAY_UNIFIED_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/h5";

    /**
     * 查询订单请求地址
     * 微信支付订单号查询
     * GET
     * https://api.mch.weixin.qq.com/v3/pay/transactions/id/{transaction_id}?mchid=xxx
     */
    public static final String TRANSACTION_ID_QUERY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/id/";

    /**
     * 查询订单请求地址
     * 商户订单号查询
     * GET
     * https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/{out_trade_no}?mchid=xxx
     */
    public static final String OUT_TRADE_NO_QUERY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/";

    /**
     * 关闭订单请求地址
     * 根据商户订单号关闭
     * POST
     * https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/{out_trade_no}/close
     */
    public static final String ORDER_CLOSE_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/{out_trade_no}/close";

}
