package com.bitprogress.util;

import com.bitprogress.basemodel.tree.TreeNode;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class TreeUtils {

    /**
     * 构建树
     *
     * @param sources              源数据
     * @param convertFunction      转换函数
     * @param parentSignFunction   父节点标识函数，是用于获取当前节点作为父节点的标识的函数，一般是 node.getId()
     * @param childrenSignFunction 子节点标识函数，是用于获取当前节点作为子节点的标识的函数，一般是 node.getParentId()
     * @param <T>                  源数据类型
     * @param <E>                  树节点类型
     * @param <C>                  树节点标识类型
     * @return 树结构列表
     */
    public static <T, E extends TreeNode, C> List<E> buildTree(List<T> sources,
                                                               Function<T, E> convertFunction,
                                                               Function<T, C> parentSignFunction,
                                                               Function<T, C> childrenSignFunction) {
        if (CollectionUtils.isEmpty(sources)) {
            return CollectionUtils.emptyList();
        }
        Map<C, List<E>> childrenGroup = CollectionUtils.emptyMap();
        Map<C, List<E>> parentGroup = CollectionUtils.emptyMap();
        CollectionUtils.forEach(sources, source -> {
            E treeNode = convertFunction.apply(source);
            // 根据自身的 子节点标识函数 设置到对应的子节点集合中
            C childrenKey = childrenSignFunction.apply(source);
            List<E> children = CollectionUtils.getForMap(childrenGroup, childrenKey, CollectionUtils.emptyList());
            children.add(treeNode);
            childrenGroup.put(childrenKey, children);
            // 根据自身的 父节点标识函数
            C parentKey = parentSignFunction.apply(source);
            List<E> parents = CollectionUtils.getForMap(parentGroup, parentKey, CollectionUtils.emptyList());
            parents.add(treeNode);
            parentGroup.put(parentKey, parents);
        });
        List<E> roots = CollectionUtils.emptyList();
        childrenGroup.forEach((key, value) -> {
            // 用自身作为子节点的标识，去匹配父节点的标识
            List<E> parents = CollectionUtils.getForMap(parentGroup, key);
            if (CollectionUtils.isEmpty(parents)) {
                // 没有匹配上则表示没有父节点，可以做为根节点
                roots.addAll(value);
            } else {
                // 匹配上则设置到所有的父节点去
                parents.forEach(parent -> parent.setChildren(value));
            }
        });
        return roots;
    }

    /**
     * 构建树
     *
     * @param sources              源数据
     * @param rootPredicate        根节点判断
     * @param convertFunction      转换函数
     * @param parentSignFunction   父节点标识函数，是用于获取当前节点作为父节点的标识的函数，一般是 node.getId()
     * @param childrenSignFunction 子节点标识函数，是用于获取当前节点作为子节点的标识的函数，一般是 node.getParentId()
     * @param <T>                  源数据类型
     * @param <E>                  树节点类型
     * @param <C>                  树节点标识类型
     * @return 树结构列表
     */
    public static <T, E extends TreeNode, C> List<E> buildTree(List<T> sources,
                                                               Predicate<T> rootPredicate,
                                                               Function<T, E> convertFunction,
                                                               Function<T, C> parentSignFunction,
                                                               Function<T, C> childrenSignFunction) {
        return buildTree(sources, rootPredicate, convertFunction, (parent, current) ->
                Objects.equals(parentSignFunction.apply(parent), childrenSignFunction.apply(current)));
    }

    /**
     * 构建树
     *
     * @param sources                源数据
     * @param rootPredicate          根节点判断
     * @param convertFunction        转换函数
     * @param childrenFilterFunction 子节点过滤函数，判断父节点的父节点标识与当前节点的子节点标识是否一致
     *                               node.getId().equals(node.getParentId())
     * @param <T>                    源数据类型
     * @param <E>                    树节点类型
     * @return 树结构列表
     */
    public static <T, E extends TreeNode> List<E> buildTree(List<T> sources,
                                                            Predicate<T> rootPredicate,
                                                            Function<T, E> convertFunction,
                                                            BiFunction<T, T, Boolean> childrenFilterFunction) {
        if (CollectionUtils.isEmpty(sources)) {
            return CollectionUtils.emptyList();
        }
        List<T> roots = CollectionUtils.filterList(sources, rootPredicate);
        return CollectionUtils.toList(roots, root -> {
            E rootTree = convertFunction.apply(root);
            rootTree.setChildren(getChildren(sources, root, convertFunction, childrenFilterFunction));
            return rootTree;
        });
    }

    /**
     * 获取子节点
     *
     * @param sources                源数据
     * @param parent                 父节点
     * @param convertFunction        转换函数
     * @param childrenFilterFunction 子节点过滤函数，判断父节点的父节点标识与当前节点的子节点标识是否一致
     *                               node.getId().equals(node.getParentId())
     * @param <T>                    源数据类型
     * @param <E>                    树节点类型
     * @return 子节点列表
     */
    private static <T, E extends TreeNode> List<E> getChildren(List<T> sources,
                                                               T parent,
                                                               Function<T, E> convertFunction,
                                                               BiFunction<T, T, Boolean> childrenFilterFunction) {
        List<T> children = CollectionUtils.filterList(sources, source -> childrenFilterFunction.apply(parent, source));
        if (CollectionUtils.isEmpty(children)) {
            return CollectionUtils.emptyList();
        } else {
            return CollectionUtils.toList(children, current -> {
                E childrenTree = convertFunction.apply(current);
                childrenTree.setChildren(getChildren(sources, current, convertFunction, childrenFilterFunction));
                return childrenTree;
            });
        }
    }

}
