package com.bitprogress.model.menu.vo.selfmenuinfo;

import java.util.List;

/**
 * @author wuwuwupx
 */
public class NewsInfo {

    /**
     * 多条图文
     */
    private List<News> list;

    public List<News> getList() {
        return list;
    }

    public void setList(List<News> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "NewsInfo{" +
                "list=" + list +
                '}';
    }

}
