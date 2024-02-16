package com.taylorcassidy.honoursproject.filter.factories;

import com.taylorcassidy.honoursproject.filter.filters.FIR;
import com.taylorcassidy.honoursproject.filter.filters.IFilter;

public class FIRFactory implements IFilterFactory{
    private final float[] coefficients;

    public FIRFactory(float[] coefficients) {
        this.coefficients = coefficients;
    }

    @Override
    public IFilter createFilter() {
        return new FIR(coefficients);
    }
}
