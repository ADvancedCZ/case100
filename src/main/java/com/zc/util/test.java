package com.zc.util;

import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        ArrayList<ClassB1> list = new ArrayList<>();
        testMethod(list);
    }
    public static void testMethod(List<? extends ClassA1> list){
        return;
    }
}
