package com.ys.algo01;

import java.util.Arrays;

public class QuickSort001 {
    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9, 8};
        System.out.println(Arrays.toString(arr));
        quicksort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private static void quicksort(int[] arr, int left, int right) {
        if (left > right) { //base case
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
        quicksort(arr, left, l - 1);
        quicksort(arr, l + 1, right);
    }
}
