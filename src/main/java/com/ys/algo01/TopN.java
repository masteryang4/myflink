package com.ys.algo01;

import java.util.Arrays;

/**
 * topn，使用堆，要先构建堆
 * <p>
 * 长度为n的数组，取前k个最大的数
 * 要先构建长度为k的【小顶堆】，其他的数字依次和堆顶进行比较，比堆顶【大】的取代堆顶，重新调整堆
 * 这么做的目的就是：每次都会把最小的数换掉，因为是小顶堆，最后留下的一定是最大的k个数字
 * <p>
 * 空间复杂度：O(k)
 * 时间复杂度：O(n * logk)
 */
public class TopN {
    //小顶堆
    public static void make_heap(int[] arr, int parent, int length) {
        int flag = arr[parent];
        int left = 2 * parent + 1;
        while (left < length) {
            int right = left + 1;
            if (right < length && arr[right] < arr[left]) {
                left = right;
            }
            if (arr[left] < flag) {
                arr[parent] = arr[left];
                parent = left;
                left = 2 * left + 1;
            } else {
                break;
            }
        }
        arr[parent] = flag;
    }

    public static int[] top_n(int[] arr, int k) {
        int[] k_arr = Arrays.copyOfRange(arr, 0, k);
        System.out.println(Arrays.toString(k_arr));

        //构建小顶堆
        for (int i = (k_arr.length - 1) / 2; i >= 0; i--) {
            make_heap(k_arr, i, k_arr.length);
        }

        System.out.println(Arrays.toString(k_arr));

        for (int j = k; j < arr.length - 1; j++) {
            if (arr[j] > k_arr[0]) {
                k_arr[0] = arr[j];
                make_heap(k_arr, 0, k_arr.length);
            }
        }

        return k_arr;
    }

    public static void main(String[] args) {
        int[] n_arr = {6, 1, 2, 7, 9, 8, 5, 3, 0, 4};
        int k = 4;

        int[] r_arr = top_n(n_arr, k);

        System.out.println(Arrays.toString(r_arr));
    }
}
