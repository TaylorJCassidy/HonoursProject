package com.taylorcassidy.honoursproject.filter;

import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Vector3FilterChainer {
    private final List<Vector3Filter> filters;
    private final List<FilterFactory.FilterTypes> filterTypes;
    private final FilterFactory filterFactory;

    public Vector3FilterChainer(List<FilterFactory.FilterTypes> filterTypes, FilterFactory filterFactory) {
        this.filters = filterTypes.stream().map(filterFactory::createVector3Filter).collect(Collectors.toList());
        this.filterTypes = filterTypes;
        this.filterFactory = filterFactory;
    }

    public Vector3FilterChainer(int count, FilterFactory filterFactory) {
        this(new ArrayList<>(Collections.nCopies(count, FilterFactory.FilterTypes.NONE)), filterFactory);
    }

    public Vector3FilterChainer(int count) {
        this(count, new FilterFactory());
    }

    public Vector3 filter(Vector3 vector) {
        Vector3 filtered = filters.get(0).filter(vector);
        for (int i = 1; i < filters.size(); i++) {
            filtered = filters.get(i).filter(filtered);
        }
        return filtered;
    }

    public FilterFactory.FilterTypes getFilter(int idx) {
        return filterTypes.get(idx);
    }

    public void setFilter(FilterFactory.FilterTypes filterType, int idx) {
        filterTypes.set(idx, filterType);
        filters.set(idx, filterFactory.createVector3Filter(filterType));
    }
}
