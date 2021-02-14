package com.ys.algo01.mytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode654.【构造】最大二叉树
 */
public class MakeMaxTree {
    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 6, 0, 5};
        MyTree root = makemaxtree(arr, 0, arr.length - 1);

        level(root);
    }

    private static MyTree makemaxtree(int[] arr, int left, int right) {
        if (left > right) {
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
