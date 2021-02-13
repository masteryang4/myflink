package com.ys.algo01.mytree;

/**
 * 遍历二叉树（递归）
 * 左序、中序、后序
 * 【递归思维】
 */
public class BianliTree {
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

        zuoxu_bianli(root);//124536
        System.out.println();

        zhongxu_bianli(root);//425163
        System.out.println();

        houxu_bianli(root);//452631
        System.out.println();
    }

    private static void zuoxu_bianli(MyTree root) {
        if (root == null) {
            return;
        }
        System.out.print(root.value + " ");
        zuoxu_bianli(root.left);
        zuoxu_bianli(root.right);
    }

    private static void zhongxu_bianli(MyTree root) {
        if (root == null) {
            return;
        }
        zhongxu_bianli(root.left);
        System.out.print(root.value + " ");
        zhongxu_bianli(root.right);
    }

    private static void houxu_bianli(MyTree root) {
        if (root == null) {
            return;
        }
        houxu_bianli(root.left);
        houxu_bianli(root.right);
        System.out.print(root.value + " ");
    }
}

class MyTree {
    public int value;
    public MyTree left;
    public MyTree right;

    public MyTree(int value) {
        this.value = value;
    }

    public MyTree(int value, MyTree left, MyTree right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}
