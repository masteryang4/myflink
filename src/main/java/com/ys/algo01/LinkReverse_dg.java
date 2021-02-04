package com.ys.algo01;

/**
 * 反转链表-【递归】实现
 * 时间O(n)，相当于整个链表遍历
 * 空间O(n)，不断调用栈，空间复杂度高
 * <p>
 * 递归的本质：想一想这个问题在1个元素，2个元素的时候怎么操作，就是递归的本质
 */
public class LinkReverse_dg {
    private static LinkofMine reverse_dg(LinkofMine node) {
        if (node == null || node.next == null) {
            return node;
        }

        LinkofMine last = reverse_dg(node.next);
        node.next.next = node;
        node.next = null;
        return last;
    }

    public static void main(String[] args) {
        LinkofMine l5 = new LinkofMine(5, null);
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

        next = reverse_dg(l1);
        while (next != null) {
            System.out.println(next.node);
            next = next.next;
        }
    }
}
