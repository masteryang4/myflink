package com.ys.algo01.mytree;

/**
 * LeetCode114.
 * 将二叉树转换成链表
 *       1              1
 *      / \             \
 *     2  5     ==>      2
 *    / \  \              \
 *   3  4   6              3
 *                          \
 *                           4
 *                            \
 *                             5
 *                              \
 *                               6
 */
public class TreeToLink {

    private static void treetolink(MyTree root) {
        if (root == null) {
            return;
        }
        treetolink(root.left);
        treetolink(root.right);

        //【后序遍历】先将左右子树转化成链表再进行操作
        MyTree left = root.left;
        MyTree right = root.right;

        root.left = null;
        root.right = left;

        MyTree p = root;
        while (p.right != null) {
            p = p.right;
        }
        p.right = right;
    }

    public static void main(String[] args) {
        MyTree t1 = new MyTree(1);
        MyTree t2 = new MyTree(2);
        MyTree t3 = new MyTree(5);
        MyTree t4 = new MyTree(3);
        MyTree t5 = new MyTree(4);
        MyTree t6 = new MyTree(6);

        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.right = t6;

        treetolink(t1);

        MyTree root = t1;
        while (root != null) {
            System.out.print(root.value + " ");
            root = root.right;
        }
    }
}
