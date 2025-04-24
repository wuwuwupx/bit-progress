package com.bitprogress.core.model;

import com.bitprogress.basemodel.tree.TreeNode;
import lombok.Data;

import java.util.List;

@Data
public class TreeVO implements TreeNode {

    private Long id;
    private String name;

    private List<TreeVO> children;
    /**
     * 设置子节点
     *
     * @param children 子节点
     */
    @Override
    public void setChildren(List<? extends TreeNode> children) {
        this.children = (List<TreeVO>) children;
    }

    public TreeVO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
