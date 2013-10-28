package nikitin.numanalysis.common;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 28.10.13
 * Time: 21:05
 * To change this template use File | Settings | File Templates.
 */
public class TableBuilder {
    private PrintStream out;
    private Random rng;

    public TableBuilder() {
        out = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        });
        rng = new Random();
    }

    public TableBuilder(PrintStream out) {
        this.out = out;
        rng = new Random();
    }

    public ArrayList<Point> CreateEquallySpaced(double l, double r, int m, AFunction func) {
        ArrayList<Point> table = new ArrayList<Point>(m + 1);
        int i = 0;
        for (double j = l; i <= m; j += (r - l) / m) {
            double x = j;
            double y = func.Value(x);
            out.printf("x%d=%g f(x%d)=%g\n", i, x, i, y);
            table.add(new Point(x, y));
            i++;
        }
        return table;
    }

    public ArrayList<Point> CreateChebychev(double l, double r, int m, AFunction func) {
        ArrayList<Point> table = new ArrayList<Point>(m + 1);
        for (int i = 0; i <= m; i++) {
            double x = 0.5 * (l + r) + 0.5 * (r - l) * Math.cos((2 * i - 1) / (2.0 * (m + 1)) * Math.PI);
            double y = func.Value(x);
            out.printf("x%d=%g f(x%d)=%g\n", i, x, i, y);
            table.add(new Point(x, y));
        }
        return table;
    }

    public ArrayList<Point> CreateRandom(double l, double r, int m, AFunction func) {
        ArrayList<Point> table = new ArrayList<Point>(m + 1);
        Random rng = new Random();
        rng.setSeed(123);
        for (int i = 0; i < m + 1; i++) {
            double x = (r - l) * rng.nextDouble() + l;
            double y = func.Value(x);
            out.printf("x%d=%g f(x%d)=%g\n", i, x, i, y);
            table.add(new Point(x, y));
        }
        return table;
    }
}
