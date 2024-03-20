package com.taylorcassidy.honoursproject.filter.filters;

import junit.framework.TestCase;

public class FIRFilterTest extends TestCase {

    public void testFilter() {
        FIRFilter filter = new FIRFilter(new float[]{0.2f, 0.2f, 0.2f, 0.2f, 0.2f});

        assertEquals(filter.filter(1f), 0.2f, 0.01f);
        assertEquals(filter.filter(1f), 0.4f, 0.01f);
        assertEquals(filter.filter(1f), 0.6f, 0.01f);
        assertEquals(filter.filter(1f), 0.8f, 0.01f);
        assertEquals(filter.filter(1f), 1f, 0.01f);

        assertEquals(filter.filter(0f), 0.8f, 0.01f);
        assertEquals(filter.filter(0f), 0.6f, 0.01f);
        assertEquals(filter.filter(1f), 0.6f, 0.01f);
        assertEquals(filter.filter(1f), 0.6f, 0.01f);
        assertEquals(filter.filter(1f), 0.6f, 0.01f);
    }
}