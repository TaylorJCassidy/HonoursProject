package com.taylorcassidy.honoursproject.models;

import junit.framework.TestCase;

public class Vector3Test extends TestCase {

    public void testAdd() {
        Vector3 v1 = new Vector3(1f, 1f, 1f, 10L);
        Vector3 v2 = new Vector3(2f, 3f, 4f);

        Vector3 result = v1.add(v2);

        assertEquals(result.getX(), 3f, 0.01f);
        assertEquals(result.getY(), 4f, 0.01f);
        assertEquals(result.getZ(), 5f, 0.01f);
        assertEquals(result.getTimestamp(), 10L);
    }

    public void testSubtract() {
        Vector3 v1 = new Vector3(1f, 1f, 1f, 10L);
        Vector3 v2 = new Vector3(2f, 3f, 4f);

        Vector3 result = v1.subtract(v2);

        assertEquals(result.getX(), -1f, 0.01f);
        assertEquals(result.getY(), -2f, 0.01f);
        assertEquals(result.getZ(), -3f, 0.01f);
        assertEquals(result.getTimestamp(), 10L);
    }

    public void testMultiplyByVector3() {
        Vector3 v1 = new Vector3(2f, 4f, 8f, 10L);
        Vector3 v2 = new Vector3(2f, 3f, 4f);

        Vector3 result = v1.multiply(v2);

        assertEquals(result.getX(), 4f, 0.01f);
        assertEquals(result.getY(), 12f, 0.01f);
        assertEquals(result.getZ(), 32f, 0.01f);
        assertEquals(result.getTimestamp(), 10L);
    }

    public void testMultiplyByMatrix() {
        Vector3 v = new Vector3(new float[]{3, 4, 2});
        Matrix M = new Matrix(new float[][]{{13, 9, 7}, {8, 7, 4}, {6, 4, 0}});

        Vector3 result = v.multiply(M);

        assertEquals(result.getX(), 83f, 0.01f);
        assertEquals(result.getY(), 63f, 0.01f);
        assertEquals(result.getZ(), 37f, 0.01f);
    }

    public void testMultiplyByScalar() {
        Vector3 v = new Vector3(2f, 4f, 8f, 10L);

        Vector3 result = v.multiply(2f);

        assertEquals(result.getX(), 4f, 0.01f);
        assertEquals(result.getY(), 8f, 0.01f);
        assertEquals(result.getZ(), 16f, 0.01f);
        assertEquals(result.getTimestamp(), 10L);
    }

    public void testDivide() {
        Vector3 v = new Vector3(3f, 4f, 10f, 10L);

        Vector3 result = v.divide(2f);

        assertEquals(result.getX(), 1.5f, 0.01f);
        assertEquals(result.getY(), 2f, 0.01f);
        assertEquals(result.getZ(), 5f, 0.01f);
        assertEquals(result.getTimestamp(), 10L);
    }

    public void testMagnitude() {
        Vector3 v = new Vector3(10f, 10f, 10f);

        float magnitude = v.magnitude();

        assertEquals(magnitude, 17.32f, 0.01f);
    }
}