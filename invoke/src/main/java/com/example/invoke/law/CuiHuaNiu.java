package com.example.invoke.law;

/**
 * @author YETLAND
 * @date 2018/9/26 16:41
 */
public class CuiHuaNiu implements ILawSuit {
    @Override
    public void submit(String proof) {
        System.out.println("submit name : " + proof);
    }

    @Override
    public void defend() {
        System.out.println("CuiHuaNiu defend");
    }
}
