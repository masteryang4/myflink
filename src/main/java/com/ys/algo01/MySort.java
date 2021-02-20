package com.ys.algo01;

import java.util.Arrays;

public class MySort {

    private static void quickSort(int[] arr, int left, int right) {
        if (left > right) {
            return;
        }
        int flag = arr[left];
        int l = left;
        int r = right;
        while (l < r) {
            while (arr[r] >= flag && l < r) {
                r--;
            }
            while (arr[l] <= flag && l < r) {
                l++;
            }
            int tmp = arr[r];
            arr[r] = arr[l];
            arr[l] = tmp;
        }
        if (l == r) {
            arr[left] = arr[l];
            arr[l] = flag;
        }

        quickSort(arr, left, l - 1);
        quickSort(arr, l + 1, right);
    }

    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9, 8};
        System.out.println(Arrays.toString(arr));


        quickSort(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));
    }
}
