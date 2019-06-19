package com.test.jacoco;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by didi on 2019/6/17.
 */
public class CalculaterTest {

    @Test
    public void testAdd() {
        assertEquals(5, new Calculater().add(1, 4));
    }

    @Test
    public void testSuntract() {
        assertEquals(3, new Calculater().subtract(5, 2));
    }

    @Test
    public void testMultiply() {
        assertEquals(10, new Calculater().multiply(5, 2));
    }

    @Test
    public void testDivide() {
        assertEquals(3, new Calculater().divide(6, 2));
    }
    @Test
    public void testDivide2() {
        assertEquals(3, new Calculater().divide2(6, 2));
    }

}