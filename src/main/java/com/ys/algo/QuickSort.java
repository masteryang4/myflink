package com.ys.algo;

import java.util.Arrays;

/**
 * 快速排序
 * 双指针法 + 递归
 * https://blog.csdn.net/lkp1603645756/article/details/85008715
 * 右侧先行，why？
 * - 因为从小到大排序，右侧找小的，这种情况就要右侧主导
 * - 右侧主导，右侧先行，指针相遇时也一定会停在小于index值的地方
 * 可用“6 1 2 7 9”进行模拟
 */

public class QuickSort {

    private static void quickSort(int[] a, int left, int right) {
//        if (right < left) { //【重点】
//            return;
//        }
        int temp = left;
        int l = left;
        int r = right;
        int t = 0;
        while (right > left) {
            while (right > left && a[right] > a[temp]) {
                right--;
            }
            while (right > left && a[left] < a[temp]) {
                left++;
            }
            t = a[right];
            a[right] = a[left];
            a[left] = t;
        }

        if (right == left) {
            t = a[right];
            a[right] = a[temp];
            a[temp] = t;
        } else {
            return;  //【重点】
        }

        quickSort(a, l, temp - 1);
        quickSort(a, temp + 1, r);

    }

    public static void main(String[] args) {
        int[] a = {2, 1, 5, 4, 8, 6, 7, 100, 13, 26, 15};
        System.out.println(Arrays.toString(a));

        quickSort(a, 0, a.length - 1);

        System.out.println(Arrays.toString(a));
    }

}
