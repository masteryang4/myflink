package com.ys.algo01;

public class LinkReverse003 {

    private static LinkofMine reverse_diedai(LinkofMine node) {
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

        next = reverse_diedai(l1);
        while (next != null) {
            System.out.println(next.node);
            next = next.next;
        }
    }

}
