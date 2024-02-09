package com.taylorcassidy.honoursproject.models;

import java.util.Locale;

public class Acceleration {
    private final float x;
    private final float y;
    private final float z;

    public Acceleration(float[] data) {
        x = data[0];
        y = data[1];
        z = data[2];
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @Override
    public String toString() {
        return String.format(Locale.UK, "%f,%f,%f", x, y, z);
    }
}
