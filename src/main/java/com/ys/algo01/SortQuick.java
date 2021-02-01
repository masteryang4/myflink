package com.ys.algo01;

import java.util.Arrays;

/**
 * {6, 1, 2, 7, 9}
 */
public class SortQuick {
    public static void quick_sort(int[] arr, int left, int right) {
        if (left > right) {
            return;
        }

        int flag = arr[left];
        int i = left;
        int j = right;
        int tmp = 0;
        while (i < j) {
            while (arr[j] >= flag && i < j) { //【注意】条件二也很重要，缺一不可，目的是要拦着i
                j--;
            }
            while (arr[i] <= flag && i < j) {
                i++;
            }
            tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        if (i == j) {
            arr[left] = arr[i];
            arr[i] = flag;
        }

        quick_sort(arr, left, i - 1);
        quick_sort(arr, i + 1, right);
    }

    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9};
        System.out.println(Arrays.toString(arr));

        quick_sort(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));
    }
}
