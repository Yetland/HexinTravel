package com.example.leetcode.easy;

import com.example.leetcode.Base;

/**
 * author: YETLAND
 * created on: 2018/4/19 10:35
 * description:
 */
public class Test07 extends Base{

    public static void main(String... args) {
        int result = reverse(-123);
        print("result = " + result);
//        System.out.print("result = " + result);
    }

    /**
     *
     * @param x 输入值。如果是负数的话，需要先反转，但是考虑到integer的范围，需要判断当输入值是最小值时，
     *          反转会越界，所以直接返回。
     * @return 翻转结果
     */
    public static int reverse(int x) {
        int tag = 1;
        if (x < 0) {
            if (x == Integer.MIN_VALUE){
                return 0;
            }
            tag = -1;
            x = x * tag;
        }
        double f;
        StringBuilder s = new StringBuilder(String.valueOf(x));
        char[] c = s.toString().toCharArray();

        for (int i = 0; i < c.length / 2; i++) {
            char c1 = c[i];
            c[i] = c[c.length - i - 1];
            c[c.length - 1 - i] = c1;
        }
        s = new StringBuilder();

        for (char c1 : c) {
            s.append(c1);
        }
        f = Double.valueOf(s.toString());
        f = f * tag;
        if (f > Integer.MAX_VALUE || f < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) f;
    }
}
