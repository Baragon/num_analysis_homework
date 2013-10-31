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

    }
}
