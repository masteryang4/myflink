package com.ys.algo;

import java.util.HashMap;
import java.util.Map;

/**
 * s: "ADOBECODEBANC",
 * t: "ABC"
 */

/**
 * 【注意】方法：有实例带入是最好的
 */

public class HDCKLeetCode76 {

    public static void minsub(String s, String t) {
        HashMap<Character, Integer> window = new HashMap<>();
        HashMap<Character, Integer> need = new HashMap<>();

        char[] t_chars = t.toCharArray();
        char[] s_chars = s.toCharArray();

        for (char t_char : t_chars) {
            need.put(t_char, need.getOrDefault(t_char, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : need.entrySet()) {
            System.out.print(entry.getKey() + "," + entry.getValue() + " ; ");
        }
        System.out.println();

        //window
        int left = 0;
        int right = 0;
        int valid = 0;
        int start = 0;
        int len = 0;
        while (right < s.length()) {
            char ch = s_chars[right];
            right++;
            if (need.containsKey(ch)) {
                window.put(ch, window.getOrDefault(ch, 0) + 1);
                if (window.get(ch) == need.get(ch)) {
                    valid++;
                }
            }

            System.out.println("window---------->>>");
            for (Map.Entry<Character, Integer> characterIntegerEntry : window.entrySet()) {
                System.out.println(characterIntegerEntry.getKey() + "," + characterIntegerEntry.getValue());
            }
            System.out.println("need---------->>>");
            for (Map.Entry<Character, Integer> entry : need.entrySet()) {
                System.out.println(entry.getKey() + "," + entry.getValue());
            }

            while (valid == need.size()) {
                len = valid;
                char l_ch = s_chars[left];
                left++;
                if (need.containsKey(l_ch)) {
                    window.put(l_ch, window.get(l_ch) - 1);
                    if (window.get(l_ch) < need.get(l_ch)) {
                        valid--;
                        if (window.get(l_ch) == 0) {
                            window.remove(l_ch);
                        }
                    }
                }
            }

        }

        start = left - 1;

        System.out.println("=======>>>");
//        String s1 = new String();
        System.out.println(start + "," + len);
        for (int i = start; i <= start + len; i++) {
            System.out.print(s_chars[i]);
        }

    }

    public static void main(String[] args) {
        minsub("ADOBECODEBANC666", "ABC");
    }

}
