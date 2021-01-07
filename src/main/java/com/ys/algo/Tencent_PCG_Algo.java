package com.ys.algo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 小Q在进行射击气球的游戏，如果小Q在连续T枪中打爆了所有颜色的气球，将得到一只QQ公仔作为奖励。（每种颜色的球至少被打爆一只）。
 * 这个游戏中有m种不同颜色的气球，编号1到m。
 * 小Q一共有n发子弹，然后连续开了n枪。
 * 小Q想知道在这n枪中，打爆所有颜色的气球最少用了连续几枪？
 * <p>
 * 输入格式
 * 第一行包含两个整数n和m。
 * 第二行包含n个整数，分别表示每一枪打中的气球的颜色，0表示没打中任何颜色的气球。
 * <p>
 * 输出格式
 * 一个整数表示小Q打爆所有颜色气球用的最少枪数。
 * 如果小Q无法在这n枪打爆所有颜色的气球，则输出-1。
 * <p>
 * 数据范围
 * 1≤n≤1061≤n≤106,
 * 1≤m≤20001≤m≤2000
 * <p>
 * 输入样例：
 * 12 5
 * 2 5 3 1 3 2 4 1 0 5 4 3
 * <p>
 * 输出样例：
 * 6
 * <p>
 * 样例解释
 * 有五种颜色的气球，编号1到5。
 * 游客从第二枪开始直到第七枪，这连续六枪打爆了5 3 1 3 2 4这几种颜色的气球，包含了从1到5的所有颜色，所以最少枪数为6。
 * <p>
 * 【思路】用两个指针，每次后指针往后移，移到符合颜色数时，前指针看看能不能在符合颜色数的前提下往后移一点。最后每次比较一下长度即可。
 * <p>
 * 【 滑动窗口问题 】
 */
public class Tencent_PCG_Algo {

    public static int mySearch(int[] a, int n, int m) {
        int[] cnt = new int[m + 1];
        Set<Integer> set = new HashSet<>();
        int res = -1;
        int i = 0, j = 0;
        while (i <= j && j < n) {
            cnt[a[j]]++;
            if (a[j] != 0) {
                set.add(a[j]);
            }
            if (set.size() >= m) {
                while (i < j) {
                    if (a[i] != 0 && cnt[a[i]] == 1)
                        break;
                    cnt[a[i]]--;
                    i++;
                }
                if (res == -1 || j - i + 1 < res) res = j - i + 1;
            }

            j++;
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = sc.nextInt();

        System.out.println(m + "," + n + "," + Arrays.toString(a));

        //method
        System.out.println(mySearch(a, n, m));
    }
}
