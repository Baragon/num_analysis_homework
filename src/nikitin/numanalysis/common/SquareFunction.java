package nikitin.numanalysis.common;

public class SquareFunction extends AFunction {

    @Override
    public String toString() {
        return "x^2";
    }

    @Override
    public double Value(double x) {
        return x * x;
    }

    @Override
    public double DerivativeValue(double x) {
        return 2 * x;
    }
}
