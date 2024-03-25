package com.taylorcassidy.honoursproject.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.hardware.SensorManager;

import androidx.test.platform.app.InstrumentationRegistry;

import com.taylorcassidy.honoursproject.filter.Vector3FilterChainer;
import com.taylorcassidy.honoursproject.models.Vector3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.BiConsumer;

@RunWith(MockitoJUnitRunner.class)
public class AccelerationControllerTest {

    @Test
    public void testRegisterAccelerometerListener() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FileController mockFileController = mock(FileController.class);
        GyroscopeController mockGyroscopeController = mock(GyroscopeController.class);
        BiConsumer<Vector3, Long> mockConsumer = mock(BiConsumer.class);
        Vector3FilterChainer mockFilterChainer = mock(Vector3FilterChainer.class);

        Vector3 vector3 = new Vector3(1f, 1f, 1f);
        when(mockFilterChainer.filter(any(Vector3.class))).thenReturn(vector3);

        AccelerometerController accelerometerController = new AccelerometerController(context.getSystemService(SensorManager.class), mockFileController, mockGyroscopeController, mockFilterChainer);

        accelerometerController.registerAccelerometerListener(mockConsumer);

        verify(mockFilterChainer, timeout(100).atLeastOnce()).filter(any(Vector3.class));
        verify(mockConsumer, timeout(100).atLeastOnce()).accept(eq(vector3), anyLong());
    }
}
