package com.taylorcassidy.honoursproject.filter.filters;

public class FIR implements IFilter {

    private final float[] buffer;
    private final int bufferSize;
    private final float[] coefficients;
    private int bufferPosition = 0;

    public FIR(float[] coefficients) {
        bufferSize = coefficients.length;
        buffer = new float[bufferSize];
        this.coefficients = coefficients;
    }

    public FIR(int coefficientCount) {
        this(generateCoefficients(coefficientCount));
    }

    @Override
    public float filter(float value) {
        buffer[bufferPosition] = value;

        float result = 0f;
        for (int i = 0; i < bufferSize; i++) {
            int index = Math.floorMod(bufferPosition - i, bufferSize);
            result += coefficients[i] * buffer[index];
        }

        if (++bufferPosition >= bufferSize) bufferPosition = 0;

        return result;
    }

    private static float[] generateCoefficients(int coefficientCount) {
        float[] coefficients = new float[coefficientCount];
        for (int i = 0; i < coefficientCount; i++) {
            coefficients[i] = 1f / (coefficientCount);
        }
        return coefficients;
    }
}
