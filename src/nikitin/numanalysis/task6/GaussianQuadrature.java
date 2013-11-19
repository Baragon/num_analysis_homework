package nikitin.numanalysis.task6;

import nikitin.numanalysis.common.*;
import nikitin.numanalysis.task5.NumericalIntegration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 19.11.13
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
public class GaussianQuadrature {
    private static PrintStream out;
    private static Scanner in;
    private static final int n = 2;

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        in = new Scanner(System.in);
        out = System.out;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/task6.ini"));
        } catch (FileNotFoundException e) {
            out.println("Config file not found! Stack trace:");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("Вычисление интегралов при помози КФ НАСТ.");
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
        String weightClassName = properties.getProperty("weightClassName");
        AFunction w;
        try {
            w = (AFunction) Class.forName(weightClassName).newInstance();
        } catch (ClassNotFoundException e) {
            out.println("No such function class!");
            w = new SquareFunction();
        } catch (Exception e) {
            e.printStackTrace();
            w = new SquareFunction();
        }
        out.println("f(x)=" + func);
        out.println("w(x)=" + w);
        out.printf("a=%g , b=%g , m=%d\n", l, r, m);
        out.println("N=" + n);
        out.println("Построение таблицы значений весовой функции:");
        TableBuilder tb = new TableBuilder(out);
        ArrayList<Point> wTable = tb.CreateEquallySpaced(l, r, m, w);
        out.println("Вычисление моментов µ:");
        double[] mu = new double[4];
        //mu[0] = NumericalIntegration.SimpsonMethod(wTable,l,r,w);
        //out.println("µ0="+mu[0]);
        for (int i = 0; i < 4; i++) {
            mu[i] = NumericalIntegration.SimpsonMethod(wTable, l, r, new ProductFunction(w, new PowerFunction(i)));
            out.println("µ" + i + "=" + mu[i]);
        }
        Point res = SolveLinear(mu[1], mu[0], mu[2], mu[1], -mu[2], -mu[3]);
        double p = res.x;
        double q = res.y;
        out.println("Получили ω2 = x^2+" + p + "*x" + "+" + q);
        double sqrtD = Math.sqrt(p * p - 4 * q);
        double x1 = (-p + sqrtD) / 2;
        double x2 = (-p - sqrtD) / 2;
        out.println("Корни:");
        out.println("x1=" + x1 + "; x2=" + x2);
        res = SolveLinear(1, 1, x1, x2, mu[0], mu[1]);
        double a1 = res.x;
        double a2 = res.y;
        out.println("Получили коэф. КФ:");
        out.println("A1=" + a1 + "; A2=" + a2);
        out.println("Проверка(для f(x)*x^3):");
        AFunction testFunc = new PowerFunction(3);
        out.println(mu[3] + "=" + (a1 * testFunc.Value(x1) + a2 * testFunc.Value(x2)));
        out.println("J=" + (a1 * func.Value(x1) + a2 * func.Value(x2)));
    }

    private static Point SolveLinear(double a11, double a12, double a21, double a22, double b1, double b2) {
        double delta = a11 * a22 - a12 * a21;
        double delta1 = b1 * a22 - b2 * a12;
        double delta2 = b2 * a11 - b1 * a21;
        return new Point(delta1 / delta, delta2 / delta);
    }
}
