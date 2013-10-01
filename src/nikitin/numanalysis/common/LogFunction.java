package nikitin.numanalysis.common;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 01.10.13
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class LogFunction extends AFunction {
    @Override
    public String toString() {
        return "ln(x)";
    }

    @Override
    public double Value(double x) {
        return Math.log(x);
    }

    @Override
    public double DerivativeValue(double x) {
        return 1 / x;
    }
}
