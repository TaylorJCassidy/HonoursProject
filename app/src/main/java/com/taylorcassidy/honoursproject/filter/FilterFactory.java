package com.taylorcassidy.honoursproject.filter;

import com.taylorcassidy.honoursproject.filter.filters.Cutoff;
import com.taylorcassidy.honoursproject.filter.filters.FIR;
import com.taylorcassidy.honoursproject.filter.filters.IFilter;
import com.taylorcassidy.honoursproject.filter.filters.Raw;
import com.taylorcassidy.honoursproject.filter.filters.coefficients.Lowpass;

public class FilterFactory {
    public enum FilterTypes {
        NONE,
        LOW_PASS,
        CUTOFF
    }

    public static IFilter createFilter(FilterTypes filterType) {
        switch (filterType) {
            default:
            case NONE:
                return new Raw();
            case LOW_PASS:
                return new FIR(Lowpass.COEFFICIENTS);
            case CUTOFF:
                return new Cutoff();
        }
    }
}
