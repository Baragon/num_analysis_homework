package nikitin.numanalysis.task13;

import nikitin.numanalysis.common.AFunction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TestP extends AFunction {

    @Override
    public String toString() {
        return "1/sqrt(x+3)";
    }

    @Override
    public double Value(double x) {
        return 1 / Math.sqrt(x + 3);
    }

    @Override
    public double DerivativeValue(double x) {
        throw new NotImplementedException();
    }
}
