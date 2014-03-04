package nikitin.numanalysis.task7;

import nikitin.numanalysis.common.AFunction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Baragon on 18.12.13.
 */
public class ChebyshevWeight extends AFunction {
    @Override
    public String toString() {
        return "1/sqrt(1-x^2)";
    }

    @Override
    public double Value(double x) {
        return 1 / Math.sqrt(1 - x * x);
    }

    @Override
    public double DerivativeValue(double x) {
        throw new NotImplementedException();
    }

}
