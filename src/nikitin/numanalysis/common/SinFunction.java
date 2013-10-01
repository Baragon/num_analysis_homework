package nikitin.numanalysis.common;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 01.10.13
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
public class SinFunction extends AFunction {
    @Override
    public String toString() {
        return "sin(x)";
    }

    @Override
    public double Value(double x) {
        return Math.sin(x);
    }

    @Override
    public double DerivativeValue(double x) {
        return Math.cos(x);
    }
}
