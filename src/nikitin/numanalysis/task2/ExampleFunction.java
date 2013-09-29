package nikitin.numanalysis.task2;

import nikitin.numanalysis.common.AFunction;

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
