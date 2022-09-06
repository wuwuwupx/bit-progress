package com.bitprogress.model.menu.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bitprogress.model.WechatResult;

import java.util.List;

/**
 * @author wuwuwupx
 */
public class MenuVO extends WechatResult {

    /**
     * 普通菜单
     */
    private ButtonVO menu;

    /**
     * 自定义菜单
     */
    @JsonProperty("conditionalmenu")
    private List<ButtonVO> conditionalMenu;

    public ButtonVO getMenu() {
        return menu;
    }

    public void setMenu(ButtonVO menu) {
        this.menu = menu;
    }

    public List<ButtonVO> getConditionalMenu() {
        return conditionalMenu;
    }

    public void setConditionalMenu(List<ButtonVO> conditionalMenu) {
        this.conditionalMenu = conditionalMenu;
    }

    @Override
    public String toString() {
        return "MenuVO{" +
                "menu=" + menu +
                ", conditionalMenu=" + conditionalMenu +
                '}';
    }

}
