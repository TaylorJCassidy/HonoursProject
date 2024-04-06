package com.taylorcassidy.honoursproject.filter;

import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.taylorcassidy.honoursproject.filter.filters.IFilter;
import com.taylorcassidy.honoursproject.models.Vector3;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)

public class Vector3FilterTest extends TestCase {
    @Mock
    private IFilter mockXFilter;
    @Mock
    private IFilter mockYFilter;
    @Mock
    private IFilter mockZFilter;


    @Test
    public void testFilter() {
        when(mockXFilter.filter(anyFloat())).thenReturn(10f);
        when(mockYFilter.filter(anyFloat())).thenReturn(20f);
        when(mockZFilter.filter(anyFloat())).thenReturn(30f);
        Vector3Filter filter = new Vector3Filter(mockXFilter, mockYFilter, mockZFilter);

        Vector3 v = new Vector3(1f, 2f, 3f, 123L);

        Vector3 f = filter.filter(v);

        verify(mockXFilter, times(1)).filter(1f);
        verify(mockYFilter, times(1)).filter(2f);
        verify(mockZFilter, times(1)).filter(3f);

        assertEquals(f.getX(), 10f);
        assertEquals(f.getY(), 20f);
        assertEquals(f.getZ(), 30f);
        assertEquals(f.getTimestamp(), v.getTimestamp());
    }
}