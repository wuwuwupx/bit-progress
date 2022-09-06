package com.bitprogress.model.pay;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 * 商户门店信息
 * 对于json结构体参数，内层参数的必填属性仅在结构体有传参的情况下才生效，即如果json结构体都不传参，则内层参数必定全部都不传参。
 */
public class StoreInfo {

    /**
     * 必填
     * 商户侧门店编号 示例值：0001
     * string[1,32]
     */
    private String id;

    /**
     * 非必填
     * 商户侧门店名称 示例值：腾讯大厦分店
     * string[1,256]
     */
    private String name;

    /**
     * 非必填
     * 地区编码，详细请见省市区编号对照表
     * string[1,32]
     */
    @JsonProperty("area_code")
    private String areaCode;

    /**
     * 非必填
     * 详细的商户门店地址
     * string[1,512]
     */
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
