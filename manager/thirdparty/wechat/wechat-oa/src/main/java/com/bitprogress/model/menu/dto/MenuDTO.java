package com.bitprogress.model.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bitprogress.model.menu.Button;
import com.bitprogress.model.menu.MatchRule;

import java.util.List;

/**
 * @author wuwuwupx
 */
public class MenuDTO {

    /**
     * 菜单列表
     */
    private List<Button> button;

    /**
     * 菜单匹配规则
     */
    @JsonProperty("matchrule")
    private MatchRule matchRule;

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

    @Override
    public String toString() {
        return "MenuDTO{" +
                "button=" + button +
                ", matchRule=" + matchRule +
                '}';
    }

}
