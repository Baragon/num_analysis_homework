package nikitin.numanalysis.task5;

import nikitin.numanalysis.common.AFunction;
import nikitin.numanalysis.common.Point;
import nikitin.numanalysis.common.SquareFunction;
import nikitin.numanalysis.common.TableBuilder;

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
 * Date: 01.11.13
 * Time: 19:06
 * To change this template use File | Settings | File Templates.
 */
public class NumericalIntegration {
    private static PrintStream out;
    private static Scanner in;

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        in = new Scanner(System.in);
        out = System.out;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/task5.ini"));
        } catch (FileNotFoundException e) {
            out.println("Config file not found! Stack trace:");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("Задача обратного интерполирования.");
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
        out.println("f(x)=" + func);
        out.printf("a=%g , b=%g , m=%d\n", l, r, m);
        ArrayList<Point> table = new TableBuilder().CreateEquallySpaced(l, r, m, func);
        double midpoint = MidpointMethod(table, l, r, func);
        double trapezoidal = TrapezoidalMethod(table, l, r, func);
        double simpson = SimpsonMethod(table, l, r, func);
        double realJ = func.AntiDerivativeValue(r) - func.AntiDerivativeValue(l);
        out.println("J=" + realJ);
        out.printf("Метод средних прямоугольников: J=%.12f , eRm(f)=%.12f\n", midpoint, Math.abs(midpoint - realJ));
        out.printf("Метод трапеций               : J=%.12f , eRm(f)=%.12f\n", trapezoidal, Math.abs(trapezoidal - realJ));
        out.printf("Метод Симпсона               : J=%.12f , eRm(f)=%.12f\n", simpson, Math.abs(simpson - realJ));
    }

    public static double MidpointMethod(ArrayList<Point> table, double a, double b, AFunction p) {
        double sum = 0;
        double h = (b - a) / (table.size() - 1);
        for (int i = 1; i < table.size(); i++) {
            sum += p.Value((table.get(i - 1).x + table.get(i).x) / 2);
        }
        return sum * h;
    }

    public static double TrapezoidalMethod(ArrayList<Point> table, double a, double b, AFunction p) {
        double sum = 0;
        double h = (b - a) / (table.size() - 1);
        for (int i = 1; i < table.size(); i++) {
            sum += p.Value(table.get(i - 1).x) + p.Value(table.get(i).x);
        }
        return sum * h / 2;
    }

    public static double SimpsonMethod(ArrayList<Point> table, double a, double b, AFunction p) {
        double sum = 0;
        double h = (b - a) / (table.size() - 1);
        for (int i = 1; i < table.size(); i++) {
            sum += p.Value(table.get(i - 1).x) + 4 * p.Value(table.get(i - 1).x + h / 2) + p.Value(table.get(i).x);
        }
        return sum * h / 6;
    }
}
