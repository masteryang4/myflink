package com.ys.algo01;

/**
 * k个一组反转链表
 */
public class LinkReverse_Kgroup {

    //反转[a,b)
    private static LinkofMine reverse_ab(LinkofMine a, LinkofMine b) {
        LinkofMine pre = null;
        LinkofMine curr = a;
        LinkofMine nxt = null;
        while (curr != b) {
            nxt = curr.next;
            curr.next = pre;
            pre = curr;
            curr = nxt;
        }
        return pre;
    }

    private static LinkofMine reverse_kgroup(LinkofMine l1, int k) {
        if (l1 == null) {
            return null;
        }
        LinkofMine b = l1;
        int i = k;
        while (i > 0) {
            if (b == null) {
                return l1;
            }
            b = b.next;
            i--;
        }
        LinkofMine head = reverse_ab(l1, b);
        l1.next = reverse_kgroup(b, k);
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

//        next = reverse_ab(l2, l5); //test  432

        next = reverse_kgroup(l1, 2);  // 21 43 65

//        next = reverse_kgroup(l1, 3);  // 321 654

//        next = reverse_kgroup(l1, 4);  // 4321 56

        while (next != null) {
            System.out.println(next.node);
            next = next.next;
        }
    }
}
