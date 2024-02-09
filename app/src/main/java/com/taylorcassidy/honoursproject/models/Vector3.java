package com.taylorcassidy.honoursproject.models;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Vector3 {
    private final float x;
    private final float y;
    private final float z;

    public Vector3(float[] vector) {
        x = vector[0];
        y = vector[1];
        z = vector[2];
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

    public Vector3 subtract(Vector3 vector) {
        return new Vector3(new float[] {
                this.x - vector.getX(),
                this.y - vector.getY(),
                this.z - vector.getZ()
        });
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.UK, "x=%f, y=%f, z=%f", x, y, z);
    }

    public String toCSV() {
        return String.format(Locale.UK, "%f,%f,%f", x, y, z);
    }
}
