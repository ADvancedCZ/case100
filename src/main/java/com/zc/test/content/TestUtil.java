package com.zc.test.content;

import java.util.concurrent.ConcurrentHashMap;

public class TestUtil {

    private static final TestBean testBean = new TestBean();

    private static ConcurrentHashMap<String, TestBean> companyCounterMap = new ConcurrentHashMap<>();

    private static final String GLOBAL = "GLOBAL";

    public static TestBean getTestBeanByCompute() {
        return companyCounterMap.computeIfAbsent(GLOBAL, t -> new TestBean());
    }

    public static TestBean getTestBeanBySynchronize() {
        TestBean testBean = companyCounterMap.get(GLOBAL);
        if(testBean == null){
            synchronized (TestComputeController.class) {
                if(companyCounterMap.get(GLOBAL) == null) {
                    testBean = new TestBean();
                    companyCounterMap.put(GLOBAL, testBean);
                }else{
                    testBean = companyCounterMap.get(GLOBAL);
                }
            }
        }
        return testBean;
    }

    public static TestBean getSingleton(){
        return testBean;
    }
}
