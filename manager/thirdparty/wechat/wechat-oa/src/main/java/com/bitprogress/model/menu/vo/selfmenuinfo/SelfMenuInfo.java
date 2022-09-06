package com.bitprogress.model.menu.vo.selfmenuinfo;

import java.util.List;

/**
 * @author wuwuwupx
 */
public class SelfMenuInfo {

    private List<SelfButton> button;

    public List<SelfButton> getButton() {
        return button;
    }

    public void setButton(List<SelfButton> button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "SelfMenuInfo{" +
                "button=" + button +
                '}';
    }

}
