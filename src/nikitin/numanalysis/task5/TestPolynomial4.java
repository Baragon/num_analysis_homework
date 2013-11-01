package nikitin.numanalysis.task5;

import nikitin.numanalysis.common.AFunction;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 01.11.13
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */
public class TestPolynomial4 extends AFunction {
    @Override
    public String toString() {
        return "2x^4-3x^3+x^2+4x-5";
    }

    @Override
    public double Value(double x) {
        return 2 * x * x * x * x - 3 * x * x * x + x * x + 4 * x - 5;
    }

    @Override
    public double AntiDerivativeValue(double x) {
        return 2f / 5 * x * x * x * x * x - 3f / 4 * x * x * x * x + x * x * x / 3 + 2 * x * x - 5 * x;
    }

    @Override
    public double DerivativeValue(double x) {
        return 8 * x * x * x - 9 * x * x + 2 * x + 4;
    }
}
