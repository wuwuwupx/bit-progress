package com.bitprogress.model.menu.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bitprogress.model.menu.Button;
import com.bitprogress.model.menu.MatchRule;

import java.util.List;

/**
 * @author wuwuwupx
 */
public class ButtonVO {

    /**
     * 菜单列表
     */
    private List<Button> button;

    /**
     * 匹配规则
     */
    @JsonProperty("matchrule")
    private MatchRule matchRule;

    /**
     * 菜单ID
     */
    @JsonProperty("menuid")
    private String menuId;

    public List<Button> getButton() {
        return button;
    }

    public void setButton(List<Button> button) {
        this.button = button;
    }

    public MatchRule getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(MatchRule matchRule) {
        this.matchRule = matchRule;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "ButtonVO{" +
                "button=" + button +
                ", matchRule=" + matchRule +
                ", menuId='" + menuId + '\'' +
                '}';
    }

}
