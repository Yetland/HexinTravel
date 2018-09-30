package com.example.leetcode.normal;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        List<Stu> stus = Arrays.asList(new Stu(1, "jack", Arrays.asList("java", "android")),
                new Stu(2, "jack1", Arrays.asList("python", "java", "ios")),
                new Stu(3, "jack2", Arrays.asList("php", "ios", "spring"))
        );
        //求每个学科学习的学生名字
        Map<String, List<String>> sub2NamesMap = new HashMap<>();
        List<String> subsList = new ArrayList<>();
        List<String> nameList;
        for (Stu stu : stus) {
            for (String s : stu.getSubs()) {
                if (!subsList.contains(s)) {
                    subsList.add(s);
                }
            }
        }

        for (String s : subsList) {
            nameList = new ArrayList<>();
            for (Stu stu : stus) {
                List<String> subs = stu.getSubs();
                if (subs.contains(s)){
                    nameList.add(stu.getName());
                }
            }
            sub2NamesMap.put(s,nameList);
        }

    }

    static class Stu {
        private Integer id;

        private String name;

        private List<String> subs;


        public Stu(Integer id, String name, List<String> subs) {
            this.id = id;
            this.name = name;
            this.subs = subs;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getSubs() {
            return subs;
        }

        public void setSubs(List<String> subs) {
            this.subs = subs;
        }
    }
}
