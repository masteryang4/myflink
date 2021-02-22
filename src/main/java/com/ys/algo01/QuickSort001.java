package com.ys.algo01;

import java.util.Arrays;

public class QuickSort001 {
    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9, 8};
        System.out.println(Arrays.toString(arr));
//        quicksort(arr, 0, arr.length - 1);
//        bubblesort(arr);
//        heapsort(arr);
        mergesort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    /**
     *
     * @param arr
     * @param left
     * @param right
     */
    private static void mergesort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergesort(arr, left, mid);
        mergesort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] resultarr = new int[right - left + 1];
        int l = left;
        int m = mid + 1;
        int k = 0;
        while (l <= mid && m <= right) {
            if (arr[l] > arr[m]) {
                resultarr[k++] = arr[m++];
            } else {
                resultarr[k++] = arr[l++];
            }
        }
        while (l <= mid) {
            resultarr[k++] = arr[l++];
        }
        while (m <= right) {
            resultarr[k++] = arr[m++];
        }
        for (int i = 0; i < resultarr.length; i++) {
            arr[i + left] = resultarr[i];
        }
    }

    /**
     *
     * @param arr
     */
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

    /**
     *
     * @param arr
     */
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

    /**
     *
     * @param arr
     * @param left
     * @param right
     */
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
