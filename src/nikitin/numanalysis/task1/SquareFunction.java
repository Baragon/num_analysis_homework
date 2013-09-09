package nikitin.numanalysis.task1;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 09.09.13
 * Time: 19:59
 * To change this template use File | Settings | File Templates.
 */
public class SquareFunction extends AFunction {

    @Override
    public String toString() {
        return "x^2";
    }

    @Override
    public double Value(double x) {
        return x * x;
    }
}
