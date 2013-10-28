package nikitin.numanalysis.task3;

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
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 16.10.13
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */
public class EquallySpacedInterpolation {
    private static PrintStream out;
    private static Scanner in;

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        in = new Scanner(System.in);
        out = System.out;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/task3.ini"));
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

        out.print("Построение таблицы значений ");
        ArrayList<Point> table = new TableBuilder(out).CreateEquallySpaced(l, r, m, func);
        out.println("Введите степень интерполяционного многочлена(n<=m)");
        int n;
        do {
            out.print("n=");
            n = in.nextInt();
            if (n > m) out.println("n не может быть больше m!");
        } while (n > m);
        double h = (r - l) / m;
        out.printf("Введите x ∈ [%g,%g]\n", l, r);
        double x;
        do {
            out.print("x=");
            x = in.nextDouble();
            if ((x < l) || (x > r)) out.println("x должен ∈ [a,b]");
        } while ((x < l) || (x > r));
        FiniteDifference fDiff = new FiniteDifference(m, n, table);
        double xl;
        double pn = 0;
        if ((x >= l) && (x <= l + h)) {
            int k0 = 0;
            double tProduct = 1;
            double factorial = 1;
            double t = (x - table.get(k0).x) / h;
            pn = table.get(k0).y;
            for (int j = 0; j < n; j++) {
                tProduct *= t - j;
                factorial *= (j + 1);
                double diff = fDiff.GetDiff(k0, j + 1);
                pn += tProduct / factorial * diff;
            }
        } else if ((x <= r) && (x >= r - h)) {
            int k0 = m;
            double tProduct = 1;
            double factorial = 1;
            double t = (x - table.get(k0).x) / h;
            pn = table.get(k0).y;
            for (int j = 0; j < n; j++) {
                tProduct *= t + j;
                factorial *= (j + 1);
                pn += tProduct / factorial * fDiff.GetDiff(m - j - 1, j + 1);
            }
        } else {

            int k0 = 0;
            for (double xk = table.get(k0).x; xk <= x; ) {
                k0++;
                xk = table.get(k0).x;
            }
            k0--;
            double tProduct = 1;
            double factorial = 1;
            double t = (x - table.get(k0).x) / h;
            pn = table.get(k0).y;
            for (int j = 0; j < n; j++) {
                int sign = 2 * (j % 2) - 1;
                tProduct *= t + sign * ((j + 1) / 2);
                factorial *= (j + 1);
                pn += tProduct / factorial * fDiff.GetDiff(k0 - ((j + 1) / 2), j + 1);
            }
        }
        out.printf("Pn=%.12f, efn=%.12f\n", pn, Math.abs(func.Value(x) - pn));
    }

}
