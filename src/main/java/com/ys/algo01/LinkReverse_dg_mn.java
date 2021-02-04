package com.ys.algo01;

/**
 * 反转链表的【一部分】——【递归】实现
 */
public class LinkReverse_dg_mn {
    private static LinkofMine realnext = null;

    private static LinkofMine reverse_dg_n(LinkofMine node, int n) { //(m,n)是2维变量，先固定一个变量，从（1，n）一维变量开始
        if (n == 1) {
            realnext = node.next;
            return node;
        }
        LinkofMine last = reverse_dg_n(node.next, n - 1);
        node.next.next = node;
        node.next = realnext;
        return last;
    }

    private static LinkofMine reverse_dg_mn(LinkofMine node, int m, int n) { //二维基于一维
        if (m == 1) {
            return reverse_dg_n(node, n);
        }
        node.next = reverse_dg_mn(node.next, m - 1, n - 1);
        return node;
    }

    public static void main(String[] args) {
        LinkofMine l6 = new LinkofMine(6, null);
        LinkofMine l5 = new LinkofMine(5, l6);
        LinkofMine l4 = new LinkofMine(4, l5);
        LinkofMine l3 = new LinkofMine(3, l4);
        LinkofMine l2 = new LinkofMine(2, l3);
        LinkofMine l1 = new LinkofMine(1, l2);

        LinkofMine next = l1;
        while (next != null) {
            System.out.println(next.node);
            next = next.next;
        }
        System.out.println("==============>>>");

        next = reverse_dg_mn(l1, 2, 4);  //1,4,3,2,5,6
//        next = reverse_dg_n(l1, 3);  //n=3；output:3,2,1,4,5,6

        while (next != null) {
            System.out.println(next.node);
            next = next.next;
        }
    }
}
