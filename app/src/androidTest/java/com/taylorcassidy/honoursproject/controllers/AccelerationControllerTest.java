package com.taylorcassidy.honoursproject.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import android.content.Context;
import android.hardware.SensorManager;

import androidx.test.platform.app.InstrumentationRegistry;

import com.taylorcassidy.honoursproject.filter.FilterFactory;
import com.taylorcassidy.honoursproject.models.Vector3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.function.BiConsumer;

@RunWith(MockitoJUnitRunner.class)
public class AccelerationControllerTest {

    @Test
    public void testRegisterAccelerometerListener() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FileController fileController = mock(FileController.class);
        GyroscopeController gyroscopeController = mock(GyroscopeController.class);
        AccelerometerController accelerometerController = new AccelerometerController(context.getSystemService(SensorManager.class), fileController, gyroscopeController, List.of(FilterFactory.FilterTypes.NONE));

        BiConsumer<Vector3, Long> mockConsumer = mock(BiConsumer.class);
        accelerometerController.registerAccelerometerListener(mockConsumer);

        verify(mockConsumer, timeout(100).atLeastOnce()).accept(any(Vector3.class), anyLong());
    }
}
