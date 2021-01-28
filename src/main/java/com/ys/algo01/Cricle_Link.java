package com.ys.algo01;

/**
 * 判断一个链表是否有环
 */
public class Cricle_Link {
    public static boolean isCricle(LinkofMine node) {
        LinkofMine p1 = node; //p1走一步
        LinkofMine p2 = node; //p2走两步

        while (p2 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
            if (p1 == p2) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        LinkofMine l5 = new LinkofMine(5, null);
        LinkofMine l4 = new LinkofMine(4, l5);
        LinkofMine l3 = new LinkofMine(3, l4);
        LinkofMine l2 = new LinkofMine(2, l3);
        LinkofMine l1 = new LinkofMine(1, l2);

        l5.next = l2;//控制链表是否有环

        System.out.println(isCricle(l1));
    }
}
