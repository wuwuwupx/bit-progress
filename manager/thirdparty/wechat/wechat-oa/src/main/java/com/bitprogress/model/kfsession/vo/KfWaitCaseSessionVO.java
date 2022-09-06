package com.bitprogress.model.kfsession.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bitprogress.model.WechatResult;
import com.bitprogress.model.kfsession.KfWaitCaseSession;

import java.util.List;

/**
 * @author wuwuwupx
 *  未接入客服会话列表
 */
public class KfWaitCaseSessionVO extends WechatResult {

    /**
     * 未接入会话数量
     */
    private Integer count;

    /**
     * 未接入会话列表，最多返回100条数据，按照来访顺序
     */
    @JsonProperty("waitcaselist")
    private List<KfWaitCaseSession> waitCaseList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<KfWaitCaseSession> getWaitCaseList() {
        return waitCaseList;
    }

    public void setWaitCaseList(List<KfWaitCaseSession> waitCaseList) {
        this.waitCaseList = waitCaseList;
    }

}
