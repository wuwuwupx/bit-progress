package com.bitprogress.model.menu.vo;

import com.bitprogress.model.WechatResult;
import com.bitprogress.model.menu.Button;

import java.util.List;

/**
 * @author wuwuwupx
 */
public class ButtonTryMatchVO extends WechatResult {

    /**
     * 菜单列表
     */
    private List<Button> button;

    public List<Button> getButton() {
        return button;
    }

    public void setButton(List<Button> button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "ButtonTryMatchVO{" +
                "button=" + button +
                '}';
    }

}
