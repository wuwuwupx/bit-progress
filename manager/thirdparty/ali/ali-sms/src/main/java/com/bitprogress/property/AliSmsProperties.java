package com.bitprogress.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuwuwupx
 */
@Configuration
@ConfigurationProperties(prefix = AliSmsProperties.PREFIX)
public class AliSmsProperties {

    public static final String PREFIX = "ali.sms";

    /**
     * 密钥
     */
    private String accessKeyId;

    /**
     * 密码
     */
    private String accessKeySecret;

    /**
     * 签名
     */
    private String signName;

    /**
     * 模板
     */
    private String templateCode;

    /**
     * 境外签名
     */
    private String overseaSignName;

    /**
     * 境外模板
     */
    private String overseaTemplateCode;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getOverseaSignName() {
        return overseaSignName;
    }

    public void setOverseaSignName(String overseaSignName) {
        this.overseaSignName = overseaSignName;
    }

    public String getOverseaTemplateCode() {
        return overseaTemplateCode;
    }

    public void setOverseaTemplateCode(String overseaTemplateCode) {
        this.overseaTemplateCode = overseaTemplateCode;
    }

    @Override
    public String toString() {
        return "AliSmsProperties{" +
                "accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", signName='" + signName + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", overseaSignName='" + overseaSignName + '\'' +
                ", overseaTemplateCode='" + overseaTemplateCode + '\'' +
                '}';
    }

}
