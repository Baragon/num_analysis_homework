package nikitin.numanalysis.task8;

import nikitin.numanalysis.common.AFunction;

/**
 * Created by Baragon on 18.12.13.
 */
public class TestFunction10Solution extends AFunction {
    @Override
    public String toString() {
        return "3/(2*e^3x +1)";
    }

    @Override
    public double Value(double x) {
        return 3f / (2 * Math.exp(3 * x) + 1);
    }

    @Override
    public double DerivativeValue(double x) {
        return 0;
    }
}
