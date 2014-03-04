package nikitin.numanalysis.common;

/**
 * Created by Baragon on 12.12.13.
 */
public class IdentityFunction extends AFunction {
    @Override
    public String toString() {
        return "x";
    }

    @Override
    public double Value(double x) {
        return x;
    }

    @Override
    public double DerivativeValue(double x) {
        return 1;
    }

    @Override
    public AFunction DerivativeFunction() {
        return new ConstantFunction(1);
    }

}
