package nikitin.numanalysis.task13;

import nikitin.numanalysis.common.AFunction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TestF extends AFunction {

    @Override
    public String toString() {
        return "log(x+5)";
    }

    @Override
    public double Value(double x) {
        return Math.log(x + 5);
    }

    @Override
    public double DerivativeValue(double x) {
        throw new NotImplementedException();
    }
}
