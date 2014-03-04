package nikitin.numanalysis.task8;

import nikitin.numanalysis.common.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Baragon on 18.12.13.
 */
public class TestFunction10 extends AFunction {

    @Override
    public String toString() {
        return "-3*x+x^2";
    }

    @Override
    public double Value(double x) {
        return -3 * x + x * x;
    }

    @Override
    public double DerivativeValue(double x) {
        throw new NotImplementedException();
    }

    @Override
    public AFunction DerivativeFunction() {
        return new SumFunction(new ConstantFunction(-3), new ProductFunction(new ConstantFunction(2), new IdentityFunction()));
    }
}
