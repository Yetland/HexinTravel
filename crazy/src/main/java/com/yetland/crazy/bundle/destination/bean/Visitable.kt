package com.yetland.crazy.bundle.destination.bean

/**
 * @Name:           Animal
 * @Author:         yeliang
 * @Date:           2017/7/7
 */

abstract class Visitable{
    var objectId: String? = null
    var updatedAt: String? = null
    var createdAt: String? = null
    var url: String? = null
    abstract fun type(typeFactory: TypeFactory): Int
}

abstract class Animal : Visitable()

class Mouse(val name: String = "Mouse") : Animal() {
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }
}

class Dog(val name: String = "Dog") : Animal(){
    var age: Int = 1

    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }

}

class Duck(val name: String = "Duck") : Animal() {
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }

}

class Car(val brand: String = "BENZ") : Visitable() {
    var price = 1
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }
}

class Footer : Visitable() {
    override fun type(typeFactory: TypeFactory): Int {
        return typeFactory.type(this)
    }
}
