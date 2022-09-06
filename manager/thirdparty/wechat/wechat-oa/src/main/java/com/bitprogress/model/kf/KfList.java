package com.bitprogress.model.kf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bitprogress.model.WechatResult;

import java.util.List;

/**
 * @author wuwuwupx
 *  客服列表
 */
public class KfList extends WechatResult {

    @JsonProperty("kf_list")
    private List<Kf> kfList;

    public List<Kf> getKfList() {
        return kfList;
    }

    public void setKfList(List<Kf> kfList) {
        this.kfList = kfList;
    }

}
