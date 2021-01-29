package com.ys.algo_Tencent_Nowcoder;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 6
 * 4 3 1 6 2 5
 * <p>
 * 2
 */
public class Q11 {
    private static int arrMax(int[] arr) {
        int arrmax = Integer.MIN_VALUE;
        for (int i : arr) {
            if (i > arrmax) {
                arrmax = i;
            }
        }
        return arrmax;
    }

    private static int arrMin(int[] arr) {
        int arrmin = Integer.MAX_VALUE;
        for (int i : arr) {
            if (i < arrmin) {
                arrmin = i;
            }
        }
        return arrmin;
    }

    public static int nsort_num(int[] arr, int size) {
        int left = 0;
        int right = 0;



        return left > right ? right : left;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();

        int[] arr = new int[size];//【注意】
        for (int i = 0; i < size; i++) {
            arr[i] = sc.nextInt();
        }

        System.out.println(size);
        System.out.println(Arrays.toString(arr));

        System.out.println(nsort_num(arr, size));
    }
}
