package com.zc.case00.test;

import java.util.List;

public class ClassC {
    public static void doSomething(List<? extends ClassA> a){
        System.out.println("success");
    }
}
