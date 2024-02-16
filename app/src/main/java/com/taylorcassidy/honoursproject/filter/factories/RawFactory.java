package com.taylorcassidy.honoursproject.filter.factories;

import com.taylorcassidy.honoursproject.filter.filters.IFilter;
import com.taylorcassidy.honoursproject.filter.filters.Raw;

public class RawFactory implements IFilterFactory{

    @Override
    public IFilter createFilter() {
        return new Raw();
    }
}
