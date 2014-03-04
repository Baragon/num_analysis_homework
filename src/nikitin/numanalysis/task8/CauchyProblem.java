package nikitin.numanalysis.task8;

import nikitin.numanalysis.common.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by Baragon on 18.12.13.
 */
public class CauchyProblem {
    private static PrintStream out;
    private static Scanner in;
    private static final int n = 2;

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        in = new Scanner(System.in);
        out = System.out;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/task8.ini"));
        } catch (FileNotFoundException e) {
            out.println("Config file not found! Stack trace:");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("Численное решение Задачи Коши для ОДУ.");
        int n;
        if (properties.getProperty("NumberOfRoots").equalsIgnoreCase("?")) {
            out.println("N=");
            n = in.nextInt();
        } else {
            n = Integer.valueOf(properties.getProperty("NumberOfRoots"));
        }
        double h;
        if (properties.getProperty("h").equalsIgnoreCase("?")) {
            out.println("h=");
            h = in.nextDouble();
        } else {
            h = Double.valueOf(properties.getProperty("h"));
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
        out.println("y'(x)=f(y(x))");
        out.println("f(x)=" + func);
        out.println("N=" + n);
        out.println("h=" + h);
        out.print("x0=");
        double x0 = in.nextDouble();
        out.print("y0=");
        double y0 = in.nextDouble();
        TableBuilder tb = new TableBuilder(out);
        ArrayList<Point> table = tb.CreateEquallySpaced(x0 - h * 2, x0 + h * n, n + 2, func);
        AFunction solution = new TestFunction10Solution();
        out.println("Метод разложения в ряд Тейлора:");
        table = TaylorMethod(x0, y0, func, table);
        for (Point p : table) {
            //p.y = TaylorMethod(x0,y0,func,p.x);
            out.printf("y(%g)=%g   ;  R =%g\n", p.x, p.y, solution.Value(p.x) - p.y);
        }
        out.println("Метод Адамса:");
        table = AdamsMethod(x0, y0, func, table);
        for (Point p : table) {
            out.printf("y(%g)=%g   ;  R =%g\n", p.x, p.y, solution.Value(p.x) - p.y);
        }
        out.println("Метод Рунге-Кутта:");
        table = RungeKuttMethod(x0, y0, func, table);
        for (Point p : table) {
            out.printf("y(%g)=%g   ;  R =%g\n", p.x, p.y, solution.Value(p.x) - p.y);
        }
        out.println("Метод Эйлера:");
        table = EulerMethod(x0, y0, func, table);
        for (Point p : table) {
            out.printf("y(%g)=%g   ;  R =%g\n", p.x, p.y, solution.Value(p.x) - p.y);
        }
        out.println("Модифицированный метод Эйлера:");
        table = EulerModifiedMethod(x0, y0, func, table);
        for (Point p : table) {
            out.printf("y(%g)=%g   ;  R =%g\n", p.x, p.y, solution.Value(p.x) - p.y);
        }
        out.println("Метод Эйлера-Коши:");
        table = EulerCauchyMethod(x0, y0, func, table);
        for (Point p : table) {
            out.printf("y(%g)=%g   ;  R =%g\n", p.x, p.y, solution.Value(p.x) - p.y);
        }

    }

    public static ArrayList<Point> TaylorMethod(double x0, double y0, AFunction f, ArrayList<Point> table) {
        final int n = 5;
        AFunction dy[] = new AFunction[n];
        dy[0] = new SolutionFunction(x0, y0, f);
        for (int i = 1; i < n; i++) {
            dy[i] = dy[i - 1].DerivativeFunction();
        }

        for (Point p : table) p.y = y0;
        double factorial = 1;
        for (int i = 1; i < n; i++) {
            factorial *= i;
            for (Point p : table) p.y += dy[i].Value(x0) * Math.pow(p.x - x0, i) / factorial;
        }
        return table;
    }

    public static ArrayList<Point> AdamsMethod(double x0, double y0, AFunction f, ArrayList<Point> table) {
        final int n = 4;
        double h = table.get(1).x - table.get(0).x;
        for (int i = n + 1; i < table.size(); i++) {
            ArrayList<Point> htable = new ArrayList<Point>();
            for (int j = i - n - 1; j < i; j++) {
                htable.add(new Point(table.get(j).x, h * f.Value(table.get(j).y)));
            }
            FiniteDifference fd = new FiniteDifference(n, n, htable);
            table.get(i).y = table.get(i - 1).y + fd.GetDiff(4, 0) + 0.5 * fd.GetDiff(3, 1) + 5f / 12f * fd.GetDiff(2, 2) + 3f / 8f * fd.GetDiff(1, 3) + 251f / 720f * fd.GetDiff(0, 4);
        }
        return table;
    }

    public static ArrayList<Point> RungeKuttMethod(double x0, double y0, AFunction f, ArrayList<Point> table) {
        final int n = 4;
        double h = table.get(1).x - table.get(0).x;
        for (int i = n + 1; i < table.size(); i++) {
            double xn = table.get(i - 1).x;
            double yn = table.get(i - 1).y;
            double k1 = h * f.Value(yn);
            double k2 = h * f.Value(yn + k1 / 2);
            double k3 = h * f.Value(yn + k2 / 2);
            double k4 = h * f.Value(yn + k3);
            table.get(i).y = table.get(i - 1).y + 1f / 6f * (k1 + 2 * k2 + 2 * k3 + k4);
        }
        return table;
    }

    public static ArrayList<Point> EulerMethod(double x0, double y0, AFunction f, ArrayList<Point> table) {
        final int n = 4;
        double h = table.get(1).x - table.get(0).x;
        for (int i = n + 1; i < table.size(); i++) {
            double xn = table.get(i - 1).x;
            double yn = table.get(i - 1).y;
            table.get(i).y = yn + h * f.Value(yn);
        }
        return table;
    }

    public static ArrayList<Point> EulerModifiedMethod(double x0, double y0, AFunction f, ArrayList<Point> table) {
        final int n = 4;
        double h = table.get(1).x - table.get(0).x;
        for (int i = n + 1; i < table.size(); i++) {
            double xn = table.get(i - 1).x;
            double yn = table.get(i - 1).y;
            double yn12 = yn + h / 2 * f.Value(yn);
            table.get(i).y = yn + h * f.Value(yn12);
        }
        return table;
    }

    public static ArrayList<Point> EulerCauchyMethod(double x0, double y0, AFunction f, ArrayList<Point> table) {
        final int n = 4;
        double h = table.get(1).x - table.get(0).x;
        for (int i = n + 1; i < table.size(); i++) {
            double xn = table.get(i - 1).x;
            double yn = table.get(i - 1).y;
            double Y = yn + h * f.Value(yn);
            table.get(i).y = yn + h / 2 * (f.Value(yn) + f.Value(Y));
        }
        return table;
    }
}
