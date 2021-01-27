package com.ys.algo01;

/**
 * 悟：通过【具体】例子来写【抽象】代码，方便写代码
 */
public class SortBubble {
    public static void bubblesort(int[] arr) {
        int tmp = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9, 8, 4, 3, 5, 0};

        for (int i : arr) {
            System.out.print(i + ",");
        }
        System.out.println();

        bubblesort(arr);

        for (int i : arr) {
            System.out.print(i + ",");
        }
        System.out.println();
    }
}
