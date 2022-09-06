package com.bitprogress.property;

/**
 * @author wuwuwupx
 * @desc: 服务信息
 */
public class ServiceProperties {

    /**
     * 接口全限名
     */
    private String interfaceName;

    /**
     * 版本
     */
    private String version;

    private String ref;

    /**
     * 注册到的注册中心ID
     */
    private String registryIds;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRegistryIds() {
        return registryIds;
    }

    public void setRegistryIds(String registryIds) {
        this.registryIds = registryIds;
    }

    @Override
    public String toString() {
        return "ServiceProperties{" +
                "name='" + interfaceName + '\'' +
                ", version='" + version + '\'' +
                ", ref='" + ref + '\'' +
                ", registryIds='" + registryIds + '\'' +
                '}';
    }

}
