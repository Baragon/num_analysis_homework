package nikitin.numanalysis.common;

import nikitin.numanalysis.task2.DistanceComparator;
import nikitin.numanalysis.task2.PolynomialInterpolation;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 31.10.13
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */
public class PolynomialInterpolationFunction extends AFunction {
    private ArrayList<Point> table;
    private int n;
    private double constant;

    public PolynomialInterpolationFunction(ArrayList<Point> table, int n, double constant) {
        this.n = n;
        this.table = new ArrayList<Point>(table.size());
        for (Point p : table)
            this.table.add(new Point(p.x, p.y));
        this.constant = constant;
    }

    @Override
    public String toString() {
        return "Pn(x)";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double Value(double x) {
        Collections.sort(table, new DistanceComparator(x));
        double xn = PolynomialInterpolation.LagrangePolynomial(table, n, x);
        return xn + constant;
    }

    @Override
    public double DerivativeValue(double x) {
        throw new NotImplementedException();
    }
}
