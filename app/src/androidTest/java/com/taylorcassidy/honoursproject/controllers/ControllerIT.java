package com.taylorcassidy.honoursproject.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import android.content.Context;
import android.hardware.SensorManager;

import androidx.test.platform.app.InstrumentationRegistry;

import com.taylorcassidy.honoursproject.filter.Vector3FilterChainer;
import com.taylorcassidy.honoursproject.models.Vector3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.Consumer;

@RunWith(MockitoJUnitRunner.class)
public class ControllerIT {

    @Test
    public void controllerIntegrationTest() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        GyroscopeController gyroscopeController = new GyroscopeController(context.getSystemService(SensorManager.class), new Vector3FilterChainer(1));
        AccelerometerController accelerometerController = new AccelerometerController(context.getSystemService(SensorManager.class), new FileController(context), gyroscopeController, new Vector3FilterChainer(2));
        accelerometerController.setUseGyroscope(true);
        VelocityController velocityController = new VelocityController(accelerometerController, new FileController(context), new Vector3FilterChainer(1));
        DisplacementController displacementController = new DisplacementController(velocityController, new FileController(context));

        Consumer<Vector3> mockConsumer = mock(Consumer.class);

        displacementController.registerDisplacementListener(mockConsumer);

        verify(mockConsumer, timeout(100).atLeastOnce()).accept(new Vector3());
    }

}
