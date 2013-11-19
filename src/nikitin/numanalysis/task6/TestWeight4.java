package nikitin.numanalysis.task6;

import nikitin.numanalysis.common.AFunction;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 19.11.13
 * Time: 12:39
 * To change this template use File | Settings | File Templates.
 */
public class TestWeight4 extends AFunction {
    @Override
    public String toString() {
        return "x^(-1/4)";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double Value(double x) {
        return 1d / Math.pow(x, 0.25);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double DerivativeValue(double x) {
        return -0.25 * Math.pow(x, -5f / 4);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
