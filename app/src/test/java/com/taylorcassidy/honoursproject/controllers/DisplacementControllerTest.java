package com.taylorcassidy.honoursproject.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.taylorcassidy.honoursproject.models.Vector3;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@RunWith(MockitoJUnitRunner.class)
public class DisplacementControllerTest {

    @Mock
    FileController mockFileController;
    @Mock
    VelocityController mockVelocityController;

    DisplacementController displacementController;

    @Before
    public void setUp() {
        displacementController = new DisplacementController(mockVelocityController, mockFileController);
    }

    @Test
    public void testRegisterDisplacementListener() {
        Consumer<Vector3> mockConsumer = mock(Consumer.class);
        Vector3 velocityReturn = new Vector3(3f, 2f, 1f);

        doAnswer(i -> {
            ((BiConsumer<Vector3, Long>) i.getArgument(0)).accept(velocityReturn, 1000000L);
            return null;
        }).when(mockVelocityController).registerVelocityListener(any(BiConsumer.class));

        displacementController.registerDisplacementListener(mockConsumer);

        verify(mockConsumer).accept(velocityReturn.multiply(1000000L / 1e9f));
    }
}
