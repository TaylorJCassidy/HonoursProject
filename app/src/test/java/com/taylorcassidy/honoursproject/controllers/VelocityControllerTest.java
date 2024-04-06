package com.taylorcassidy.honoursproject.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.taylorcassidy.honoursproject.filter.Vector3FilterChainer;
import com.taylorcassidy.honoursproject.models.Vector3;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.BiConsumer;

@RunWith(MockitoJUnitRunner.class)
public class VelocityControllerTest {

    @Mock
    FileController mockFileController;
    @Mock
    AccelerometerController mockAccelerometerController;
    @Mock
    Vector3FilterChainer mockFilterChainer;

    VelocityController velocityController;

    @Before
    public void setUp() {
        velocityController = new VelocityController(mockAccelerometerController, mockFileController, mockFilterChainer);
    }

    @Test
    public void testRegisterVelocityListener() {
        BiConsumer<Vector3, Long> mockConsumer = mock(BiConsumer.class);
        Vector3 filterReturn = new Vector3(1f, 1f, 1f);
        Vector3 accelerometerReturn = new Vector3(3f, 2f, 1f);

        doAnswer(i -> {
            ((BiConsumer<Vector3, Long>) i.getArgument(0)).accept(accelerometerReturn, 1L);
            return null;
        }).when(mockAccelerometerController).registerAccelerometerListener(any());
        when(mockFilterChainer.filter(accelerometerReturn.multiply(1 / 1e9f))).thenReturn(filterReturn);

        velocityController.registerVelocityListener(mockConsumer);

        verify(mockAccelerometerController).registerAccelerometerListener(any(BiConsumer.class));
        verify(mockConsumer).accept(eq(filterReturn), anyLong());
    }
}
