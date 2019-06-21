package com.test.jacoco;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by didi on 2019/6/19.
 */
public class Calculater2Test {

    @Test
    public void testAdd() {
        assertEquals(50, new Calculater2().add(46, 4));
    }

    @Test
    public void testSuntract() {
        assertEquals(3, new Calculater2().subtract(5, 2));
    }

    @Test
    public void testMultiply() throws Exception {

    }

    @Test
    public void testdivid0000e66() throws Exception {
        assertEquals(2,new Calculater2().divid0000e66(6,3));

    }
}