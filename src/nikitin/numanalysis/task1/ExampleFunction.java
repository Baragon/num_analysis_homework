package nikitin.numanalysis.task1;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 09.09.13
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public class ExampleFunction extends AFunction {
    @Override
    public String toString() {
        return "(x^3-3x^2-4x+12)/3";
    }

    @Override
    public double Value(double x) {
        return (x * x * x - 3 * x * x - 4 * x + 12) / 3;
    }

    @Override
    public double DerivativeValue(double x) {
        return (3 * x * x - 6 * x - 4) / 3;
    }
}
