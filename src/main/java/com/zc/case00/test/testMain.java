package com.zc.case00.test;

import java.util.ArrayList;

public class testMain {
    public static void main(String[] args) {
        ClassB classB = new ClassB();
        ArrayList<ClassB> classBArrayList = new ArrayList<>();
        ClassC.doSomething(classBArrayList);
    }
}
