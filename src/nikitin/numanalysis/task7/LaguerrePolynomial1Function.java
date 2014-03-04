package nikitin.numanalysis.task7;

import nikitin.numanalysis.common.AFunction;

/**
 * Created by Baragon on 18.12.13.
 */
public class LaguerrePolynomial1Function extends AFunction {
    @Override
    public String toString() {
        return "-x+1";
    }

    @Override
    public double Value(double x) {
        return -x + 1;
    }

    @Override
    public double DerivativeValue(double x) {
        return -1;
    }
}
