package com.taylorcassidy.honoursproject.models;

public class Matrix {
    private final float[][] data;

    public Matrix(float[][] data) {
        this.data = data;
    }

    public Matrix(int rows, int colums) {
        data = new float[rows][colums];
    }

    public Matrix multiply(Matrix matrix) {
        return null;
    }

    public Matrix add(Matrix matrix) {
        return null;
    }

    public Matrix subtract(Matrix matrix) {
        return null;
    }

    public Matrix inverse() {
        return null;
    }

    public Matrix transpose() {
        return null;
    }
}
