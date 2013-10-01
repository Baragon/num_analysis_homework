package nikitin.numanalysis.task2;

import nikitin.numanalysis.common.AFunction;

public class ExampleFunction extends AFunction {
    @Override
    public String toString() {
        return "1/(1+x^2)";
    }

    @Override
    public double Value(double x) {
        return 1 / (1 + x * x);
    }

    @Override
    public double DerivativeValue(double x) {
        return (2 * x) / (1 + x * x) / (1 + x * x);
    }
}
