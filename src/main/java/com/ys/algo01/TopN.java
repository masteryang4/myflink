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
 *
 * 参考：https://zhuanlan.zhihu.com/p/76734219
 * https://www.zhihu.com/search?type=content&q=topk
 *
 * [四种解法]https://blog.csdn.net/z50L2O08e2u4afToR9A/article/details/82837278
 * [最快解法]https://blog.csdn.net/z50L2O08e2u4afToR9A/article/details/82837280
 */
public class TopN {
    //小顶堆
    public static void make_heap(int[] arr, int parent, int length) { //参数可以再优化，length其实可以不用
        int flag = arr[parent];  //不用flag也行，直接交换也可，见：SortHeapTest

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
        //时间复杂度 O(k)
        for (int i = (k_arr.length - 1) / 2; i >= 0; i--) {
            make_heap(k_arr, i, k_arr.length);
        }

        System.out.println(Arrays.toString(k_arr));

        //数组n依次进去与一个一个堆顶进行比较
        //【注意】堆排序的交换阶段，其实就是相当于调整堆的最后一步。因为堆顶以下所有都是调整好的堆。

        //时间复杂度  O(n*logk)
        //logk其实可以看成是长度为k的堆的【高度】，也就是节点要操作的次数
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
