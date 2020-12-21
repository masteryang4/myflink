package com.ys.algo;

import java.util.Arrays;

/**
 * 冒泡排序
 * 双层for循环
 */
public class BubbleSort {

    public static void bubbleSort(int[] a) {
        int temp = 0;
        for (int i = 1; i < a.length; i++) {
            for (int j = 0; j < a.length - i; j++) {
                if (a[j] > a[j + 1]) {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {2, 1, 5, 4, 8, 6, 7, 100, 13, 26, 15};
        System.out.println(Arrays.toString(a));

        bubbleSort(a);

        System.out.println(Arrays.toString(a));
    }
}
