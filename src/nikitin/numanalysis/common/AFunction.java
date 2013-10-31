package nikitin.numanalysis.common;

public abstract class AFunction {
    public abstract String toString();

    public abstract double Value(double x);

    public abstract double DerivativeValue(double x);

    public double Derivative2Value(double x) {
        return 0;
    }
}
