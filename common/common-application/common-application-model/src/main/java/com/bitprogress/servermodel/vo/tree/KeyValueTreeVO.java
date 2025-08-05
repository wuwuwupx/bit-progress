package com.bitprogress.servermodel.vo.tree;

import com.bitprogress.basemodel.tree.TreeNode;
import com.bitprogress.servermodel.vo.kv.KeyValueVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValueTreeVO<K, V> extends KeyValueVO<K, V> implements TreeNode {

    private List<KeyValueTreeVO<K, V>> children;

    /**
     * 设置子节点
     *
     * @param children 子节点
     */
    @Override
    public void setChildren(List<? extends TreeNode> children) {
        this.children = (List<KeyValueTreeVO<K, V>>) children;
    }

}
