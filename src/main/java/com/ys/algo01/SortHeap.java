package com.ys.algo01;

import java.util.Arrays;

/**
 * 没有捷径，就是实例一步一步往里带入
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(1)
 */
public class SortHeap {
    //调整大顶堆
    public static void makeHeap(int[] arr, int parent, int length) {
        int flag = arr[parent];
        int left = 2 * parent + 1;

        while (left < length) { //先判断left有没有，有left才会有right
            int right = left + 1;

            if (right < length && arr[left] < arr[right]) { //确保right存在的情况
                left = right;
            }

            if (arr[left] > flag) { //核心【注意】要用flag，而不是arr[parent]，因为接下来的循环要用！
                //交换之后可能会出现再次交换的情况，所以，要先用flag存上，并每次和flag比较，
                //因为flag才是真正的当前的parrent节点的值。（当前的子树来看，谁大谁是parent）
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
//            System.out.println("---->>test");
//            System.out.println(Arrays.toString(arr));

            makeHeap(arr, 0, arr.length - i - 1);
        }
    }

    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9, 8};
        System.out.println(Arrays.toString(arr));

        heap_sort(arr);

        System.out.println(Arrays.toString(arr));
    }
}
