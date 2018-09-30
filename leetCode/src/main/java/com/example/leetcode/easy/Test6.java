package com.example.leetcode.easy;

import com.example.leetcode.Base;

import java.text.DecimalFormat;

/**
 * PROJECT NAME：HexinTravel
 * AUTHOR：YETLAND
 * DATE：2018/6/15
 * DESCRIPTION：
 */
public class Test6 extends Base {

    public static void main(String... args) {
        int x = 22220;
        double x2 = x / 100.00;
        new DecimalFormat("#.00").format(x2);
        print(x2);
    }
}
