package nikitin.numanalysis.task5;

import nikitin.numanalysis.common.AFunction;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 01.11.13
 * Time: 19:42
 * To change this template use File | Settings | File Templates.
 */
public class TestPolynomial3 extends AFunction {
    @Override
    public String toString() {
        return "x^3-11x^2-7x+1";
    }

    @Override
    public double Value(double x) {
        return x * x * x - 11 * x * x - 7 * x + 1;
    }

    @Override
    public double AntiDerivativeValue(double x) {
        return x * x * x * x / 4 - 11f / 3 * x * x * x - 7f / 2 * x * x + x;
    }

    @Override
    public double DerivativeValue(double x) {
        return 3 * x * x - 22 * x - 7;
    }
}
