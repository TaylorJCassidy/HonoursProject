package com.taylorcassidy.honoursproject.filter;

import com.taylorcassidy.honoursproject.filter.filters.IFilter;
import com.taylorcassidy.honoursproject.models.Vector3;

public class Vector3Filter {
    private final IFilter xFilter;
    private final IFilter yFilter;
    private final IFilter zFilter;

    public Vector3Filter(IFilter xFilter, IFilter yFilter, IFilter zFilter) {
        this.xFilter = xFilter;
        this.yFilter = yFilter;
        this.zFilter = zFilter;
    }

    public Vector3 filter(Vector3 vector) {
        return new Vector3(new float[] {
                xFilter.filter(vector.getX()),
                yFilter.filter(vector.getY()),
                zFilter.filter(vector.getZ())
        }, vector.getTimestamp());
    }
}
