package com.ys.algo01.mytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode105.通过前序和中序遍历结果构造二叉树
 */
public class MakeTreeQianandZhong {

    private static void level(MyTree root) {
        Queue<MyTree> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            MyTree poll = queue.poll();
            System.out.println(poll.value);
            if (poll.left != null) {
                queue.offer(poll.left);
            }
            if (poll.right != null) {
                queue.offer(poll.right);
            }
        }
    }

    private static MyTree makeTree(int[] prearr, int[] midarr) {
        return null;
    }

    public static void main(String[] args) {
        int[] prearr = {3, 9, 20, 15, 7}; //前序遍历结果
        int[] midarr = {9, 3, 15, 20, 7}; //中序遍历结果

        MyTree root = makeTree(prearr, midarr);

        level(root);
    }
}
