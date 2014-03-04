package nikitin.numanalysis.task7;

import nikitin.numanalysis.common.AFunction;
import nikitin.numanalysis.common.ConstantFunction;
import nikitin.numanalysis.common.PowerFunction;
import nikitin.numanalysis.common.ProductFunction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Baragon on 12.12.13.
 */
public class ChebyshevLaguerrWeight extends AFunction {
    @Override
    public String toString() {
        return "e^(-x^2)";
    }

    @Override
    public double Value(double x) {
        return Math.exp(-x * x);
    }

    @Override
    public double DerivativeValue(double x) {
        throw new NotImplementedException();
    }

    @Override
    public AFunction DerivativeFunction() {
        return new ProductFunction(new ProductFunction(new ConstantFunction(-2), new PowerFunction(1)), new ChebyshevLaguerrWeight());
    }

}
