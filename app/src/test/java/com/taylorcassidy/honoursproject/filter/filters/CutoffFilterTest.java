package com.taylorcassidy.honoursproject.filter.filters;

import junit.framework.TestCase;

public class CutoffFilterTest extends TestCase {

    public void testFilter() {
        CutoffFilter cutoffFilter = new CutoffFilter();

        assertEquals(cutoffFilter.filter(0.099f), 0f, 0.0001f);
        assertEquals(cutoffFilter.filter(-0.099f), 0f, 0.0001f);
        assertEquals(cutoffFilter.filter(0.05f), 0f, 0.0001f);
        assertEquals(cutoffFilter.filter(-0.05f), 0f, 0.0001f);
        assertEquals(cutoffFilter.filter(0.1f), 0.1f, 0.0001f);
        assertEquals(cutoffFilter.filter(-0.1f), -0.1f, 0.0001f);
        assertEquals(cutoffFilter.filter(5f), 5f, 0.0001f);
    }
}