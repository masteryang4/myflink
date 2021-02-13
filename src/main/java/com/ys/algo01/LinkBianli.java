package com.ys.algo01;

/**
 * 单链表反向遍历（递归）
 * 正向遍历（迭代）
 */
public class LinkBianli {

    private static void bianli_reverse(LinkofMine l1) { //反向遍历（递归）
        if (l1 == null) {
            return;
        }
        bianli_reverse(l1.next);
        System.out.println(l1.node);
    }

    public static void main(String[] args) {
        LinkofMine l6 = new LinkofMine(6, null);
        LinkofMine l5 = new LinkofMine(5, l6);
        LinkofMine l4 = new LinkofMine(4, l5);
        LinkofMine l3 = new LinkofMine(3, l4);
        LinkofMine l2 = new LinkofMine(2, l3);
        LinkofMine l1 = new LinkofMine(1, l2);

        LinkofMine next = l1;
        while (next != null) { //正向遍历（迭代）
            System.out.println(next.node);
            next = next.next;
        }
        System.out.println("==============>>>");

        bianli_reverse(l1);
    }
}
