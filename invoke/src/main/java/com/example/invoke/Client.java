package com.example.invoke;

/**
 * @author YETLAND
 */
public class Client {
    public static void main(String[] args) {
        useProxy();
    }

    /**
     * 反射动态代理
     */
    private static void useProxy() {
        ProductHandler handler = ProductHandler.newInstance();
        //创建代理者，类似海外代购员
        Product product = (Product) handler.bind(ForeignProduct.newInstance());
        //进行代购操作，返回需要的商品结果
        String result = product.buy(2.5);
        String name = product.name("apple");
        System.out.println("result: " + result + "  , name = " + name);
    }
}
