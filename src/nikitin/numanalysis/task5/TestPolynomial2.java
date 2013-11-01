package nikitin.numanalysis.task5;

import nikitin.numanalysis.common.AFunction;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 01.11.13
 * Time: 19:40
 * To change this template use File | Settings | File Templates.
 */
public class TestPolynomial2 extends AFunction {
    @Override
    public String toString() {
        return "2x^2+3x-4";
    }

    @Override
    public double Value(double x) {
        return 2 * x * x + 3 * x - 4;
    }

    @Override
    public double AntiDerivativeValue(double x) {
        return 2f / 3 * x * x * x + 3f / 2 * x * x - 4 * x;
    }

    @Override
    public double DerivativeValue(double x) {
        return 4 * x + 3;
    }
}
