package nikitin.numanalysis.task4;

import nikitin.numanalysis.common.*;
import nikitin.numanalysis.task1.NonlinearEqSolver;
import nikitin.numanalysis.task2.DistanceComparator;
import nikitin.numanalysis.task2.PolynomialInterpolation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 31.10.13
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
public class ReverseInterpolation {
    private static PrintStream out;
    private static Scanner in;

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        in = new Scanner(System.in);
        out = System.out;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/task4.ini"));
        } catch (FileNotFoundException e) {
            out.println("Config file not found! Stack trace:");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("Интерполирование по равноотстоящим узлам.");
        double l;
        if (properties.getProperty("left").equalsIgnoreCase("?")) {
            out.println("a=");
            l = in.nextDouble();
        } else {
            l = Double.valueOf(properties.getProperty("left"));
        }
        double r;
        if (properties.getProperty("right").equalsIgnoreCase("?")) {
            out.println("b=");
            r = in.nextDouble();
        } else {
            r = Double.valueOf(properties.getProperty("right"));
        }
        int m;
        if (properties.getProperty("NumberOfRoots").equalsIgnoreCase("?")) {
            out.println("m=");
            m = in.nextInt();
        } else {
            m = Integer.valueOf(properties.getProperty("NumberOfRoots"));
        }
        double f;
        if (properties.getProperty("F").equalsIgnoreCase("?")) {
            out.println("F=");
            f = in.nextDouble();
        } else {
            f = Double.valueOf(properties.getProperty("F"));
        }
        String funcClassName = properties.getProperty("functionClassName");
        AFunction func;
        try {
            func = (AFunction) Class.forName(funcClassName).newInstance();
        } catch (ClassNotFoundException e) {
            out.println("No such function class!");
            func = new SquareFunction();
        } catch (Exception e) {
            e.printStackTrace();
            func = new SquareFunction();
        }
        out.println("f(x)=" + func);
        out.printf("a=%g , b=%g , m=%d\n", l, r, m);
        out.println("Построение таблицы значений");
        ArrayList<Point> table = new TableBuilder(out).CreateEquallySpaced(l, r, m, func);
        out.println("Введите степень интерполяционного многочлена(n<=m)");
        int n;
        do {
            out.print("n=");
            n = in.nextInt();
            if (n > m) out.println("n не может быть больше m!");
        } while (n > m);
        PolynomialInterpolationFunction pn = new PolynomialInterpolationFunction(table, n, -f);
        out.println("1 способ:");
        for (Point p : table) {
            double t = p.x;
            p.x = p.y;
            p.y = t;
        }
        Collections.sort(table, new DistanceComparator(f));
        double xn = PolynomialInterpolation.LagrangePolynomial(table, n, f);
        out.printf("X=%.12f, r(X)=%.12f\n", xn, Math.abs(func.Value(xn) - f));
        out.println("2 способ:");
//        double value = pn.Value(0.2);
//        out.println(value);
        ArrayList<Interval> intervals = NonlinearEqSolver.Separate(l, r, (r - l) / m, pn);
        for (Interval i : intervals) {
            double x = NonlinearEqSolver.BisectionMethod(i, pn, 0.00000001f);
            out.printf("X=%.12f, r(X)=%.12f\n", x, Math.abs(func.Value(x) - f));
        }
        table = new TableBuilder().CreateEquallySpaced(l, r, m, func);
        double h = (r - l) / m;
        double[] df = new double[table.size()];
        double[] ddf = new double[table.size()];
        df[0] = (-3 * table.get(0).y + 4 * table.get(1).y - table.get(2).y) / 2 / h;
        df[df.length - 1] = (+3 * table.get(df.length - 1).y - 4 * table.get(df.length - 2).y + table.get(df.length - 3).y) / 2 / h;

        for (int i = 1; i < table.size() - 1; i++) {
            double x = table.get(i).x;
            double y = table.get(i).y;
            df[i] = (table.get(i + 1).y - table.get(i - 1).y) / 2 / h;
            ddf[i] = (table.get(i + 1).y - 2 * table.get(i).y + table.get(i - 1).y) / h / h;
        }
        double drStart = Math.abs(df[0] - func.DerivativeValue(table.get(0).x));
        out.println("  Xi           |    F(Xi)       |  ~F'(Xi)       |    R'(F,Xi)    |    ~F\"(Xi)     |   R\"(F,Xi)    ");
        out.printf("%14.12f | %14.12f | %14.12f | %14.12f |################|#################\n", table.get(0).x, table.get(0).y, df[0], drStart);
        for (int i = 1; i < table.size() - 1; i++) {
            double dr = Math.abs(df[i] - func.DerivativeValue(table.get(i).x));
            double ddr = Math.abs(ddf[i] - func.Derivative2Value(table.get(i).x));
            out.printf("%14.12f | %14.12f | %14.12f | %14.12f | %14.12f | %14.12f\n", table.get(i).x, table.get(i).y, df[i], dr, ddf[i], ddr);
        }
        double drEnd = Math.abs(df[m] - func.DerivativeValue(table.get(m).x));
        out.printf("%14.12f | %14.12f | %14.12f | %14.12f |################|#################\n", table.get(m).x, table.get(m).y, df[m], drEnd);

    }
}
