package com.taylorcassidy.honoursproject.filter;

import com.taylorcassidy.honoursproject.filter.factories.IFilterFactory;
import com.taylorcassidy.honoursproject.filter.filters.IFilter;
import com.taylorcassidy.honoursproject.models.Vector3;

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

    public Vector3 filter(Vector3 vector) {
        return new Vector3(new float[] {
                xFilter.filter(vector.getX()),
                yFilter.filter(vector.getY()),
                zFilter.filter(vector.getZ())
        });
    }
}
