package com.zc.test.content;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestComputeController {

    @GetMapping("compute")
    public TestBean getTestBeanByCompute() {
        TestBean testBeanByCompute = TestUtil.getTestBeanByCompute();
        int count = testBeanByCompute.getCount();
        testBeanByCompute.setCount(++count);
        long count2 = testBeanByCompute.getCount2().addAndGet(1L);
        System.out.println("thread: " + Thread.currentThread() + " count: " + count + " count2: " + count2 + " " + testBeanByCompute.getCount2());
        return testBeanByCompute;
    }

    @GetMapping("synchronize")
    public TestBean getTestBeanBySynchronize() {
        TestBean testBeanByCompute = TestUtil.getTestBeanBySynchronize();
        int count = testBeanByCompute.getCount();
        testBeanByCompute.setCount(++count);
        long count2 = testBeanByCompute.getCount2().addAndGet(1L);
        System.out.println("thread: " + Thread.currentThread() + " count: " + count + " count2: " + count2 + " " + testBeanByCompute.getCount2());
        return testBeanByCompute;
    }

    @GetMapping("singleton")
    public TestBean getSingleton(){
        TestBean testBeanByCompute = TestUtil.getSingleton();
        int count = testBeanByCompute.getCount();
        testBeanByCompute.setCount(++count);
        long count2 = testBeanByCompute.getCount2().addAndGet(1L);
        System.out.println("thread: " + Thread.currentThread() + " count: " + count + " count2: " + count2 + " " + testBeanByCompute.getCount2());
        return testBeanByCompute;
    }
}
