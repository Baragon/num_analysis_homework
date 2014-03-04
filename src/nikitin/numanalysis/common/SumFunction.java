package nikitin.numanalysis.common;

/**
 * Created by Baragon on 12.12.13.
 */
public class SumFunction extends AFunction {
    AFunction a;
    AFunction b;

    public SumFunction(AFunction a, AFunction b) {
        super();
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "(" + a + "+" + b + ")";
    }

    @Override
    public double Value(double x) {
        return a.Value(x) + b.Value(x);
    }

    @Override
    public double DerivativeValue(double x) {
        return a.DerivativeValue(x) + b.DerivativeValue(x);
    }

    @Override
    public AFunction DerivativeFunction() {
        return new SumFunction(a.DerivativeFunction(), b.DerivativeFunction());
    }

}
