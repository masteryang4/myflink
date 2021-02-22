package com.ys.algo01;

import java.util.Arrays;

public class QuickSort001 {
    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9, 8};
        System.out.println(Arrays.toString(arr));
//        quicksort(arr, 0, arr.length - 1);
//        bubblesort(arr);
        heapsort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void heapsort(int[] arr) {
        for (int i = (arr.length - 1) / 2; i >= 0; i--) {
            makeheap(arr, i, arr.length);
        }
//        System.out.println("heap= " + Arrays.toString(arr)); //[9, 7, 8, 6, 1, 2]
        for (int i = arr.length - 1; i > 0; i--) {
            int tmp = arr[i];
            arr[i] = arr[0];
            arr[0] = tmp;

            makeheap(arr, 0, i);
        }
    }

    private static void makeheap(int[] arr, int parent, int length) {
        int flag = arr[parent];
        int left = 2 * parent + 1;
        while (left < length) {
            int right = left + 1;
            if (right < length && arr[right] > arr[left]) { //【重点】
                left = right;
            }
            if (arr[left] > flag) {
                arr[parent] = arr[left];
                parent = left;
                left = 2 * left + 1;
            } else {
                break;
            }
        }
        arr[parent] = flag;
    }

    private static void bubblesort(int[] arr) {
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
