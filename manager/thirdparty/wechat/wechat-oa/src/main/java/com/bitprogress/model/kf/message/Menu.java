package com.bitprogress.model.kf.message;

/**
 * @author wuwuwupx
 *  菜单消息菜单项
 */
public class Menu {

    /**
     * 菜单项ID
     */
    private Integer id;

    /**
     * 菜单内容
     */
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
