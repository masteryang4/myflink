package com.ys.algo01.mytree;

import java.util.LinkedList;
import java.util.List;

/**
 * 1.对一个二叉树进行前序序列化（根-左-右）。
 * 2.并根据前序序列化结果，将其还原回一个二叉树。
 */
public class XuliehuaTree {

    private static List<String> treelist = new LinkedList<>();

    /**
     * 二叉树 前序序列化 将二叉树序列化成一个列表，空节点按“#”处理
     *
     * @param root
     * @return
     */
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

    /**
     * 反序列化二叉树，将一个字符串（前序）还原回二叉树，【构建】二叉树
     *
     * @param str = "1, 2, 4, #, #, 5, #, #, 3, #, 6, #, #"
     * @return
     */
    private static LinkedList<String> mytreelist = new LinkedList<>(); //【重点】LinkedList可以动态变化列表的长度，取一个就少一个

    private static MyTree fanxuliehua(String str) {
        String[] split = str.split(", "); //【注意】","+" "

        for (String s : split) {
            mytreelist.add(s);
        }

        return build(mytreelist);
    }

    private static MyTree build(LinkedList<String> mytreelist) {
        if (mytreelist == null) {
            return null;
        }

        String first = mytreelist.removeFirst(); //【重点】是removeFirst，使得list是动态的
        if (first.equals("#")) {
            return null;
        }
        MyTree root = new MyTree(Integer.parseInt(first));

        root.left = build(mytreelist);
        root.right = build(mytreelist);

        return root;
    }


    public static void main(String[] args) {
        /*
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

         */

//        List<String> mytree = xuliehua(t1);

//        System.out.println(mytree); //[1, 2, 4, #, #, 5, #, #, 3, #, 6, #, #]

        MyTree root = fanxuliehua("1, 2, 4, #, #, 5, #, #, 3, #, 6, #, #");
//        System.out.println(root.value); //1

        List<String> xuliehua = xuliehua(root);
        System.out.println(xuliehua); //[1, 2, 4, #, #, 5, #, #, 3, #, 6, #, #]

    }
}
