package nikitin.numanalysis.task2;

import nikitin.numanalysis.common.AFunction;

/**
 * Created with IntelliJ IDEA.
 * User: m10nds
 * Date: 18.10.13
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class TestFunction11 extends AFunction {
    @Override
    public String toString() {
        return "sin(x)+x^2";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double Value(double x) {
        return Math.sin(x) + x * x;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double DerivativeValue(double x) {
        return Math.cos(x) + 2 * x;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public double Derivative2Value(double x) {
        return -Math.sin(x) + 2;
    }
}
