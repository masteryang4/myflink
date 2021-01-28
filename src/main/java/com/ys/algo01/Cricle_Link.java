package com.ys.algo01;

/**
 * 判断一个链表是否有环
 * <p>
 * 附加：如何计算环的大小？
 * 【答】两人相遇说明有圈且“已经进入圈里面了”；
 * 所以，相遇的位置在跑还会相同位置相遇，
 * 且p2 会超过p1 整整一圈（扣圈）；路程 （一圈几个节点）= 【速度（p2几个节点/步） * 步数（p2跑了几次）】-【速度（p1几个节点/步） * 步数（p1跑了几次）】
 * 因为p1，p2步树相同，所以，
 * 等于：路程 （一圈几个节点）= 速度（p2-p1 几个节点/步） * 步数（p1,p2跑了几次）= 1 * 步数
 * 所以，最终结果就是：环的大小，就是指针跑了几次
 */
public class Cricle_Link {
    public static boolean isCricle(LinkofMine node) {
        LinkofMine p1 = node; //p1走一步
        LinkofMine p2 = node; //p2走两步

        while (p2 != null && p2.next != null) {//【重点】p2走的快，先判断p2，因为很可能没有环。且p2 = p2.next.next;避免空指针
            p1 = p1.next;
            p2 = p2.next.next;
            if (p1 == p2) {
                System.out.println(p1.node);//打印出二者相遇的地方
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        LinkofMine l5 = new LinkofMine(5, null);
        LinkofMine l4 = new LinkofMine(4, l5);
        LinkofMine l3 = new LinkofMine(3, l4);
        LinkofMine l2 = new LinkofMine(2, l3);
        LinkofMine l1 = new LinkofMine(1, l2);

        l5.next = l2;//控制链表是否有环

        System.out.println(isCricle(l1));
    }
}
