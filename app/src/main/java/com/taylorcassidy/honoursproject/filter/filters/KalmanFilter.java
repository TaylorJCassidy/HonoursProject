package com.taylorcassidy.honoursproject.filter.filters;

import com.taylorcassidy.honoursproject.models.Matrix;

public class KalmanFilter implements IFilter {

    /**
     * The state estimate matrix, equivalent to x(t)
     */
    private Matrix stateEstimate;

    /**
     * The state transition matrix, equivalent to F
     */
    private Matrix stateTransition;

    /**
     * The transposed state transition matrix, equivalent to FT
     */
    private Matrix transposedStateTransition;

    /**
     * The control input matrix, equivalent to B
     */
    private Matrix controlInput;

    /**
     * The error covariance matrix, equivalent to P(t)
     */
    private Matrix errorCovariance;

    /**
     * The noise covariance matrix, equivalent to Q
     */
    private Matrix noiseCovariance;

    /**
     * The measurement transition matrix, equivalent to H
     */
    private Matrix measurementTransition;

    /**
     * The transposed transition matrix, equivalent to HT
     */
    private Matrix transposedMeasurementTransition;

    /**
     * The measurement covariance matrix, equivalent to R
     */
    private Matrix measurementCovariance;

    public KalmanFilter(Matrix initialStateEstimate, Matrix stateTransition, Matrix controlInput, Matrix errorCovariance, Matrix noiseCovariance, Matrix measurementTransition, Matrix measurementCovariance) {
        stateEstimate = initialStateEstimate;
        this.stateTransition = stateTransition;
        this.controlInput = controlInput;
        this.errorCovariance = errorCovariance;
        this.noiseCovariance = noiseCovariance;
        this.measurementTransition = measurementTransition;
        this.measurementCovariance = measurementCovariance;
        transposedStateTransition = stateTransition.transpose();
        transposedMeasurementTransition = measurementTransition.transpose();
    }

    private void predict(Matrix input) {
        //F * x(t-1)
        final Matrix Fx = stateTransition.multiply(stateEstimate);
        if (input != null) {
            //B * u(t-1)
            final Matrix Bu = controlInput.multiply(input);
            //x(t) = F * x(t-1) + B * u(t-1)
            stateEstimate = Fx.add(Bu);
        }
        else {
            //x(t) = F * x(t-1)
            stateEstimate = Fx;
        }

        //P(t) = F * P(t-1) * FT + Q
        errorCovariance = stateTransition.multiply(errorCovariance).multiply(transposedStateTransition).add(noiseCovariance);
    }

    private void correct(Matrix value) {
        //S = H * P(t) * HT + R
        final Matrix S = measurementTransition.multiply(errorCovariance).multiply(transposedMeasurementTransition).add(measurementCovariance);
        //K = P(t) * HT * inv(S)
        final Matrix K = errorCovariance.multiply(transposedMeasurementTransition).multiply(S.inverse());

        //Y = z - H * x(t)
        final Matrix Y = value.subtract(measurementTransition.multiply(stateEstimate));

        //x(t) = x(t) + K * Y
        stateEstimate = stateEstimate.add(K.multiply(Y));

        //P(t) = P(t) - K * H * P(t)
        errorCovariance = errorCovariance.subtract(K.multiply(measurementTransition).multiply(errorCovariance));
    }

    public Matrix getStateEstimate() {
        return stateEstimate;
    }

    @Override
    public float filter(float value) {
        predict(null);
        correct(new Matrix(new float[][]{{value}}));
        return getStateEstimate().getValue(0, 0);
    }

    public static class AccelerationBuilder {
        public KalmanFilter build() {
            Matrix initialStateEstimate = new Matrix(1, 1);
            Matrix stateTransition = new Matrix(new float[][]{{1f}});
            Matrix controlInput = new Matrix(1, 1);
            Matrix errorCovariance = new Matrix(1, 1);
            Matrix noiseCovariance = new Matrix(1, 1);
            Matrix measurementTransition = new Matrix(new float[][]{{1f}});
            Matrix measurementCovariance = new Matrix(1, 1);

            return new KalmanFilter(
                    initialStateEstimate,
                    stateTransition,
                    controlInput,
                    errorCovariance,
                    noiseCovariance,
                    measurementTransition,
                    measurementCovariance
            );
        }
    }
}
