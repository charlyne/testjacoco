package com.test.jacoco;

import org.junit.Test;

import static org.junit.Assert.*;

public class FindfileTest {

    @Test
    public void testFindFile() throws Exception {
        System.out.println("999");
    }

    @Test
    public void testFindFile1() throws Exception {
        Findfile findfile1=new Findfile();
        assertTrue(findfile1.findFile1("/Users/didi/Downloads/testjacoco","index.html","output.txt"));
    }
}