package com.bitprogress.model.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wuwuwupx
 */
public class MenuDeleteDTO {

    @JsonProperty("menuid")
    private String menuId;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "MenuDeleteDTO{" +
                "menuId='" + menuId + '\'' +
                '}';
    }

}
