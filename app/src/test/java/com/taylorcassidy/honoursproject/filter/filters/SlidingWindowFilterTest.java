package com.taylorcassidy.honoursproject.filter.filters;

import junit.framework.TestCase;

public class SlidingWindowFilterTest extends TestCase {

    public void testFilter() {
        SlidingWindowFilter filter = new SlidingWindowFilter(5, 0.1f, 0.2f);

        assertEquals(filter.filter(1f), 1f, 0.01f);
        assertEquals(filter.filter(0.1f), 0.1f, 0.01f);
        assertEquals(filter.filter(0.1f), 0.1f, 0.01f);
        assertEquals(filter.filter(0.1f), 0.1f, 0.01f);
        assertEquals(filter.filter(0.1f), 0.1f, 0.01f);

        assertEquals(filter.filter(0.1f), 0f, 0.01f);
    }
}