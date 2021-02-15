package com.ys.algo01.mytree;

import java.util.*;

/**
 * LeetCode652.
 * 寻找重复子树
 * （包含 序列化一个二叉树）
 * https://mp.weixin.qq.com/s/LJbpo49qppIeRs-FbgjsSQ
 */
public class RepeatSubTree {

    private static void level(MyTree root) {
        Queue<MyTree> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            MyTree poll = queue.poll();
            System.out.print(poll.value + " ");  //1 2 3 4 2 4 4
            if (poll.left != null) {
                queue.offer(poll.left);
            }
            if (poll.right != null) {
                queue.offer(poll.right);
            }
        }
    }

    private static List<Integer> findDuplicateSubtrees(MyTree root) {
        if (root == null) {
            return null;
        }
        String s = tree_toString(root);
        System.out.println(s); //序列化结果：#,#,4,#,2,#,#,4,#,2,#,#,4,3,1

        return nodelist;
    }

    //【思路】寻找重复子树：就要知道自己什么样（序列化），知道别人什么样（将序列化结果存起来），进行比较
    private static Map<String, Integer> strmap = new HashMap<>(); //记录每个二叉树序列及其对应的个数

    private static List<Integer> nodelist = new LinkedList<>(); //存储重复的二叉树根节点value

    private static String tree_toString(MyTree root) { //后序遍历，将一个二叉树序列化
        if (root == null) {
            return "#";
        }
        String leftstr = tree_toString(root.left);
        String rightstr = tree_toString(root.right);

        String mytree = leftstr + "," + rightstr + "," + root.value;//整个树的序列化，描述了【自己是什么样子】

        Integer num = strmap.getOrDefault(mytree, 0);

        if (num == 1) {
            nodelist.add(root.value);
        }
        strmap.put(mytree, num + 1);

        return mytree;
    }

    public static void main(String[] args) {
        MyTree t1 = new MyTree(1);
        MyTree t2 = new MyTree(2);
        MyTree t3 = new MyTree(3);
        MyTree t4 = new MyTree(4);
        MyTree t5 = new MyTree(2);
        MyTree t6 = new MyTree(4);
        MyTree t7 = new MyTree(4);

        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t3.left = t5;
        t3.right = t6;
        t5.left = t7;

        level(t1);
        System.out.println();

        List<Integer> duplicateSubtreeslist = findDuplicateSubtrees(t1);

        System.out.println(duplicateSubtreeslist); //列表可以直接打印
    }
}
