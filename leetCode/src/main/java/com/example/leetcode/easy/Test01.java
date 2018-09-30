package com.example.leetcode.easy;

import com.example.leetcode.Base;

public class Test01 extends Base {

    public static void main(String... args) {
        int[] nums = new int[]{1, 3, 12, 7};
        int target = 15;
        twoSum(nums, target);
    }

    /**
     * 两数相加
     * https://leetcode-cn.com/problems/two-sum/description/
     *
     * @param nums   输入
     * @param target 目标值
     * @return 结果
     */
    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for (int i1 = 0; i1 < nums.length; i1++) {
            for (int i2 = i1 + 1; i2 < nums.length; i2++) {
                if (nums[i1] + nums[i2] == target) {
                    result[0] = i1;
                    result[1] = i2;
                    System.out.print("i1 = " + i1 + " , i2 = " + i2);
                    return result;
                }
            }
        }
        return result;
    }
}
