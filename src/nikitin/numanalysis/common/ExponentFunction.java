package nikitin.numanalysis.common;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 28.10.13
 * Time: 23:56
 * To change this template use File | Settings | File Templates.
 */
public class ExponentFunction extends AFunction {
    @Override
    public String toString() {
        return "e^x";
    }

    @Override
    public double Value(double x) {
        return Math.exp(x);
    }

    @Override
    public double DerivativeValue(double x) {
        return Math.exp(x);
    }
}
