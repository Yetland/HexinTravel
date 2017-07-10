package com.yetland.crazy.core.entity

/**
 * @Name:           Entity
 * @Author:         yeliang
 * @Date:           2017/7/5
 */

open class ImModel(name: String){
    constructor(name:String , age:Int):this(name){

    }
}

class HelloEntity: ImModel {
    constructor(name: String):super(name)
}