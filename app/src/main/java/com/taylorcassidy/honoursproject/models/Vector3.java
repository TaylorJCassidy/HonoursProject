package com.taylorcassidy.honoursproject.models;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Vector3 {
    private final float x;
    private final float y;
    private final float z;

    private final long timestamp;

    public Vector3() {
        this(new float[] {0f, 0f, 0f}, 0L);
    }

    public Vector3(float[] vector, long timestamp) {
        x = vector[0];
        y = vector[1];
        z = vector[2];
        this.timestamp = timestamp;
    }

    public Vector3(float[] vector) {
        this(vector, 0L);
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

    public long getTimestamp() {
        return timestamp;
    }

    public Vector3 add(Vector3 vector) {
        return new Vector3(new float[] {
                this.x + vector.getX(),
                this.y + vector.getY(),
                this.z + vector.getZ()
        }, timestamp); //use timestamp of vector being added to
    }

    public Vector3 subtract(Vector3 vector) {
        return new Vector3(new float[] {
                this.x - vector.getX(),
                this.y - vector.getY(),
                this.z - vector.getZ()
        }, timestamp); //use timestamp of vector being subtracted from
    }

    public Vector3 multiply(Vector3 vector) {
        return new Vector3(new float[] {
                this.x * vector.getX(),
                this.y * vector.getY(),
                this.z * vector.getZ()
        }, timestamp); //use timestamp of vector being multiplied from
    }

    public Vector3 multiply(float scalar) {
        return new Vector3(new float[] {
                this.x * scalar,
                this.y * scalar,
                this.z * scalar
        }, timestamp); //use timestamp of vector being multiplied from
    }

    public float magnitude() {
        final float x2 = x*x;
        final float y2 = y*y;
        final float z2 = z*z;
        return (float) Math.sqrt(x2 + y2 + z2);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.UK, "timestamp=%d, x=%f, y=%f, z=%f", timestamp, x, y, z);
    }

    public String toCSV() {
        return String.format(Locale.UK, "%f,%f,%f", x, y, z);
    }
}
