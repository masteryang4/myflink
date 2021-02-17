package com.ys.algo01.mytree;

import java.util.Arrays;

/**
 * 构建（大顶）堆。面试重点。
 */
public class MakeHeapTest {

    private static void makemaxheap(int[] arr, int parrent) {//如果是堆排序 通常三个参数：(arr,parent,length).
        int flag = arr[parrent];
        int length = arr.length;
        int left = 2 * parrent + 1;

        while (left < length) {
            int right = left + 1;
            if (right < length && arr[right] > arr[left]) {
                left = right;
            }
            if (arr[left] > flag) {
                arr[parrent] = arr[left];
                parrent = left;
                left = 2 * left + 1;
            } else {
                break;
            }
        }
        arr[parrent] = flag;
    }

    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9, 8};

        System.out.println(Arrays.toString(arr)); //【注意】Arrays.toString(arr));

        for (int i = (arr.length - 1) / 2; i >= 0; i--) {
            makemaxheap(arr, i);
        }

        System.out.println(Arrays.toString(arr)); //[9, 7, 8, 6, 1, 2]
    }
}
