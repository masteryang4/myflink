package com.ys.algo01;

/**
 * 常考的链表相关的题目：
 * <p>
 * 1、判断两个单链表是否相交
 * 两个单链表如果相交，一定会合并成一条链表；
 * A链表从头开始遍历，找到尾节点（如果相交，尾节点是一个节点），把这个尾节点指向B链表的头节点；
 * 如果有环的话，则A、B一定相交
 * 接下来就是判断链表是否有环：使用快慢指针方法即可。
 * <p>
 * <p>
 * 2、单链表倒数第k个节点
 * （1）反转，找第k个
 * （2）双指针，p、q之间始终差k个节点，
 * p、q同时走，当q指向尾节点的时候，p就是倒数第k个节点
 */
public class MyOtherLink {
    public static void main(String[] args) {

    }
}
