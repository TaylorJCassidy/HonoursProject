package com.taylorcassidy.honoursproject.controllers;

import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.function.Consumer;

public class DisplacementController {

    private final VelocityController velocityController;
    private final FileController fileController;
    private Vector3 displacement = new Vector3();
    private boolean shouldLogToFile = false;

    public DisplacementController(VelocityController velocityController, FileController fileController) {
        this.velocityController = velocityController;
        this.fileController = fileController;
    }

    public void registerDisplacementListener(Consumer<Vector3> consumer) {
        if (shouldLogToFile) fileController.open("disX,disY,disZ", "displacement");
        velocityController.registerVelocityListener((velocity, deltaT) -> {
            if (deltaT != 0L) {
                final float t = deltaT / 1e9f; //convert from nanoseconds to seconds
                displacement = displacement.add(velocity.multiply(t)); //v * t
            }

            consumer.accept(displacement);
            if (shouldLogToFile) fileController.write(displacement.toCSV());
        });
    }

    public void unregisterDisplacementController() {
        velocityController.unregisterVelocityListener();
        displacement = new Vector3();
        if (shouldLogToFile) fileController.close();
    }

    public boolean isShouldLogToFile() {
        return shouldLogToFile;
    }

    public void setShouldLogToFile(boolean shouldLogToFile) {
        this.shouldLogToFile = shouldLogToFile;
    }
}
