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

    private void correct(float value) {
        //S = H * P(t) * HT + R
        final Matrix S = measurementTransition.multiply(errorCovariance).multiply(transposedMeasurementTransition).add(measurementCovariance);
        //K = P(t) * HT * inv(S)
        final Matrix K = errorCovariance.multiply(transposedMeasurementTransition).multiply(S.inverse());

        //Y = z - H * x(t) TODO
        final Matrix Y = measurementTransition.multiply(stateEstimate);

        //x(t) = x(t) + K * Y
        stateEstimate = stateEstimate.add(K.multiply(Y));

        //P(t) = P(t) - K * H * P(t)
        errorCovariance = errorCovariance.subtract(K.multiply(measurementTransition).multiply(errorCovariance));
    }

    private float getEstimate() {
        return 0f;
    }

    @Override
    public float filter(float value) {
        predict(null);
        correct(value);
        return getEstimate();
    }
}
