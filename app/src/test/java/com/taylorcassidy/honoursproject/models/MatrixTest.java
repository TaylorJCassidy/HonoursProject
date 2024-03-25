package com.taylorcassidy.honoursproject.models;

import junit.framework.TestCase;

public class MatrixTest extends TestCase {

    public void testMultiplyMatrix() {
        Matrix A = new Matrix(new float[][]{{1f, 2f, 3f}, {4f, 5f, 6f}});
        Matrix B = new Matrix(new float[][]{{7f, 8f}, {9f, 10f}, {11f, 12f}});

        Matrix AB = A.multiply(B);

        assertEquals(AB.getRows(), 2);
        assertEquals(AB.getCols(), 2);
        assertEquals(AB.getValue(0,0), 58f, 0.01f);
        assertEquals(AB.getValue(0,1), 64f, 0.01f);
        assertEquals(AB.getValue(1,0), 139f, 0.01f);
        assertEquals(AB.getValue(1,1), 154f, 0.01f);
    }

    public void testMultiplyMScalar() {
        Matrix A = new Matrix(new float[][]{{4f, 0f}, {1f, -9f}});

        Matrix AB = A.multiply(2f);

        assertEquals(AB.getRows(), 2);
        assertEquals(AB.getCols(), 2);
        assertEquals(AB.getValue(0,0), 8f, 0.01f);
        assertEquals(AB.getValue(0,1), 0f, 0.01f);
        assertEquals(AB.getValue(1,0), 2f, 0.01f);
        assertEquals(AB.getValue(1,1), -18f, 0.01f);
    }

    public void testAdd() {
        Matrix A = new Matrix(new float[][]{{1f, 3f}, {1f, 0f}, {1f, 2f}});
        Matrix B = new Matrix(new float[][]{{0f, 0f}, {7f, 5f}, {2f, 1f}});

        Matrix AplusB = A.add(B);

        assertEquals(AplusB.getRows(), 3);
        assertEquals(AplusB.getCols(), 2);
        assertEquals(AplusB.getValue(0,0), 1f, 0.01f);
        assertEquals(AplusB.getValue(0,1), 3f, 0.01f);
        assertEquals(AplusB.getValue(1,0), 8f, 0.01f);
        assertEquals(AplusB.getValue(1,1), 5f, 0.01f);
        assertEquals(AplusB.getValue(2,0), 3f, 0.01f);
        assertEquals(AplusB.getValue(2,1), 3f, 0.01f);
    }

    public void testSubtract() {
        Matrix A = new Matrix(new float[][]{{2f, 8f}, {0f, 9f}});
        Matrix B = new Matrix(new float[][]{{5f, 6f}, {11f, 3f}});

        Matrix AsubtractB = A.subtract(B);

        assertEquals(AsubtractB.getRows(), 2);
        assertEquals(AsubtractB.getCols(), 2);
        assertEquals(AsubtractB.getValue(0,0), -3f, 0.01f);
        assertEquals(AsubtractB.getValue(0,1), 2f, 0.01f);
        assertEquals(AsubtractB.getValue(1,0), -11f, 0.01f);
        assertEquals(AsubtractB.getValue(1,1), 6f, 0.01f);
    }

    public void testInverse2DMatrix() {
        Matrix A = new Matrix(new float[][]{{4f, 7f}, {2f, 6f}});

        Matrix inverseA = A.inverse();

        assertEquals(inverseA.getRows(), 2);
        assertEquals(inverseA.getCols(), 2);
        assertEquals(inverseA.getValue(0,0), 0.6f, 0.01f);
        assertEquals(inverseA.getValue(0,1),  -0.7f, 0.01f);
        assertEquals(inverseA.getValue(1,0), -0.2f, 0.01f);
        assertEquals(inverseA.getValue(1,1), 0.4f, 0.01f);
    }

    public void testInverse1DMatrix() {
        Matrix A = new Matrix(new float[][]{{10f}});

        Matrix inverseA = A.inverse();

        assertEquals(inverseA.getValue(0,0), 0.1f, 0.01f);
    }

    public void testTranspose() {
        Matrix A = new Matrix(new float[][]{{1f, 2f, 3f}, {4f, 5f, 6f}});

        Matrix AT = A.transpose();

        assertEquals(AT.getRows(), 3);
        assertEquals(AT.getCols(), 2);
        assertEquals(AT.getValue(0,0), 1f, 0.01f);
        assertEquals(AT.getValue(0,1), 4f, 0.01f);
        assertEquals(AT.getValue(1,0), 2f, 0.01f);
        assertEquals(AT.getValue(1,1), 5f, 0.01f);
        assertEquals(AT.getValue(2,0), 3f, 0.01f);
        assertEquals(AT.getValue(2,1), 6f, 0.01f);
    }
}