package nikitin.numanalysis.task3;

import nikitin.numanalysis.common.AFunction;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 28.10.13
 * Time: 23:35
 * To change this template use File | Settings | File Templates.
 */
public class TestFunction7 extends AFunction {
    @Override
    public String toString() {
        return "1-e^(-x)+x^2";
    }

    @Override
    public double Value(double x) {
        return 1 - Math.exp(-x) + x * x;
    }

    @Override
    public double DerivativeValue(double x) {
        return Math.exp(-x) + 2 * x;
    }
}
