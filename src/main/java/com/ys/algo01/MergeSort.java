package com.ys.algo01;

import java.util.Arrays;

/**
 * 归并排序
 * 本质上是后序遍历二叉树（递归角度）
 */
public class MergeSort {

    private static void merge(int[] arr, int left, int mid, int right) {//arr,0,2,4
        int[] tmp = new int[right - left + 1]; //【注意】
        int l = left;
        int m = mid + 1;
        int k = 0;
        while (l <= mid && m <= right) { //先把小的放tmp里面
            if (arr[l] < arr[m]) {
                tmp[k++] = arr[l++];
            } else {
                tmp[k++] = arr[m++];
            }
        }
        while (l <= mid) { //剩余的拼接到tmp中
            tmp[k++] = arr[l++];
        }
        while (m <= right) {
            tmp[k++] = arr[m++];
        }
        //tmp赋值回arr
        for (int i = 0; i < tmp.length; i++) {  //【注意】tmp的长度不一定等于arr，所以arr[i + left]
            arr[i + left] = tmp[i];
        }
    }

    private static void merge_sort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        merge_sort(arr, left, mid);
        merge_sort(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }

    public static void main(String[] args) {
        int[] arr = {8, 1, 2, 7, 6, 3};
        merge_sort(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));  //[1, 2, 3, 6, 7, 8]
    }
}
