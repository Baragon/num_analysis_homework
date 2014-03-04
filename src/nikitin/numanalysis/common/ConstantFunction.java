package nikitin.numanalysis.common;

/**
 * Created by Baragon on 12.12.13.
 */
public class ConstantFunction extends AFunction {
    double value;

    public ConstantFunction(double val) {
        super();
        value = val;
    }

    @Override
    public String toString() {
        if (value >= 0) return String.valueOf((int) value);
        else return "(" + String.valueOf((int) value) + ")";
    }

    @Override
    public double Value(double x) {
        return value;
    }

    @Override
    public double DerivativeValue(double x) {
        return 0;
    }

    @Override
    public AFunction DerivativeFunction() {
        return new ConstantFunction(0);
    }


}
