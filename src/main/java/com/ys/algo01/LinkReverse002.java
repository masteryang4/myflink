package com.ys.algo01;

/**
 * 反转链表——迭代实现
 * 时间复杂度O(n)
 * 空间复杂度O(1)
 */
public class LinkReverse002 {
    public static LinkofMine reverse(LinkofMine node) {
        LinkofMine pre = null;
        LinkofMine curr = node;
        LinkofMine next = null;

        while (curr != null) {
            next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        LinkofMine l4 = new LinkofMine(4, null);
        LinkofMine l3 = new LinkofMine(3, l4);
        LinkofMine l2 = new LinkofMine(2, l3);
        LinkofMine l1 = new LinkofMine(1, l2);

        LinkofMine next = l1;
        while (next != null) {
            System.out.println(next.node);
            next = next.next;
        }
        System.out.println("==============>>>");

        next = reverse(l1);
        while (next != null) {
            System.out.println(next.node);
            next = next.next;
        }
    }
}
