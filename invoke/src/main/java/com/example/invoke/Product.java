package com.example.invoke;

/**
 * @author YETLAND
 */
public interface Product {
    /**
     * 花钱购买商品
     *
     * @param money
     * @return
     */
    String buy(double money);

    String name(String name);
}