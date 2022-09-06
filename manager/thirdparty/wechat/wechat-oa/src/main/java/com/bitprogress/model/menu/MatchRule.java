package com.bitprogress.model.menu;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 */
public class MatchRule {

    /**
     * 用户标签的id，可通过用户标签管理接口获取
     */
    @JsonProperty("tag_id")
    private String tagId;

    /**
     *
     */
    @JsonProperty("group_id")
    private Integer groupId;
    /**
     * 性别：男（1）女（2），不填则不做匹配
     */
    private Integer sex;
    /**
     * 国家信息，是用户在微信中设置的地区，具体请参考地区信息表
     */
    private String country;

    /**
     * 省份信息，是用户在微信中设置的地区，具体请参考地区信息表
     */
    private String province;

    /**
     * 城市信息，是用户在微信中设置的地区，具体请参考地区信息表
     */
    private String city;

    /**
     * 客户端版本，当前只具体到系统型号：IOS(1), Android(2),Others(3)，不填则不做匹配
     */
    @JsonProperty("client_platform_type")
    private String clientPlatformType;

    /**
     * 语言信息
     */
    private String language;

}
