package com.example.leetcode.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class UnionTest {

    public static void main(String[] args) {

        // 初始化一个学生集合
        List<Student> students = Arrays.asList(new Student(1,"james"),new Student(2,"love"));
//        students.stream().filter()

//        List<Student>  students = new ArrayList<>();
//        students.add(new MySupplier<Student>(){
//            @Override
//            public Student get() {
//                return new Student(3,"uu");
//            }
//        }.get());

        // 调用方法
        List<String> stuIn = filterMap(students, student -> student.id.equals(1),
                student -> "学生学号：" + student.id + ",学生姓名:" + student.name,
                student -> System.out.println("原来学生的信息："+student.toString()));


        //打印
        System.out.println("过滤转换print");
        for (String s : stuIn) {
            System.out.println(s);
        }


    }

    //按条件过滤一个集合，然后转变成自己需要的类型
    static <T,R> List<R> filterMap(Collection<T> t, MyPredicate<T> p, MyFunction<T,R> f, MyConsumer<T> c){
        List<R> r = new ArrayList<>();
        for (T t1 : t) {
            c.accept(t1);
            if (p.test(t1)){
                r.add(f.apply(t1));
            }
        }
        return r;
    }

    static class Student{
        private Integer id;

        private String name;

        public Student(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    interface MyPredicate<T>{
        boolean test(T t);
    }

    interface MyFunction<T,R>{
        R apply(T t);
    }

    interface MyConsumer<T>{
        void accept(T t);
    }

//interface MySupplier<T>{
//    T get();
//}
}
