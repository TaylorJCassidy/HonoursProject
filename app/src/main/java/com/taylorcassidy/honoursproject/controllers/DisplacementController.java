package com.taylorcassidy.honoursproject.controllers;

import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.function.Consumer;

public class DisplacementController {
    private final AccelerometerController accelerometerController;
    private Vector3 currentDisplacement = new Vector3();
    private long previousReadingTimestamp;

    public DisplacementController(AccelerometerController accelerometerController) {
        this.accelerometerController = accelerometerController;
    }

    public void registerDisplacementListener(Consumer<Vector3> consumer) {
        accelerometerController.registerAccelerometerListener(acceleration -> {
            if (previousReadingTimestamp != 0L) {
                float deltaT = (acceleration.getTimestamp() - previousReadingTimestamp) / 1000000000f; //convert from nanoseconds to seconds
                Vector3 displacement = acceleration.multiply(deltaT); //d = a * t
                currentDisplacement = currentDisplacement.add(displacement);
            }

            previousReadingTimestamp = acceleration.getTimestamp();
            consumer.accept(currentDisplacement);
        });
    }

    public void unregisterDisplacementController() {
        accelerometerController.unregisterAccelerometerListener();
        previousReadingTimestamp = 0L;
        currentDisplacement = new Vector3();
    }
}
