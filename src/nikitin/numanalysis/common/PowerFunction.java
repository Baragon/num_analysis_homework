package nikitin.numanalysis.common;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 19.11.13
 * Time: 12:32
 * To change this template use File | Settings | File Templates.
 */
public class PowerFunction extends AFunction {
    private double p;

    public PowerFunction(double p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "x^" + p;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double Value(double x) {
        return Math.pow(x, p);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double DerivativeValue(double x) {
        return p * Math.pow(x, p - 1);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
