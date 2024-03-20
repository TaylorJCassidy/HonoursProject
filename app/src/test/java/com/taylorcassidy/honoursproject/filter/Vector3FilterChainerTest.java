package com.taylorcassidy.honoursproject.filter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.taylorcassidy.honoursproject.models.Vector3;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class Vector3FilterChainerTest extends TestCase {

    @Mock
    private FilterFactory mockFilterFactory;

    @Test
    public void testFilterWithOneFilter() {
        Vector3Filter mockVector3Filter = mock(Vector3Filter.class);
        when(mockVector3Filter.filter(any(Vector3.class))).thenReturn(new Vector3(10f, 0f, 0f));
        when(mockFilterFactory.createVector3Filter(any(FilterFactory.FilterTypes.class))).thenReturn(mockVector3Filter);

        Vector3FilterChainer filterChainer =
                new Vector3FilterChainer.Builder(mockFilterFactory)
                        .withFilterType(FilterFactory.FilterTypes.NONE)
                        .build();

        Vector3 v = filterChainer.filter(new Vector3(0f, 0f, 0f));

        verify(mockVector3Filter, times(1)).filter(new Vector3(0f, 0f, 0f));
        assertEquals(v.getX(), 10f, 0.01f);
    }

    @Test
    public void testFilterWithMultipleFilters() {
        Vector3Filter mockVector3Filter1 = mock(Vector3Filter.class);
        when(mockVector3Filter1.filter(any(Vector3.class))).thenReturn(new Vector3(10f, 0f, 0f));

        Vector3Filter mockVector3Filter2 = mock(Vector3Filter.class);
        when(mockVector3Filter2.filter(any(Vector3.class))).thenReturn(new Vector3(25f, 0f, 0f));

        when(mockFilterFactory.createVector3Filter(any(FilterFactory.FilterTypes.class))).thenReturn(mockVector3Filter1, mockVector3Filter2);

        Vector3FilterChainer filterChainer =
                new Vector3FilterChainer.Builder(mockFilterFactory)
                        .withFilterTypes(List.of(FilterFactory.FilterTypes.NONE, FilterFactory.FilterTypes.NONE))
                        .build();

        Vector3 v = filterChainer.filter(new Vector3(0f, 0f, 0f));

        verify(mockVector3Filter1, times(1)).filter(new Vector3(0f, 0f, 0f));
        verify(mockVector3Filter2, times(1)).filter(new Vector3(10f, 0f, 0f));
        assertEquals(v.getX(), 25f, 0.01f);
    }
}