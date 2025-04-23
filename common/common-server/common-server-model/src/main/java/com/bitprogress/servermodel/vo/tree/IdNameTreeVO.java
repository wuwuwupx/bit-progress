package com.bitprogress.servermodel.vo.tree;

import com.bitprogress.basemodel.tree.TreeNode;
import com.bitprogress.servermodel.vo.IdNameVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdNameTreeVO extends IdNameVO implements TreeNode {

    private List<IdNameTreeVO> children;

    /**
     * 设置子节点
     *
     * @param children 子节点
     */
    @Override
    public void setChildren(List<? extends TreeNode> children) {
        this.children = (List<IdNameTreeVO>) children;
    }

}
