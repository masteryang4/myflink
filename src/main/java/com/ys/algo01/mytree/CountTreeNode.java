package com.ys.algo01.mytree;

/**
 * 计算二叉树节点个数（递归）
 * 【递归思维】
 */
public class CountTreeNode {

    private static int count_node(MyTree root) {
        if (root == null) { //返回条件
            return 0;
        }
        return 1 + count_node(root.left) + count_node(root.right); //根节点数（1）+左子树节点数+右子树节点数
    }

    public static void main(String[] args) {
        MyTree t1 = new MyTree(1);
        MyTree t2 = new MyTree(2);
        MyTree t3 = new MyTree(3);
        MyTree t4 = new MyTree(4);
        MyTree t5 = new MyTree(5);
        MyTree t6 = new MyTree(6);

        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.left = t6;
        MyTree root = t1;

        System.out.println(count_node(root));
    }
}
