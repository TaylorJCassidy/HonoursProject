package com.taylorcassidy.honoursproject.filter.filters;

public class KalmanFilter1D implements IFilter {

    private float stateEstimate;
    private float stateTransition;
    private float errorCovariance;
    private float noiseCovariance;
    private float measurementTransition;
    private float measurementCovariance;

    public KalmanFilter1D(float stateEstimate,
                          float stateTransition,
                          float errorCovariance,
                          float noiseCovariance,
                          float measurementTransition,
                          float measurementCovariance) {
        this.stateEstimate = stateEstimate;
        this.stateTransition = stateTransition;
        this.errorCovariance = errorCovariance;
        this.noiseCovariance = noiseCovariance;
        this.measurementTransition = measurementTransition;
        this.measurementCovariance = measurementCovariance;
    }

    private void predict() {
        //f * x(t-1)
        stateEstimate = stateTransition * stateEstimate;

        //p(t) = f * p(t-1) * f + q
        errorCovariance = stateTransition * errorCovariance * stateTransition + noiseCovariance;
    }

    private void correct(float input) {
        //s = h * p(t) * h + r
        final float s = measurementTransition * errorCovariance * measurementTransition + measurementCovariance;

        //k = p(t) * h / s
        final float k = errorCovariance * measurementTransition / s;

        //y = z - h * x(t)
        final float y = input - measurementTransition * stateEstimate;

        //x(t) = x(t) + k * y
        stateEstimate = stateEstimate + k * y;

        //p(t) = (1 - k * h) * p(t)
        errorCovariance = (1 - k * measurementTransition) * errorCovariance;
    }

    @Override
    public float filter(float value) {
        predict();
        correct(value);
        return stateEstimate;
    }

    public static class AccelerationBuilder {
        public KalmanFilter1D build() {
            return new KalmanFilter1D(0f, 1f, 0.1f, 0f, 1f, 0.0025f);
        }
    }
}
