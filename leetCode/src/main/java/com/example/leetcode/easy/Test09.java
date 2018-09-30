package com.example.leetcode.easy;

/**
 * author: YETLAND
 * created on: 2018/4/19 11:14
 * description:
 */
public class Test09 {

    public static void main(String... args) {
        boolean result = isPalindrome(10101);
        System.out.print("result = " + result + "\n");
    }

    /**
     * 是否回文数问题
     *
     * @param x 输入
     * @return 结果
     */
    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        String before, after;
        StringBuilder s = new StringBuilder(String.valueOf(x));
        before = s.toString();
        char[] c = s.toString().toCharArray();
        for (int i = 0; i < c.length / 2; i++) {
            char c1 = c[i];
            char c2 = c[c.length - i - 1];
            char c3 = c1;
            c1 = c2;
            c2 = c3;
            c[i] = c1;
            c[c.length - 1 - i] = c2;
        }
        s = new StringBuilder();

        for (char c1 : c) {
            s.append(c1);
        }
        after = s.toString();
        return before.equals(after);
    }
}
