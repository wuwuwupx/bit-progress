package com.bitprogress.core;

import com.bitprogress.basemodel.tree.TreeNode;
import com.bitprogress.core.model.TreeVO;
import com.bitprogress.util.TreeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;


class TreeUtilsTest {

    private static List<Node> nodes;

    public static void main(String[] args) {
        setUp();
        testBuildTreeWithFunctions();
        testBuildTreeWithRootPredicate();
        testBuildTreeWithChildrenFilterFunction();
    }

    static void setUp() {
        nodes = Arrays.asList(
                new Node(1L, 0L, "Root"),
                new Node(2L, 1L, "Child 1"),
                new Node(3L, 1L, "Child 2"),
                new Node(4L, 2L, "Grandchild 1"),
                new Node(5L, 2L, "Grandchild 2"),
                new Node(6L, 3L, "Grandchild 3")
        );
    }

    static void testBuildTreeWithFunctions() {
        List<TreeNode> tree = TreeUtils.buildTree(nodes,
                node -> new TreeVO(node.getId(), node.getName()),
                Node::getId,
                Node::getParentId);
        System.out.println(tree);
    }

    static void testBuildTreeWithRootPredicate() {
        Predicate<Node> rootPredicate = node -> node.getParentId() == 0;
        List<TreeNode> tree = TreeUtils.buildTree(nodes,
                rootPredicate,
                node -> new TreeVO(node.getId(), node.getName()),
                Node::getId,
                Node::getParentId);
        System.out.println(tree);
    }

    static void testBuildTreeWithChildrenFilterFunction() {
        BiFunction<Node, Node, Boolean> childrenFilterFunction = (parent, child) -> Objects.equals(parent.getId(), child.getParentId());
        List<TreeNode> tree = TreeUtils.buildTree(nodes,
                node -> node.getParentId() == 0,
                node -> new TreeVO(node.getId(), node.getName()),
                childrenFilterFunction);
        System.out.println(tree);

    }

    static class Node {
        private Long id;
        private Long parentId;
        private String name;

        public Node(Long id, Long parentId, String name) {
            this.id = id;
            this.parentId = parentId;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public Long getParentId() {
            return parentId;
        }

        public String getName() {
            return name;
        }
    }
}
