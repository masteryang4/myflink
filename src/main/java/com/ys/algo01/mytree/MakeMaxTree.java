package com.ys.algo01.mytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode654.【构造】最大二叉树
 * https://labuladong.gitbook.io/algo/shu-ju-jie-gou-xi-lie/shou-ba-shou-shua-er-cha-shu-xun-lian-di-gui-si-wei/er-cha-shu-xi-lie-2
 * [完整版]https://mp.weixin.qq.com/s/OlpaDhPDTJlQ5MJ8tsARlA
 * <p>
 * 和快排有点类似（if (left > right){}其实就是快排递归的【base case】）
 */
public class MakeMaxTree {
    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 6, 0, 5};
        MyTree root = makemaxtree(arr, 0, arr.length - 1);

        level(root);
    }

    private static MyTree makemaxtree(int[] arr, int left, int right) {
        if (left > right) { //base case
            return null;
        }
        int maxval = Integer.MIN_VALUE;
        int maxindex = -1;

        for (int i = left; i <= right; i++) {
            if (arr[i] > maxval) {
                maxval = arr[i];
                maxindex = i;
            }
        }
        MyTree root = new MyTree(maxval);

        root.left = makemaxtree(arr, left, maxindex - 1);
        root.right = makemaxtree(arr, maxindex + 1, right);

        return root;
    }

    private static void level(MyTree root) { //层序遍历
        Queue<MyTree> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            MyTree poll = queue.poll();
            System.out.print(poll.value + " ");

            if (poll.left != null) {
                queue.offer(poll.left);
            }
            if (poll.right != null) {
                queue.offer(poll.right);
            }
        }
    }
}
