package com.ys.algo01;

import java.util.Arrays;

public class MakeHeap {
    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9};

        for (int i = (arr.length - 1) / 2; i >= 0; i--) {
            makeheap(arr, i);
        }

        System.out.println(Arrays.toString(arr)); //[9, 7, 2, 6, 1]
    }

    private static void makeheap(int[] arr, int parent) {
        int flag = arr[parent];
        int length = arr.length;
        int left = 2 * parent + 1;

        while (left < length) {
            int right = left + 1;
            if (right < length && arr[right] > arr[left]) {
                left = right;
            }
            if (arr[left] > flag) {
                arr[parent] = arr[left];
                parent = left;
                left = 2 * left + 1;
            }
        }
        arr[parent] = flag;
    }
}
