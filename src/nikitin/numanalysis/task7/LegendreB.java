package nikitin.numanalysis.task7;

import nikitin.numanalysis.common.AFunction;
import nikitin.numanalysis.common.ConstantFunction;
import nikitin.numanalysis.common.IdentityFunction;
import nikitin.numanalysis.common.ProductFunction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Baragon on 18.12.13.
 */
public class LegendreB extends AFunction {
    @Override
    public String toString() {
        return "x^2-1";
    }

    @Override
    public double Value(double x) {
        return x * x - 1;
    }

    @Override
    public double DerivativeValue(double x) {
        throw new NotImplementedException();
    }

    public AFunction DerivativeFunction() {
        return new ProductFunction(new ConstantFunction(2), new IdentityFunction());
    }
}
