package com.ys.algo;

/**
 * 反转链表
 * <p>
 * 【双指针法】
 */

class MyLink {
    int num;
    MyLink next;

    public MyLink(int num, MyLink next) {
        this.num = num;
        this.next = next;
    }
}

public class ReverseLink {

    public static MyLink reverse(MyLink link) {
        MyLink pre = null;
        MyLink curr = link;
        MyLink temp = null;
        while (curr != null) {
            temp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = temp;
        }
        return pre;
    }

    public static void main(String[] args) {
        MyLink link4 = new MyLink(4, null);
        MyLink link3 = new MyLink(3, link4);
        MyLink link2 = new MyLink(2, link3);
        MyLink link1 = new MyLink(1, link2);

        MyLink link = link1;
        while (link != null) {
            System.out.print(link.num + "->");
            link = link.next;
        }

        System.out.println();

        MyLink newlink = ReverseLink.reverse(link1); //反转链表

        while (newlink != null) {
            System.out.print(newlink.num + "->");
            newlink = newlink.next;
        }

        System.out.println();
    }
}
