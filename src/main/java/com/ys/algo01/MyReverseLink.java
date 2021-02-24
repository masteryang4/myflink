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

//        next = reverse_diedai(l1); //1 2 3 4 5 6
//        next = reverse_digui(l1); //1 2 3 4 5 6
//        next = reverse_diedai_atob(l2, l5); //4 3 2
//        next = reverse_kgroup(l1, 2); //2 1 4 3 6 5
//        next = reverse_ton(l1, 4); //4321 56
        next = reverse_mton(l1, 2, 5); //1 5432 6

        while (next != null) {
            System.out.print(next.node + " ");
            next = next.next;
        }
    }

    private static LinkofMine reverse_mton(LinkofMine l1, int m, int n) {
        if (m == 1) {
            return reverse_ton(l1, n);
        }
        l1.next = reverse_mton(l1.next, m - 1, n - 1);
        return l1;
    }

    private static LinkofMine nxt = null;

    private static LinkofMine reverse_ton(LinkofMine l1, int n) {
        if (n == 1) {
            nxt = l1.next;
            return l1;
        }
        LinkofMine newHead = reverse_ton(l1.next, n - 1);
        l1.next.next = l1;
        l1.next = nxt;

        return newHead;
    }

    private static LinkofMine reverse_kgroup(LinkofMine node, int k) {
        if (node == null) {
            return null;
        }
        LinkofMine b = node;
        int i = k;
        while (i > 0) {
            if (b == null) {
                return node;
            }
            b = b.next;
            i--;
        }
        LinkofMine newHead = reverse_diedai_atob(node, b);

        node.next = reverse_kgroup(b, k);
        return newHead;
    }

    private static LinkofMine reverse_diedai_atob(LinkofMine l2, LinkofMine l5) { //左闭右开
        LinkofMine pre = null;
        LinkofMine curr = l2;
        while (curr != l5) {
            LinkofMine nxt = curr.next;
            curr.next = pre;
            pre = curr;
            curr = nxt;
        }
        return pre;
    }

    private static LinkofMine reverse_digui(LinkofMine root) {
        if (root.next == null) { //base case
            return root;
        }
        LinkofMine newHead = reverse_digui(root.next);
        root.next.next = root;
        root.next = null;
        return newHead;
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
