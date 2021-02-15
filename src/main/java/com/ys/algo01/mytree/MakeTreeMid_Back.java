package com.ys.algo01.mytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode106.
 * 通过后序和中序遍历结果构造二叉树
 * https://mp.weixin.qq.com/s/OlpaDhPDTJlQ5MJ8tsARlA
 */
public class MakeTreeMid_Back {

    private static void level(MyTree root) {
        Queue<MyTree> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            MyTree poll = queue.poll();
            System.out.print(poll.value + " ");  //3,9,20,15,7
            if (poll.left != null) {
                queue.offer(poll.left);
            }
            if (poll.right != null) {
                queue.offer(poll.right);
            }
        }
    }

    private static MyTree makeTree(int[] midarr, int[] backarr) {
        return build(midarr, 0, midarr.length - 1, backarr, 0, backarr.length - 1);
    }

    private static MyTree build(int[] midarr, int mleft, int mright, int[] backarr, int bleft, int bright) {
        if (mleft > mright || bleft > bright) {
            return null;
        }
        int rootval = backarr[bright];
        MyTree root = new MyTree(rootval);

        int index = -1; //【注意】是midarr的index
        for (int i = mleft; i <= mright; i++) {
            if (midarr[i] == rootval) {
                index = i;
                break;
            }
        }
        int leftsize = index - mleft;

        //因为index是midarr的index，所以只有midarr才可以使用index参数
        root.left = build(midarr, mleft, index - 1, backarr, bleft, bleft + leftsize - 1);
        root.right = build(midarr, index + 1, mright, backarr, bleft + leftsize, bright - 1);
        return root;
    }

    public static void main(String[] args) {
        int[] midarr = {9, 3, 15, 20, 7}; //中序遍历结果
        int[] backarr = {9, 15, 7, 20, 3}; //后序遍历结果

        MyTree root = makeTree(midarr, backarr);

        level(root);
    }
}
