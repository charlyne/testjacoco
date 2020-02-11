package com.test.jacoco;/**
 * Created by didi on 2019/6/19.
 */

/**
 * @description:
 * @author: charlyne
 * @time: 2019/6/19 4:03 PM
 */
public class Calculater2 {
    public int add(int x, int y) {
        System.out.println("方法内代码变更,验证diff正确性");
        return x + y;
    }

    public int subtract(int x, int y) {
        return x - y;
    }

    public int multiply(int x, int y) {
        return x * y;
    }

    public int divide(int x, int y) {
        return x / y;
    }
    public int divid0000e66(int x, int y) {
        return x / y;
    }
    public int divid6(int x, int y) {
        return x / y;
    }
    //add a method
    public int add(int x, int y,int z) {
        return x + y;
    }


}