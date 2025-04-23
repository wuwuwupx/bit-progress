package com.bitprogress.basemodel.tree;

import java.util.List;

public interface TreeNode {

    /**
     * 设置子节点
     *
     * @param children 子节点
     */
    void setChildren(List<? extends TreeNode> children);

}
