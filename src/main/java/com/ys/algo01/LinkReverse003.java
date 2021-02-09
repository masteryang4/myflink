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

    private static LinkofMine reverse_digui_mn(LinkofMine node, int m, int n) {
        if (m == 1) {
            return reverse_digui_toN(node, n);
        }
        LinkofMine head = reverse_digui_mn(node.next, m - 1, n - 1);
        node.next = head;
        return node;
    }

    /**
     * 【迭代】反转区间 [a, b) 的元素，注意是左闭右开
     * 【注】reverse_diedai 其实就是反转 a到null 的元素。
     */
    private static LinkofMine reverse_diedai_ab(LinkofMine a, LinkofMine b) {
        LinkofMine pre = null;
        LinkofMine cur = a;
        LinkofMine nxt = a;
        while (cur != b) {
            nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        a.next = b; //反转后的链表和原链表（从b开始的部分）拼接。
        //a之前的部分可以通过参数传进来
        return pre;
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

//        next = reverse_digui_toN(l1, 3);  //321 456

//        next = reverse_digui_mn(l1, 2, 4);   //1 432 56

        next = reverse_diedai_ab(l1, l5);  //4321 56

        while (next != null) {
            System.out.println(next.node);
            next = next.next;
        }
    }

}
