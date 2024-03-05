package com.taylorcassidy.honoursproject.filter.filters;

public class SlidingWindowFilter implements IFilter {
    private final float[] buffer;
    private final int bufferSize;
    private int bufferPosition = 0;
    private final float stdCutoff;
    private final float meanCutoff;

    public SlidingWindowFilter(int windowSize, float stdCutoff, float meanCutoff) {
        buffer = new float[windowSize];
        bufferSize = windowSize;
        this.stdCutoff = stdCutoff;
        this.meanCutoff = meanCutoff;
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

        if (std < stdCutoff && Math.abs(mean) < meanCutoff) return 0f;
        return value;
    }
}
