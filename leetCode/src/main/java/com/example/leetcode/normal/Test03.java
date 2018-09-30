package com.example.leetcode.normal;

import com.example.leetcode.Base;

/**
 * author: YETLAND
 * created on: 2018/4/19 13:58
 * description:
 */
public class Test03 extends Base {

    public static void main(String... args) {
        int size = lengthOfLongestSubstring("fffabfffabcdefg");
        print(size);
    }

    /**
     * 最大连续字串
     *
     * @param s 输入
     * @return 结果
     */
    public static int lengthOfLongestSubstring(String s) {
        StringBuilder result = new StringBuilder();
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            String s1 = String.valueOf(s.charAt(i));
            if (i == 0) {
                result.append(s1);
            } else if (!result.toString().contains(s1)) {
                result.append(s1);
            } else {
                String ss = result.toString();
                for (int j = 0; j < ss.length(); j++) {
                    if (ss.charAt(j) == s.charAt(i)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int k = j + 1; k < ss.length(); k++) {
                            stringBuilder.append(ss.charAt(k));
                        }
                        ss = stringBuilder.toString();
                        max = max > result.length() ? max : result.length();
                        print("result = " + result.toString());
                        result = new StringBuilder();
                        result.append(ss);
                        result.append(s1);
                        break;
                    }
                }
            }
        }
        print("result = " + result.toString());
        max = max > result.toString().length() ? max : result.toString().length();
        return max;
    }
}
