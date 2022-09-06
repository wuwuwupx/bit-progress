package com.bitprogress.model.pay;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 * 继承SceneInfo 多了H5场景信息
 */
public class H5SceneInfo extends SceneInfo {

    /**
     * H5场景信息
     */
    @JsonProperty("h5_info")
    private H5Info h5Info;

    public H5Info getH5Info() {
        return h5Info;
    }

    public H5SceneInfo setH5Info(H5Info h5Info) {
        this.h5Info = h5Info;
        return this;
    }

}
