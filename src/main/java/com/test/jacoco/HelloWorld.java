package com.test.jacoco;

/**
 * Created by zc on 2017/5/2.
 */
public class HelloWorld {
    public HelloWorld() {

    }

    public String testMethod1() {
        return "Hello World";
    }

    public int addMethod(int a, int b) {
        if (a > b) {
            int c = 5;
            System.out.print(c);
        }
        return a + b;
    }
}
