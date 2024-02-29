package com.taylorcassidy.honoursproject.filter.filters;

public class SlidingWindowFilter implements IFilter {

    private final static float STD_CUTOFF = 0.05f;
    private final static float MEAN_CUTOFF = 0.1f;

    private final float[] buffer;
    private final int bufferSize;
    private int bufferPosition = 0;

    public SlidingWindowFilter(int windowSize) {
        buffer = new float[windowSize];
        bufferSize = windowSize;
    }

    @Override
    public float filter(float value) {
        buffer[bufferPosition] = value;
        if (++bufferPosition >= bufferSize) bufferPosition = 0;

        float mean = buffer[0];
        for (int i = 1; i < bufferSize; i++) {
            mean += buffer[i];
        }
        mean /= bufferSize;

        float variance = 0f;
        for (float data : buffer) {
            final float diff = data-mean;
            variance += (diff * diff);
        }
        final float std = (float) Math.sqrt(variance / bufferSize);

        if (Math.abs(std) < STD_CUTOFF && mean < MEAN_CUTOFF) return 0;
        return value;
    }
}
