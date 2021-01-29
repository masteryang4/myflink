package com.ys.algo01;

import java.util.Arrays;

/**
 * 没有捷径，就是实例一步一步往里带入
 */
public class SortHeap {
    //调整大顶堆
    public static void makeHeap(int[] arr, int parent, int length) {
        int flag = arr[parent];
        int left = 2 * flag + 1;
        int right = left++;

        while (right <= length) {
            if (arr[left] < arr[right]) {
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

    public static void heap_sort(int[] arr) {
        System.out.println(Arrays.toString(arr));

        //构建大顶堆
        for (int i = ((arr.length - 1) / 2); i >= 0; i--) {
            makeHeap(arr, i, arr.length);
        }

        System.out.println(Arrays.toString(arr));

        //交换首尾，重更新调整
    }

    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9, 8};
        System.out.println(Arrays.toString(arr));

        heap_sort(arr);

        System.out.println(Arrays.toString(arr));
    }
}
