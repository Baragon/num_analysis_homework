package nikitin.numanalysis.common;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 19.11.13
 * Time: 12:28
 * To change this template use File | Settings | File Templates.
 */
public class ProductFunction extends AFunction {
    private AFunction a;
    private AFunction b;

    public ProductFunction(AFunction a, AFunction b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return a.toString() + "*" + b.toString();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double Value(double x) {
        return a.Value(x) * b.Value(x);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double DerivativeValue(double x) {
        return a.DerivativeValue(x) * b.Value(x) + a.Value(x) * b.DerivativeValue(x);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
