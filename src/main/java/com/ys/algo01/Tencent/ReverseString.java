package com.ys.algo01.Tencent;

import java.util.HashSet;
import java.util.Scanner;

/**
 * 题目：https://mp.weixin.qq.com/s/Cq--AMMDrkV89QXihJWu9w
 * <p>
 * 给一个日期：20200202
 * 观察这个日期，同时满足两个特征：
 * 1.左右对称
 * 2.除数字0外，只有一个非零数字
 * <p>
 * 编码实现以下两项：
 * 1.输入任意日期，判断是否满足以上条件，测试用例：20200202，21211212
 * 2.输出自19700101至今所有符合条件的日期
 * 参考思路：
 * //还需要对输入进行校验，是否是正确的日期
 * <p>
 * 【参考】
 * https://blog.csdn.net/plokmju88/article/details/104151665
 * labuladong 反转链表 也有涉及
 * 回文字符串（1.判断：双指针往中间移动。2.寻找：从中间往两边移动），
 * 回文数字，回文链表
 * 其实都是LeetCode题目
 */
public class ReverseString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); //20200202 21211212 21211234
        int i = sc.nextInt();
        System.out.println(i);

//        boolean ishw = ishuiwen(i); //判断是否是回文日期（数字）
//        System.out.println(ishw);

        boolean isnum = isTiaojian(i); //判断是否满足两个条件
        System.out.println(isnum);
    }

    /**
     * 1.左右对称
     * 2.除数字0外，只有一个非零数字
     *
     * @param i
     * @return
     */
    private static boolean isTiaojian(int i) {
        if (i % 10 == 0) { //末尾不能是0
            return false;
        }
        int reverseI = 0;
        HashSet<Integer> set = new HashSet<>(); //集合用来放除0以外的数字

        while (i > reverseI) {
            reverseI = reverseI * 10 + i % 10;
            if (i % 10 != 0) { //把i（其实只是i的后一半）的最后一位放到set里面
                set.add(i % 10);
            }

            i = i / 10;
        }
        System.out.println(i + " " + reverseI);
        System.out.println(set);

        if (i == reverseI && set.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 既然是数字，我们可以通过除法 / 和取余 % 的方式，将这个数字取出【后半段进行翻转】，然后比对两个数字的是否相等。
     *
     * @param i
     * @return
     */
    private static boolean ishuiwen(int i) {
        if (i % 10 == 0) { //末尾不能是0
            return false;
        }
        int reverseI = 0;
        while (i > reverseI) {
            reverseI = reverseI * 10 + i % 10;
            i = i / 10;
        }
        System.out.println(i + " " + reverseI);
        return i == reverseI;
    }
}
