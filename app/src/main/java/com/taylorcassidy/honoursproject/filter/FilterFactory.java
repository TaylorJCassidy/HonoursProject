package com.taylorcassidy.honoursproject.filter;

import com.taylorcassidy.honoursproject.filter.filters.CutoffFilter;
import com.taylorcassidy.honoursproject.filter.filters.FIRFilter;
import com.taylorcassidy.honoursproject.filter.filters.IFilter;
import com.taylorcassidy.honoursproject.filter.filters.KalmanFilter;
import com.taylorcassidy.honoursproject.filter.filters.RawFilter;
import com.taylorcassidy.honoursproject.filter.filters.SlidingWindowFilter;
import com.taylorcassidy.honoursproject.filter.filters.coefficients.Lowpass;

import java.util.Arrays;

public class FilterFactory {
    public enum FilterTypes {
        NONE,
        MOVING_AVERAGE,
        LOW_PASS,
        CUTOFF,
        SLIDING_WINDOW_ACC,
        SLIDING_WINDOW_VEC,
        KALMAN_ACC
    }

    public static IFilter createFilter(FilterTypes filterType) {
        switch (filterType) {
            default:
            case NONE:
                return new RawFilter();
            case MOVING_AVERAGE:
                final int size = 25;
                float[] coefficients = new float[size];
                Arrays.fill(coefficients, 1f / size);
                return new FIRFilter(coefficients);
            case LOW_PASS:
                return new FIRFilter(Lowpass.COEFFICIENTS);
            case CUTOFF:
                return new CutoffFilter();
            case SLIDING_WINDOW_ACC:
                return new SlidingWindowFilter(25, 0.05f, 0.1f);
            case SLIDING_WINDOW_VEC:
                return new SlidingWindowFilter(25, 0.0001f, Float.MAX_VALUE);
            case KALMAN_ACC:
                return new KalmanFilter.AccelerationBuilder().build();
        }
    }
}
