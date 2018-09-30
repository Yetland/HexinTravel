package com.example.invoke;

/**
 * @author YETLAND
 * @date 2018/9/26 16:17
 */
public class ForeignProduct implements Product {
    @Override
    public String buy(double money) {
        return "cost: " +
                money +
                " buy";
    }

    @Override
    public String name(String name) {
        return "My name is " + name;
    }

    public static ForeignProduct newInstance() {
        return new ForeignProduct();
    }
}