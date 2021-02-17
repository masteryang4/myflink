package com.ys.algo01.mytree;

import java.util.LinkedList;
import java.util.List;

/**
 * 1.对一个二叉树进行前序序列化（根-左-右）。
 * 2.并根据前序序列化结果，将其还原回一个二叉树。
 */
public class XuliehuaTree {

    private static List<String> treelist = new LinkedList<>();

    private static List<String> xuliehua(MyTree root) {
        if (root == null) {
            treelist.add("#");
            return treelist;
        }
        treelist.add(root.value + "");

        xuliehua(root.left);
        xuliehua(root.right);

        return treelist;
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
        t3.right = t6;

        List<String> mytree = xuliehua(t1);

        System.out.println(mytree); //[1, 2, 4, #, #, 5, #, #, 3, #, 6, #, #]
    }
}
