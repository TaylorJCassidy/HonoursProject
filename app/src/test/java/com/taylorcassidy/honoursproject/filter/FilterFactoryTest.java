package com.taylorcassidy.honoursproject.filter;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.taylorcassidy.honoursproject.filter.filters.IFilter;
import com.taylorcassidy.honoursproject.filter.filters.RawFilter;

import junit.framework.TestCase;

public class FilterFactoryTest extends TestCase {

    public void testCreateFilter() {
        FilterFactory filterFactory = new FilterFactory();

        IFilter filter = filterFactory.createFilter(FilterFactory.FilterTypes.NONE);

        assertThat(filter, instanceOf(RawFilter.class));
    }

    public void testCreateVector3Filter() {
        FilterFactory spiedFilterFactory = spy(FilterFactory.class);

        spiedFilterFactory.createVector3Filter(FilterFactory.FilterTypes.NONE);

        verify(spiedFilterFactory, times(3)).createFilter(FilterFactory.FilterTypes.NONE);
    }
}