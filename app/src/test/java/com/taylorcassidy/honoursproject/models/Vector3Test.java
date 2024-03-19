package com.taylorcassidy.honoursproject.models;

import junit.framework.TestCase;

public class Vector3Test extends TestCase {

    public void testMultiply() {
        Vector3 v = new Vector3(new float[]{3, 4, 2});
        Matrix M = new Matrix(new float[][]{{13, 9, 7}, {8, 7, 4}, {6, 4, 0}});

        Vector3 result = v.multiply(M);

        assertEquals(result.getX(), 83f, 0.01f);
        assertEquals(result.getY(), 63f, 0.01f);
        assertEquals(result.getZ(), 37f, 0.01f);
    }
}