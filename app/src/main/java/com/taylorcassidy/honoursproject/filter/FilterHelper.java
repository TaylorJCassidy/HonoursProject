package com.taylorcassidy.honoursproject.filter;

import com.taylorcassidy.honoursproject.filter.factories.IFilterFactory;
import com.taylorcassidy.honoursproject.filter.filters.IFilter;

public class FilterHelper {
    private final IFilter xFilter;
    private final IFilter yFilter;
    private final IFilter zFilter;

    public FilterHelper(IFilterFactory filterFactory) {
        xFilter = filterFactory.createFilter();
        yFilter = filterFactory.createFilter();
        zFilter = filterFactory.createFilter();
    }

    public float[] filter(float[] values) {
        return new float[] {
                xFilter.filter(values[0]),
                yFilter.filter(values[1]),
                zFilter.filter(values[2])
        };
    }
}
