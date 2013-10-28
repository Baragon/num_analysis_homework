package nikitin.numanalysis.common;

/**
 * Created with IntelliJ IDEA.
 * User: m10nds
 * Date: 18.10.13
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
public class PolynomialFunction extends AFunction {
    @Override
    public String toString() {
        return "(x-2.5)*x^2*(1.7-2x)";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double Value(double x) {
        return (x - 2.5) * x * x * (1.7 - 2 * x);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double DerivativeValue(double x) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
