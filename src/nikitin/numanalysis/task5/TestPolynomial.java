package nikitin.numanalysis.task5;

import nikitin.numanalysis.common.AFunction;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 01.11.13
 * Time: 19:49
 * To change this template use File | Settings | File Templates.
 */
public class TestPolynomial extends AFunction {
    @Override
    public String toString() {
        return "x^4";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double AntiDerivativeValue(double x) {
        return x * x * x * x * x / 5;
    }

    @Override
    public double Value(double x) {
        return x * x * x * x;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double DerivativeValue(double x) {
        return 4 * x * x * x;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
