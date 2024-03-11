package com.taylorcassidy.honoursproject.models;

public class Matrix {
    private final float[][] data;
    private final int rows;
    private final int cols;

    public Matrix(float[][] data) {
        this.data = data;
        rows = data.length;
        cols = data[0].length;
    }

    public Matrix(int rows, int cols) {
        data = new float[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public void setValue(int row, int col, float value) {
        data[row][col] = value;
    }

    public float getValue(int row, int col) {
        return data[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Matrix multiply(Matrix matrix) {
        if (cols != matrix.rows) throw new MultiplicationException();
        Matrix multipliedMatrix = new Matrix(rows, matrix.cols);
        for(int row = 0; row < multipliedMatrix.rows; row++) {
            for(int col = 0; col < multipliedMatrix.cols; col++) {
                float value = 0f;
                for (int i = 0; i < cols; i++) {
                    value += (getValue(row, i) * matrix.getValue(i, col));
                }

                multipliedMatrix.setValue(row, col, value);
            }
        }
        return multipliedMatrix;
    }

    public Matrix multiply(float scalar) {
        Matrix multipliedMatrix = new Matrix(rows, cols);
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                float value = getValue(row, col) * scalar;
                multipliedMatrix.setValue(row, col, value);
            }
        }
        return multipliedMatrix;
    }

    public Matrix add(Matrix matrix) {
        if (rows != matrix.getRows() && cols != matrix.getCols()) throw new AdditionException();
        Matrix addedMatrix = new Matrix(rows, cols);
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                float value = getValue(row, col) + matrix.getValue(row, col);
                addedMatrix.setValue(row, col, value);
            }
        }
        return addedMatrix;
    }

    public Matrix subtract(Matrix matrix) {
        if (rows != matrix.getRows() && cols != matrix.getCols()) throw new SubtractionException();
        Matrix subractedMatrix = new Matrix(rows, cols);
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                float value = getValue(row, col) - matrix.getValue(row, col);
                subractedMatrix.setValue(row, col, value);
            }
        }
        return subractedMatrix;
    }

    public Matrix inverse() {
        if (rows != cols) throw new NonSquareException();
        Matrix inversedMatrix = new Matrix(rows, cols);

        //not pretty, I know
        if (rows == 1) {
            inversedMatrix.setValue(0,0, 1f / getValue(0,0));
        }
        else if (rows == 2) {
            inversedMatrix.setValue(0,0, getValue(1,1));
            inversedMatrix.setValue(0,1, -getValue(0,1));
            inversedMatrix.setValue(1,0, -getValue(1,0));
            inversedMatrix.setValue(1,1, getValue(0,0));
            float determinant = (getValue(0,0) * getValue(1,1)) - (getValue(0,1) * getValue(1,0));
            inversedMatrix = inversedMatrix.multiply(1f / determinant);
        }
        else {
            //inverting larger than a 2x2 matrix is significantly more complex
            throw new InversionException(); //TODO eventually replace
        }
        return inversedMatrix;
    }

    public Matrix transpose() {
        Matrix transposedMatrix = new Matrix(cols, rows);
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                float value = getValue(row, col);
                transposedMatrix.setValue(col, row, value);
            }
        }
        return transposedMatrix;
    }

    public static class MultiplicationException extends RuntimeException {
        public MultiplicationException() {}
    }

    public static class AdditionException extends RuntimeException {
        public AdditionException() {}
    }

    public static class SubtractionException extends RuntimeException {
        public SubtractionException() {}
    }

    public static class NonSquareException extends RuntimeException {
        public NonSquareException() {}
    }

    public static class InversionException extends RuntimeException {
        public InversionException() {}
    }
}
