package com.taylorcassidy.honoursproject.filter.filters;

import junit.framework.TestCase;

public class RawFilterTest extends TestCase {

    public void testFilter() {
        RawFilter filter = new RawFilter();

        float value = filter.filter(10f);

        assertEquals(value, 10f, 0.01f);
    }
}