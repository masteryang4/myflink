package com.ys.algo01.mytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode105.
 * 通过前序和中序遍历结果【构造】二叉树
 * 1.前序遍历的的第一个数就是根节点
 * 2.通过中序遍历找到的根节点的索引，就可以确定其左子树和右子树的长度
 * <p>
 * 构造二叉树：
 * 找到根节点，对其左子树、右子树进行递归
 */
public class MakeTreeQianandZhong {

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

    private static MyTree makeTree(int[] prearr, int[] midarr) {
        return pre_mid_maketree(prearr, 0, prearr.length - 1, midarr, 0, midarr.length - 1);
    }

    private static MyTree pre_mid_maketree(int[] prearr, int preleft, int preright, int[] midarr, int midleft, int midright) {
        //base case
        if (preleft > preright || midleft > midright) {
            return null;
        }

        int rootval = prearr[preleft];
        MyTree root = new MyTree(rootval); //前序遍历的的第一个数就是根节点

        int index = -1; //中序遍历中，根节点的索引
        for (int i = midleft; i <= midright; i++) {
            if (midarr[i] == rootval) {
                index = i;
                break;
            }
        }

        int leftsize = index - midleft;//【重点】左子树的长度
        //通过中序遍历找到的根节点的索引，就可以确定其左子树和右子树的【长度】
        root.left = pre_mid_maketree(prearr, preleft + 1, preleft + leftsize, midarr, midleft, midleft + leftsize - 1);
        root.right = pre_mid_maketree(prearr, preleft + leftsize + 1, preright, midarr, midleft + leftsize + 1, midright);

//        System.out.println(root.value);
        return root;
    }

    public static void main(String[] args) {
        int[] prearr = {3, 9, 20, 15, 7}; //前序遍历结果
        int[] midarr = {9, 3, 15, 20, 7}; //中序遍历结果

        MyTree root = makeTree(prearr, midarr);

        level(root);
    }
}
