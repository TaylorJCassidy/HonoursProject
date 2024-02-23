package com.taylorcassidy.honoursproject.filter;

import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Vector3FilterChainer {
    private final List<Vector3Filter> filters;

    private Vector3FilterChainer(List<Vector3Filter> filters) {
        this.filters = filters;
    }

    public Vector3 filter(Vector3 vector) {
        Vector3 filtered = filters.get(0).filter(vector);
        for (int i = 1; i < filters.size(); i++) {
            filtered = filters.get(i).filter(filtered);
        }
        return filtered;
    }

    public static class Builder {
        private final ArrayList<FilterFactory.FilterTypes> filterTypes = new ArrayList<>();

        public Builder withFilterType(FilterFactory.FilterTypes filterType) {
            filterTypes.add(filterType);
            return this;
        }

        public Builder withFilterTypes(List<FilterFactory.FilterTypes> filterTypes) {
            this.filterTypes.addAll(filterTypes);
            return this;
        }

        public Vector3FilterChainer build() {
            List<Vector3Filter> filters = filterTypes.stream().map(Vector3Filter::new).collect(Collectors.toList());
            return new Vector3FilterChainer(filters);
        }

    }
}
