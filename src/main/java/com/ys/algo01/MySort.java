package com.ys.algo01;

import java.util.Arrays;

public class MySort {

    private static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

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

//        quickSort(arr, 0, arr.length - 1);
        bubbleSort(arr);  //[1, 2, 6, 7, 8, 9]

        System.out.println(Arrays.toString(arr));
    }

}
