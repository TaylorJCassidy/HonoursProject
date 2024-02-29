package com.taylorcassidy.honoursproject.controllers;

import com.taylorcassidy.honoursproject.models.Vector3;

import java.util.function.Consumer;

public class DisplacementController {

    private final VelocityController velocityController;
    private Vector3 displacement = new Vector3();

    public DisplacementController(VelocityController velocityController) {
        this.velocityController = velocityController;
    }

    public void registerDisplacementListener(Consumer<Vector3> consumer) {
        velocityController.registerVelocityListener((velocity, deltaT) -> {
            if (deltaT != 0L) {
                final float t = deltaT / 1e9f; //convert from nanoseconds to seconds
                displacement = displacement.add(velocity.multiply(t)); //v * t
            }

            consumer.accept(displacement);
        });
    }

    public void unregisterDisplacementController() {
        velocityController.unregisterVelocityListener();
        displacement = new Vector3();
    }
}
