package nikitin.numanalysis.common;

/**
 * Created by Baragon on 12.12.13.
 */
public class InverseFunction extends AFunction {
    private AFunction a;

    public InverseFunction(AFunction a) {
        super();
        this.a = a;
    }

    @Override
    public String toString() {
        return "1/(" + a + ")";
    }

    @Override
    public double Value(double x) {
        return 1 / a.Value(x);
    }

    @Override
    public double DerivativeValue(double x) {
        return a.DerivativeValue(x) / a.Value(x) / a.Value(x);
    }

    public AFunction DerivativeFunction() {
        return new ProductFunction(new ProductFunction(new ConstantFunction(-1), new InverseFunction(new ProductFunction(a, a))), a.DerivativeFunction());
    }
}
