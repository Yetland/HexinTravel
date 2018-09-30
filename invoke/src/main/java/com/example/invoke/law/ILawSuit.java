package com.example.invoke.law;

/**
 * @author YETLAND
 * @date 2018/9/26 16:38
 */
public interface ILawSuit {
    /**
     * 提起诉讼
     *
     * @param proof name
     */
    void submit(String proof);

    /**
     * 法庭辩护
     */
    void defend();
}
