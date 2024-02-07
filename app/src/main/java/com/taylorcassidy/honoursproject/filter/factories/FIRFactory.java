package com.taylorcassidy.honoursproject.filter.factories;

import com.taylorcassidy.honoursproject.filter.filters.FIR;
import com.taylorcassidy.honoursproject.filter.filters.IFilter;

public class FIRFactory implements IFilterFactory{

    @Override
    public IFilter createFilter() {
        return new FIR(5);
    }
}
