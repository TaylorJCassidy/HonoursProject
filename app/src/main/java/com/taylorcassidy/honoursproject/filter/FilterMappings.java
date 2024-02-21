package com.taylorcassidy.honoursproject.filter;

import com.taylorcassidy.honoursproject.filter.factories.FIRFactory;
import com.taylorcassidy.honoursproject.filter.factories.IFilterFactory;
import com.taylorcassidy.honoursproject.filter.factories.RawFactory;
import com.taylorcassidy.honoursproject.filter.filters.coefficients.Lowpass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilterMappings {
    private static final Map<String, IFilterFactory> MAPPINGS = Map.of(
            "Raw", new RawFactory(),
            "Low-pass", new FIRFactory(Lowpass.COEFFICIENTS)
    );

    public static IFilterFactory get(String filterType) {
        return MAPPINGS.get(filterType);
    }

    public static List<String> keys() {
        return new ArrayList<>(FilterMappings.MAPPINGS.keySet());
    }
}
