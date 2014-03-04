package nikitin.numanalysis.common;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class AFunction {
    public abstract String toString();

    public abstract double Value(double x);

    public abstract double DerivativeValue(double x);

    public double Derivative2Value(double x) {
        return 0;
    }

    public double AntiDerivativeValue(double x) {
        throw new NotImplementedException();
    }

    public AFunction DerivativeFunction() {
        throw new NotImplementedException();
    }
}
