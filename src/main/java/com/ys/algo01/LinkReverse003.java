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

    private static LinkofMine reverse_digui(LinkofMine node) {
        if (node.next == null) { //递归返回条件
            return node;
        }
        LinkofMine last = reverse_digui(node.next); //递归
        node.next.next = node;
        node.next = null;
        return last;
    }

    private static LinkofMine next = null;

    private static LinkofMine reverse_digui_toN(LinkofMine node, int n) {
        if (n == 1) { //递归返回条件
            next = node.next;
            return node;
        }
        LinkofMine head = reverse_digui_toN(node.next, n - 1); //递归
        node.next.next = node;
        node.next = next;
        return head;
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

//        next = reverse_diedai(l1);

//        next = reverse_digui(l1);

        next = reverse_digui_toN(l1, 3);  //321 456

        while (next != null) {
            System.out.println(next.node);
            next = next.next;
        }
    }

}
