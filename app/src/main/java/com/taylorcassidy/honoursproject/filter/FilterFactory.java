package com.taylorcassidy.honoursproject.filter;

import com.taylorcassidy.honoursproject.filter.filters.CutoffFilter;
import com.taylorcassidy.honoursproject.filter.filters.FIRFilter;
import com.taylorcassidy.honoursproject.filter.filters.IFilter;
import com.taylorcassidy.honoursproject.filter.filters.RawFilter;
import com.taylorcassidy.honoursproject.filter.filters.SlidingWindowFilter;
import com.taylorcassidy.honoursproject.filter.filters.coefficients.Lowpass;

public class FilterFactory {
    public enum FilterTypes {
        NONE,
        LOW_PASS,
        CUTOFF,
        SLIDING_WINDOW_ACC,
        SLIDING_WINDOW_VEC
    }

    public static IFilter createFilter(FilterTypes filterType) {
        switch (filterType) {
            default:
            case NONE:
                return new RawFilter();
            case LOW_PASS:
                return new FIRFilter(Lowpass.COEFFICIENTS);
            case CUTOFF:
                return new CutoffFilter();
            case SLIDING_WINDOW_ACC:
                return new SlidingWindowFilter(25, 0.05f, 0.1f);
            case SLIDING_WINDOW_VEC:
                return new SlidingWindowFilter(25, 0.0001f, Float.MAX_VALUE);
        }
    }
}
