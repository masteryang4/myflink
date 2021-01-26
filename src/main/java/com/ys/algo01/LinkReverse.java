package com.ys.algo01;

/**
 * 链表原地反转
 */
public class LinkReverse {
    public static void revese(LinkofMine l) {
        LinkofMine pre = null;
        LinkofMine curr = l;

        while (curr != null) {

        }
    }

    public static void main(String[] args) {
        LinkofMine l4 = new LinkofMine(4, null);
        LinkofMine l3 = new LinkofMine(3, l4);
        LinkofMine l2 = new LinkofMine(2, l3);
        LinkofMine l1 = new LinkofMine(1, l2);

        LinkofMine next = l1;
        while (next != null) {
            System.out.print(next.node + "->");
            next = next.next;
        }
        System.out.println();

        revese(l1);

        next = l4;
        while (next != null) {
            System.out.print(next.node + "->");
            next = next.next;
        }
        System.out.println();
    }
}

class LinkofMine {
    int node;
    LinkofMine next;

    public LinkofMine() {
    }

    public LinkofMine(int node, LinkofMine next) {
        this.node = node;
        this.next = next;
    }
}
