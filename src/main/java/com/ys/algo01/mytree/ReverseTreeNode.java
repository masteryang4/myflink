package com.ys.algo01.mytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode226
 * 1.反转二叉树（镜像反转）
 * 递归
 * 【悟】二叉树递归相关的问题，都直接想象成[三个节点]的二叉树就简单多了
 * <p>
 * 2.层序遍历二叉树（借助队列）
 */
public class ReverseTreeNode {

    //使用队列，进行层序遍历二叉树（从上到下，从左到右）
    private static void cengxubianli_tree(MyTree root) {
        Queue<MyTree> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            MyTree treenode = queue.poll();
            System.out.print(treenode.value + " ");
            if (treenode.left != null) {
                queue.offer(treenode.left);
            }
            if (treenode.right != null) {
                queue.offer(treenode.right);
            }
        }
    }

    //反转二叉树（可以前序、后序，不能中序：相当于左子树反转了两次）
    private static void reverse_tree(MyTree root) {
        if (root == null) {
            return;
        }
        MyTree tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        reverse_tree(root.left);
        reverse_tree(root.right);
    }

    public static void main(String[] args) {
        MyTree t1 = new MyTree(1);
        MyTree t2 = new MyTree(2);
        MyTree t3 = new MyTree(3);
        MyTree t4 = new MyTree(4);
        MyTree t5 = new MyTree(5);
        MyTree t6 = new MyTree(6);
        MyTree t7 = new MyTree(7);

        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.left = t6;
        t3.right = t7;
        MyTree root = t1;

        cengxubianli_tree(root); //1 2 3 4 5 6 7
        System.out.println();

        reverse_tree(root);
        cengxubianli_tree(root); //1 3 2 7 6 5 4
    }
}
