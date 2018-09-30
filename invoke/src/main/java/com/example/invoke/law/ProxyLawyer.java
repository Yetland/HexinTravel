package com.example.invoke.law;

/**
 * @author YETLAND
 * @date 2018/9/26 16:39
 */
public class ProxyLawyer implements ILawSuit {

    //持有要代理的那个对象
    private ILawSuit plaintiff;

    public ProxyLawyer(ILawSuit plaintiff) {
        this.plaintiff = plaintiff;
    }

    @Override
    public void submit(String proof) {
        plaintiff.submit(proof);
    }

    @Override
    public void defend() {
        plaintiff.defend();
    }
}