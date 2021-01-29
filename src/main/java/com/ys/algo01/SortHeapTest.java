package com.ys.algo01;

import java.util.Arrays;

/**
 * 没有捷径，就是实例一步一步往里带入
 * <p>
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(1)
 */
public class SortHeapTest {
    //调整大顶堆
    public static void makeHeap(int[] arr, int parent, int length) {
//        int flag = arr[parent];
        int tmp = 0;
        int left = 2 * parent + 1;

        while (left < length) {
            int right = left + 1;

            if (right < length && arr[left] < arr[right]) {
                left = right;
            }

            if (arr[left] > arr[parent]) {
                tmp = arr[left];
                arr[left] = arr[parent];
                arr[parent] = tmp;

                parent = left;
                left = 2 * left + 1;
            } else {
                break;
            }
        }
//        arr[parent] = flag;
    }

    public static void heap_sort(int[] arr) {
        //构建大顶堆
        for (int i = ((arr.length - 1) / 2); i >= 0; i--) {
            makeHeap(arr, i, arr.length);
        }

        int temp = 0;
        //交换首尾，重更新调整
        for (int i = 0; i < arr.length - 1; i++) {
            temp = arr[0];
            arr[0] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;

            makeHeap(arr, 0, arr.length - i - 1);
        }
    }

    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9, 8, 5, 3, 4, 0};
        System.out.println(Arrays.toString(arr));

        heap_sort(arr);

        System.out.println(Arrays.toString(arr));
    }
}
