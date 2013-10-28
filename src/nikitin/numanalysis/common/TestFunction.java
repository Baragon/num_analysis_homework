package nikitin.numanalysis.common;

/**
 * Created with IntelliJ IDEA.
 * User: m10nds
 * Date: 04.10.13
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class TestFunction extends AFunction {
    @Override
    public String toString() {
        return "5x-8*ln(x)-8";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double Value(double x) {
        return 5 * x - 8 * Math.log(x) - 8;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double DerivativeValue(double x) {
        return 5 - 8 / x;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
