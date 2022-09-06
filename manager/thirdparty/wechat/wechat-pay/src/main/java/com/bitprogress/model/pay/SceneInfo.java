package com.bitprogress.model.pay;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 * 支付场景信息
 */
public class SceneInfo {

    /**
     * 必填
     * 用户的客户端IP，支持IPv4和IPv6两种格式的IP地址
     * string[1,45]
     */
    @JsonProperty("payer_client_ip")
    private String payerClientIp;

    /**
     * 非必填
     * 商户端设备号（门店号或收银设备ID）
     * string[1,32]
     */
    @JsonProperty("device_id")
    private String deviceId;

    /**
     * 非必填
     * 商户门店信息
     */
    @JsonProperty("store_info")
    private StoreInfo storeInfo;

    public String getPayerClientIp() {
        return payerClientIp;
    }

    public SceneInfo setPayerClientIp(String payerClientIp) {
        this.payerClientIp = payerClientIp;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public SceneInfo setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public SceneInfo setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
        return this;
    }

}
