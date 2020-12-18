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

    public static void reverse(MyLink link) {
        MyLink pre = null;
        MyLink curr = link;
        while (curr != null) {

            curr.next = pre;
            pre = curr;

        }
    }

    public static void main(String[] args) {
        MyLink link4 = new MyLink(4, null);
        MyLink link3 = new MyLink(3, link4);
        MyLink link2 = new MyLink(2, link3);
        MyLink link1 = new MyLink(1, link2);

        MyLink link = link1;
        while (link.next != null) {
            System.out.print(link.num + "->");
            link = link.next;
        }

        System.out.println(link.num);

        ReverseLink.reverse(link1); //反转链表

        link = link1;
        while (link.next != null) {
            System.out.print(link.num + "->");
            link = link.next;
        }

        System.out.println(link.num);

    }
}
