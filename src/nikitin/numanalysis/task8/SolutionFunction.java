package nikitin.numanalysis.task8;

import nikitin.numanalysis.common.AFunction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Baragon on 18.12.13.
 */
public class SolutionFunction extends AFunction {
    double x0;
    double y0;
    AFunction func;

    public SolutionFunction(double x0, double y0, AFunction func) {
        this.x0 = x0;
        this.y0 = y0;
        this.func = func;
    }

    @Override
    public String toString() {
        return "y(x)";
    }

    @Override
    public double Value(double x) {
        if (x0 == x) return y0;
        throw new NotImplementedException();
    }

    @Override
    public double DerivativeValue(double x) {
        throw new NotImplementedException();
    }

    @Override
    public AFunction DerivativeFunction() {
        return new CompositeFunction(func, this);
    }

}

