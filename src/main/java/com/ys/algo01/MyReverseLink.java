package com.ys.algo01;

public class MyReverseLink {

    public static void main(String[] args) {
        LinkofMine l6 = new LinkofMine(6, null);
        LinkofMine l5 = new LinkofMine(5, l6);
        LinkofMine l4 = new LinkofMine(4, l5);
        LinkofMine l3 = new LinkofMine(3, l4);
        LinkofMine l2 = new LinkofMine(2, l3);
        LinkofMine l1 = new LinkofMine(1, l2);

        LinkofMine next = l1;
        while (next != null) {
            System.out.print(next.node + " ");
            next = next.next;
        }

        System.out.println();
        System.out.println("==============>>>");

        next = reverse_diedai(l1);
        while (next != null) {
            System.out.print(next.node + " ");
            next = next.next;
        }
    }

    private static LinkofMine reverse_diedai(LinkofMine node) {
        LinkofMine pre = null;
        LinkofMine curr = node;
        while (curr != null) {
            LinkofMine nxt = curr.next;
            curr.next = pre;
            pre = curr;
            curr = nxt;
        }
        return pre;
    }
}
