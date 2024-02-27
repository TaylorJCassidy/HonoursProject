package com.taylorcassidy.honoursproject.filter;

import com.taylorcassidy.honoursproject.filter.filters.IFilter;
import com.taylorcassidy.honoursproject.models.Vector3;

public class Vector3Filter {
    private final IFilter xFilter;
    private final IFilter yFilter;
    private final IFilter zFilter;

    public Vector3Filter(FilterFactory.FilterTypes filterType) {
        xFilter = FilterFactory.createFilter(filterType);
        yFilter = FilterFactory.createFilter(filterType);
        zFilter = FilterFactory.createFilter(filterType);
    }

    public Vector3 filter(Vector3 vector) {
        return new Vector3(new float[] {
                xFilter.filter(vector.getX()),
                yFilter.filter(vector.getY()),
                zFilter.filter(vector.getZ())
        }, vector.getTimestamp());
    }
}
