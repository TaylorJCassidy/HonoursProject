package com.taylorcassidy.honoursproject.filter.filters;

public class CutoffFilter implements IFilter {
    private static final float LOWER_BOUND = 0.1f;
    @Override
    public float filter(float value) {
        if (Math.abs(value) < LOWER_BOUND) return 0f;
        return value;
    }
}
