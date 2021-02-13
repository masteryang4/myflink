package com.ys.algo01;

/**
 * 判断是否是回文链表
 * 时间复杂度：O（n）
 * 空间复杂度：O（1）
 */
public class PalindromeLink {

    private static LinkofMine reverseLink(LinkofMine head) {
        LinkofMine pre = null;
        LinkofMine curr = head;
        LinkofMine nxt = null;
        while (curr != null) {
            nxt = curr.next;
            curr.next = pre;
            pre = curr;
            curr = nxt;
        }
        return pre;
    }

    private static boolean isPalindrome(LinkofMine head) {
        LinkofMine left = head;
        LinkofMine fast = head;
        LinkofMine slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        //【注意】此时slow就是中间点
        if (fast != null) {  //【注意】如果链表个数为奇数，则slow会停在正中间，需要向后走一步，从slow.next开始反转
            slow = slow.next;
        }
//        System.out.println(slow.node);

        LinkofMine right = reverseLink(slow);  //反转

        while (right != null) { //从头和尾分别遍历
            if (left.node != right.node) {
                return false;
            }
            right = right.next;
            left = left.next;
        }
        return true;
    }

    public static void main(String[] args) {
        LinkofMine l6 = new LinkofMine(1, null);
        LinkofMine l5 = new LinkofMine(2, l6);
//        LinkofMine l5 = new LinkofMine(2, null);
        LinkofMine l4 = new LinkofMine(3, l5);
//        LinkofMine l5 = new LinkofMine(1, null);
//        LinkofMine l4 = new LinkofMine(2, l5);
        LinkofMine l3 = new LinkofMine(3, l4);
        LinkofMine l2 = new LinkofMine(2, l3);
        LinkofMine l1 = new LinkofMine(1, l2);

        System.out.println(isPalindrome(l1));
    }
}
