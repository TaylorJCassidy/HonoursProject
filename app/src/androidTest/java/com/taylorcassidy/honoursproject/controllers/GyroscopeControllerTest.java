package com.taylorcassidy.honoursproject.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.hardware.SensorManager;

import androidx.test.platform.app.InstrumentationRegistry;

import com.taylorcassidy.honoursproject.filter.Vector3FilterChainer;
import com.taylorcassidy.honoursproject.models.Vector3;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GyroscopeControllerTest {
    @Mock
    Vector3FilterChainer mockFilterChainer;

    GyroscopeController gyroscopeController;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        gyroscopeController = new GyroscopeController(context.getSystemService(SensorManager.class), mockFilterChainer);
    }

    @Test
    public void testRegisterGyroscopeListener() {
        Vector3 vector3 = new Vector3(1f, 1f, 1f);
        Vector3 pointToRotate = new Vector3(0f, 0f, 9.8f);
        when(mockFilterChainer.filter(any(Vector3.class))).thenReturn(vector3);

        gyroscopeController.registerGyroscopeListener(pointToRotate);

        verify(mockFilterChainer, timeout(100).atLeastOnce()).filter(any(Vector3.class));
        assertEquals(gyroscopeController.getRotatedPoint(), pointToRotate);
    }
}
