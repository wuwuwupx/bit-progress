package com.bitprogress.util;

import com.bitprogress.basemodel.tree.TreeNode;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
        if (CollectionUtils.isEmpty(sources)) {
            return CollectionUtils.emptyList();
        }
        Map<C, List<E>> childrenGroup = CollectionUtils.emptyMap();
        Map<C, List<E>> parentGroup = CollectionUtils.emptyMap();
        List<T> roots = CollectionUtils.emptyList();
        CollectionUtils.forEach(sources, source -> {
            if (rootPredicate.test(source)) {
                // 如果是根节点，则直接添加到根节点集合中，不继续进行处理，避免循环依赖
                roots.add(source);
                return;
            }
            // 转换节点
            E treeNode = convertFunction.apply(source);
            // 根据自身的 子节点标识函数 设置到对应的子节点集合中
            C childrenKey = childrenSignFunction.apply(source);
            List<E> children = CollectionUtils.getForMap(childrenGroup, childrenKey, CollectionUtils.emptyList());
            children.add(treeNode);
            childrenGroup.put(childrenKey, children);
            // 根据自身的 父节点标识函数 设置到对应的父节点集合中
            C parentKey = parentSignFunction.apply(source);
            List<E> parents = CollectionUtils.getForMap(parentGroup, parentKey, CollectionUtils.emptyList());
            parents.add(treeNode);
            parentGroup.put(parentKey, parents);
        });
        childrenGroup.forEach((key, value) -> {
            // 用自身作为子节点的标识，去匹配父节点的标识
            List<E> parents = CollectionUtils.getForMap(parentGroup, key);
            // 匹配上则设置到所有的父节点去
            CollectionUtils.forEach(parents, parent -> parent.setChildren(value));
        });
        return CollectionUtils.toList(roots, rootSource -> {
            E root = convertFunction.apply(rootSource);
            root.setChildren(CollectionUtils.getForMap(childrenGroup, parentSignFunction.apply(rootSource)));
            return root;
        });
    }

    /**
     * 构建树
     *
     * @param sources                源数据
     * @param rootPredicate          根节点判断
     * @param convertFunction        转换函数
     * @param childrenFilterFunction 子节点过滤函数，判断父节点的父节点标识与当前节点的子节点标识是否一致
     *                               Objects.equals(parentNode.getId(), node.getParentId())
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
        Map<T, List<E>> childrenGroup = CollectionUtils.emptyMap();
        Map<T, E> nodeMap = CollectionUtils.emptyMap();
        AtomicInteger index = new AtomicInteger(1);

        Supplier<List<E>> nodeListSupplier = CollectionUtils::emptyList;
        CollectionUtils.forEach(sources, source -> {
            Supplier<E> supplier = () -> convertFunction.apply(source);
            E treeNode = CollectionUtils.getForMap(nodeMap, source, supplier);
            nodeMap.put(source, treeNode);
            for (int i = index.getAndIncrement(); i < sources.size(); i++) {
                // 设置树节点
                T matchSource = sources.get(i);
                Supplier<E> eSupplier = () -> convertFunction.apply(matchSource);
                E matchNode = CollectionUtils.getForMap(nodeMap, matchSource, eSupplier);
                nodeMap.put(matchSource, matchNode);
                // 匹配当前节点是否是上一层节点的子节点
                if (childrenFilterFunction.apply(source, matchSource)) {
                    List<E> children = CollectionUtils.getForMap(childrenGroup, source, nodeListSupplier);
                    children.add(matchNode);
                    childrenGroup.put(source, children);
                } else {
                    if (rootPredicate.test(matchSource)) {
                        // 根节点不寻找父节点
                        continue;
                    }
                    // 匹配当前节点是否是上一层节点的父节点
                    if (childrenFilterFunction.apply(matchSource, source)) {
                        List<E> children = CollectionUtils.getForMap(childrenGroup, matchSource, nodeListSupplier);
                        children.add(treeNode);
                        childrenGroup.put(matchSource, children);
                    }
                }
            }
        });
        List<E> roots = CollectionUtils.emptyList();
        childrenGroup.forEach((key, value) -> {
            E treeNode = CollectionUtils.getForMap(nodeMap, key);
            if (rootPredicate.test(key)) {
                roots.add(treeNode);
            }
            if (Objects.nonNull(treeNode)) {
                treeNode.setChildren(value);
            }
        });
        return roots;
    }

}
