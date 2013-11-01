package nikitin.numanalysis.task5;

import nikitin.numanalysis.common.AFunction;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 01.11.13
 * Time: 19:38
 * To change this template use File | Settings | File Templates.
 */
public class TestPolynomial1 extends AFunction {
    @Override
    public String toString() {
        return "3x+12";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double Value(double x) {
        return 3 * x + 12;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double AntiDerivativeValue(double x) {
        return 12f * x + 3f * x * x / 2f;
    }

    @Override
    public double DerivativeValue(double x) {
        return 3;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
