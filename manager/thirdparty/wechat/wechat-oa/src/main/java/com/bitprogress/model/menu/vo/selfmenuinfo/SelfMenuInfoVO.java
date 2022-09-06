package com.bitprogress.model.menu.vo.selfmenuinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bitprogress.model.WechatResult;

/**
 * @author wuwuwupx
 */
public class SelfMenuInfoVO extends WechatResult {

    /**
     * 菜单是否开启，0代表未开启，1代表开启
     */
    @JsonProperty("is_menu_open")
    private Integer isMenuOpen;
    /**
     * 菜单信息
     */
    @JsonProperty("selfmenu_info")
    private SelfMenuInfo selfMenuInfo;

    public Integer getIsMenuOpen() {
        return isMenuOpen;
    }

    public void setIsMenuOpen(Integer isMenuOpen) {
        this.isMenuOpen = isMenuOpen;
    }

    public SelfMenuInfo getSelfMenuInfo() {
        return selfMenuInfo;
    }

    public void setSelfMenuInfo(SelfMenuInfo selfMenuInfo) {
        this.selfMenuInfo = selfMenuInfo;
    }

    @Override
    public String toString() {
        return "SelfMenuInfoVO{" +
                "isMenuOpen=" + isMenuOpen +
                ", selfMenuInfo=" + selfMenuInfo +
                '}';
    }

}
