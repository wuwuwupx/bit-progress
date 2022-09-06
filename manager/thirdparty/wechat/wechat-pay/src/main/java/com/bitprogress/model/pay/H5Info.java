package com.bitprogress.model.pay;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 * H5场景信息
 */
public class H5Info {

    /**
     * 必填
     * 场景类型 示例值：iOS, Android, Wap
     * string[1,32]
     */
    private String type;

    /**
     * 非必填
     * 应用名称
     * string[1,64]
     */
    @JsonProperty("app_name")
    private String appName;

    /**
     * 非必填
     * 网站URL
     * string[1,128]
     */
    @JsonProperty("app_url")
    private String appUrl;

    /**
     * 非必填
     * iOS平台BundleID 示例值：com.tencent.wzryiOS
     * string[1,128]
     */
    @JsonProperty("bundle_id")
    private String bundleId;

    /**
     * 非必填
     * Android平台PackageName 示例值：com.tencent.tmgp.sgame
     * string[1,128]
     */
    @JsonProperty("package_name")
    private String packageName;

    public String getType() {
        return type;
    }

    public H5Info setType(String type) {
        this.type = type;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public H5Info setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public H5Info setAppUrl(String appUrl) {
        this.appUrl = appUrl;
        return this;
    }

    public String getBundleId() {
        return bundleId;
    }

    public H5Info setBundleId(String bundleId) {
        this.bundleId = bundleId;
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public H5Info setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

}
