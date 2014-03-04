package nikitin.numanalysis.task8;

import nikitin.numanalysis.common.AFunction;
import nikitin.numanalysis.common.ProductFunction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Baragon on 18.12.13.
 */
public class CompositeFunction extends AFunction {
    public AFunction a;
    public AFunction b;

    public CompositeFunction(AFunction a, AFunction b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return a + "|" + b;
    }

    @Override
    public double Value(double x) {
        return a.Value(b.Value(x));
    }

    @Override
    public double DerivativeValue(double x) {
        throw new NotImplementedException();
    }

    @Override
    public AFunction DerivativeFunction() {
        return new ProductFunction(new CompositeFunction(a.DerivativeFunction(), b), b.DerivativeFunction());
    }


}
