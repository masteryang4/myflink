package com.ys.algo;

/**
 * 堆排序
 * 1.以左节点为准，lchild为准
 * 2.有一个变量temp[始终]存储[parent]父节点的值
 */
public class HeapSort {

    public static void adjustHeap(int[] arr, int parent, int length) {
        int temp = arr[parent];
        int lchild = parent * 2 + 1;

        while (lchild < length) {
            int rchild = lchild + 1;
            if (rchild < length && arr[rchild] > arr[lchild]) {
                lchild++;
            }
            if (temp > arr[lchild]) {  //[notice]
                break;
            }
            arr[parent] = arr[lchild];
            parent = lchild;
            lchild = lchild * 2 + 1;
        }
        arr[parent] = temp;
    }

    public static void heapSort(int[] arr) {
        //构建大顶堆
        for (int i = (arr.length - 1) / 2; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);

            for (int j : arr) {
                System.out.print(j + "->");
            }
            System.out.println();
        }

        //首尾互换
        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            adjustHeap(arr, 0, i);
        }
    }

    public static void main(String[] args) {
        int[] a = {6, 1, 2, 7, 9, 8};

        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println();

        heapSort(a);

        for (int i : a) {
            System.out.print(i + " ");
        }

    }
}