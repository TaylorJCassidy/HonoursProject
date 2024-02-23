package com.taylorcassidy.honoursproject.filter;

import com.taylorcassidy.honoursproject.filter.filters.FIR;
import com.taylorcassidy.honoursproject.filter.filters.IFilter;
import com.taylorcassidy.honoursproject.filter.filters.Raw;
import com.taylorcassidy.honoursproject.filter.filters.coefficients.Lowpass;

public class FilterFactory {
    public enum FilterTypes {
        RAW,
        LOW_PASS
    }

    public static IFilter createFilter(FilterTypes filterType) {
        switch (filterType) {
            default:
            case RAW:
                return new Raw();
            case LOW_PASS:
                return new FIR(Lowpass.COEFFICIENTS);
        }
    }
}
