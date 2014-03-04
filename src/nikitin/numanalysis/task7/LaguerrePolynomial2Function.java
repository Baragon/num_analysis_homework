package nikitin.numanalysis.task7;

import nikitin.numanalysis.common.AFunction;

/**
 * Created by Baragon on 18.12.13.
 */
public class LaguerrePolynomial2Function extends AFunction {
    @Override
    public String toString() {
        return "1/2(x^2-4x+2)";
    }

    @Override
    public double Value(double x) {
        return 0.5 * (x * x - 4 * x + 2);
    }

    @Override
    public double DerivativeValue(double x) {
        return 0.5 * (2 * x - 4);
    }
}
