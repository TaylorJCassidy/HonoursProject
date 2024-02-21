package com.taylorcassidy.honoursproject.controllers;

import android.content.Context;

import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.function.Consumer;

public class DisplacementController {

    private static final String HEADER_LINE = "displacementX, displacementY, displacementZ";

    private final AccelerometerController accelerometerController;
    private final FileController fileController;
    private Vector3 currentDisplacement = new Vector3();
    private long previousReadingTimestamp;

    public DisplacementController(AccelerometerController accelerometerController,  Context context) {
        this.accelerometerController = accelerometerController;
        fileController = new FileController(context);
    }

    public void registerDisplacementListener(Consumer<Vector3> consumer) {
        fileController.open(HEADER_LINE, "displacement");
        accelerometerController.registerAccelerometerListener(acceleration -> {
            if (previousReadingTimestamp != 0L) {
                float deltaT = (acceleration.getTimestamp() - previousReadingTimestamp) / 1e9f; //convert from nanoseconds to seconds
                Vector3 displacement = acceleration.multiply(deltaT); //d = a * t
                currentDisplacement = currentDisplacement.add(displacement);
            }

            previousReadingTimestamp = acceleration.getTimestamp();
            consumer.accept(currentDisplacement);
            fileController.write(currentDisplacement.toCSV());
        });
    }

    public void unregisterDisplacementController() {
        fileController.close();
        accelerometerController.unregisterAccelerometerListener();
        previousReadingTimestamp = 0L;
        currentDisplacement = new Vector3();
    }
}
