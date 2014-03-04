package nikitin.numanalysis.task7;

import nikitin.numanalysis.common.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Baragon on 17.12.13.
 */
public class LagerrWeight extends AFunction {
    public double a;

    public LagerrWeight(double a) {
        super();
        this.a = a;
    }

    @Override
    public String toString() {
        return "x^(" + a + ")*e^(-x)";
    }

    @Override
    public double Value(double x) {
        return Math.pow(x, a) * Math.exp(-x);
    }

    @Override
    public double DerivativeValue(double x) {
        throw new NotImplementedException();
    }

    @Override
    public AFunction DerivativeFunction() {
        if (a != 0)
            return new SumFunction(new ProductFunction(new PowerFunction(a - 1), new InverseFunction(new ExponentFunction())), new ProductFunction(new ConstantFunction(-1),
                    new ProductFunction(new PowerFunction(a), new InverseFunction(new ExponentFunction()))));
        else
            return new ProductFunction(new ConstantFunction(-1), new InverseFunction(new ExponentFunction()));
    }
}
