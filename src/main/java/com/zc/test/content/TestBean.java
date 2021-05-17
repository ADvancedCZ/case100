package com.zc.test.content;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class TestBean {

    private int count;

    private AtomicLong count2 = new AtomicLong(0L);

    private String msg;

    @Override
    public String toString() {
        return "TestBean{" +
                "count=" + count +
                ", count2=" + count2 +
                ", msg='" + msg + '\'' +
                '}';
    }
}
