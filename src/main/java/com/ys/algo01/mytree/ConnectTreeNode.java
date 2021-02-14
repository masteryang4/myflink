package com.ys.algo01.mytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode116.
 * 填充二叉树节点的右侧指针
 * 给定一个完美二叉树，所有叶子节点都在同一层，每个父节点都有两个子节点。
 * 填充它的每个next指针，让这个指针指向其下一个右侧节点。
 * 【重点】
 *   二叉树的问题难点在于，如何把题目的要求细化成【每个节点】需要做的事情
 */
public class ConnectTreeNode {

    private static void level(MyTreeNode root) { //层序遍历
        Queue<MyTreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            MyTreeNode poll = queue.poll();
            System.out.print(poll.value + " ");

            if (poll.left != null) {
                queue.offer(poll.left);
            }
            if (poll.right != null) {
                queue.offer(poll.right);
            }
        }
    }

    private static void connectTwoNodes(MyTreeNode leftnode, MyTreeNode rightnode) {
        if (leftnode == null || rightnode == null) {
            return;
        }
        leftnode.next = rightnode;
        connectTwoNodes(leftnode.left, leftnode.right);
        connectTwoNodes(rightnode.left, rightnode.right);
        connectTwoNodes(leftnode.right, rightnode.left);
    }

    public static void main(String[] args) {
        MyTreeNode t1 = new MyTreeNode(1);
        MyTreeNode t2 = new MyTreeNode(2);
        MyTreeNode t3 = new MyTreeNode(3);
        MyTreeNode t4 = new MyTreeNode(4);
        MyTreeNode t5 = new MyTreeNode(5);
        MyTreeNode t6 = new MyTreeNode(6);
        MyTreeNode t7 = new MyTreeNode(7);

        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.left = t6;
        t3.right = t7;
        MyTreeNode root = t1;

        level(root);
        System.out.println("---------");

        connectTwoNodes(root.left, root.right);

        System.out.println(t1.next);
        System.out.println(t2.next.value);
        System.out.println(t3.next);
        System.out.println(t4.next.value);
        System.out.println(t5.next.value);
        System.out.println(t6.next.value);
        System.out.println(t7.next);
    }

}

class MyTreeNode {
    public int value;
    public MyTreeNode left;
    public MyTreeNode right;
    public MyTreeNode next;

    public MyTreeNode(int value) {
        this.value = value;
    }
}