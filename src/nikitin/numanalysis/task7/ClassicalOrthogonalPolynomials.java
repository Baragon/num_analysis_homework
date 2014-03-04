package nikitin.numanalysis.task7;

import nikitin.numanalysis.common.*;
import nikitin.numanalysis.task1.NonlinearEqSolver;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by Baragon on 12.12.13.
 */
public class ClassicalOrthogonalPolynomials {
    private static PrintStream out;
    private static Scanner in;

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        in = new Scanner(System.in);
        out = System.out;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/task7.ini"));
        } catch (FileNotFoundException e) {
            out.println("Config file not found! Stack trace:");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("Построение КФНАСТ.");
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
        int n;
        if (properties.getProperty("NumberOfRoots").equalsIgnoreCase("?")) {
            out.println("n=");
            n = in.nextInt();
        } else {
            n = Integer.valueOf(properties.getProperty("NumberOfRoots"));
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
        func = new ExponentFunction();

        out.printf("a=%g , b=%g , n=%d\n", l, r, n);
        out.println("f(x)=" + func);

        AFunction b = new ConstantFunction(1);
        w = new ChebyshevLaguerrWeight();
        out.println("I. B(x)=" + b + " x ∈(-inf,+inf) w(x)=" + w);
        AFunction[] h = GetOrthogonalPolynomials(b, w, n);
        for (int i = 0; i <= n; i++)
            out.println("P[" + i + "]=" + h[i]);
        double mod = Math.sqrt(n * (n - 1) / 2) + 0.1;
        ArrayList<Interval> intervals = NonlinearEqSolver.Separate(-mod, mod, 0.001, h[n]);
        double[] x = new double[n];
        for (int i = 0; i < intervals.size(); i++)
            x[i] = NonlinearEqSolver.BisectionMethod(intervals.get(i), h[n], 0.0000001f, new PrintStream(new OutputStream() {
                @Override
                public void write(int b) throws IOException {

                }
            }));
        for (int i = 0; i < intervals.size(); i++) {
            out.println("x[" + i + "]=" + x[i]);
        }
        double[] A1 = new double[n];
        int factorial = 1;
        for (int i = 2; i <= n; i++) factorial *= i;
        for (int i = 0; i < n; i++) {
            A1[i] = (1 << (n + 1)) * factorial * Math.sqrt(Math.PI) / Math.pow(h[n].DerivativeFunction().Value(x[i]), 2);
            out.println("A[" + i + "]=" + A1[i]);
        }
        double s = 0;
        for (int i = 0; i < n; i++)
            s += A1[i] * func.Value(x[i]);
        out.println("J~=" + s);
        /******/
        out.print("alpha=");
        int alpha = in.nextInt();
        w = new LagerrWeight(alpha);
        b = new IdentityFunction();
        out.println("II. B(x)=" + b + " x ∈(0,+inf) w(x)=" + w);
        h = GetOrthogonalPolynomials(b, w, n);
        for (int i = 0; i <= n; i++)
            out.println("L[" + i + "]=" + h[i]);
        mod = Math.sqrt(n * (n - 1) / 2) + 0.1;
        intervals = NonlinearEqSolver.Separate(0, mod * 10, 0.001, h[n]);
        x = new double[n];
        for (int i = 0; i < intervals.size(); i++)
            x[i] = NonlinearEqSolver.BisectionMethod(intervals.get(i), h[n], 0.0000001f, new PrintStream(new OutputStream() {
                @Override
                public void write(int b) throws IOException {

                }
            }));
        for (int i = 0; i < intervals.size(); i++) {
            out.println("x[" + i + "]=" + x[i]);
        }
        double[] A2 = new double[n];
        factorial = 1;
        double gamma = 1;
        for (int i = 2; i < alpha + n + 1; i++) {
            gamma *= i;
        }
        for (int i = 2; i <= n; i++) factorial *= i;
        for (int i = 0; i < n; i++) {
            A2[i] = factorial * gamma / Math.pow(h[n].DerivativeFunction().Value(x[i]), 2) / x[i];
            out.println("A[" + i + "]=" + A2[i]);
        }
        s = 0;
        for (int i = 0; i < n; i++)
            s += A2[i] * func.Value(x[i]);
        out.println("J~=" + s);
        /********/
        w = new ConstantFunction(1);
        b = new LegendreB();
        out.println("III. B(x)=" + b + " x ∈(" + l + "," + r + ") w(x)=" + w);
        h = GetOrthogonalPolynomials(b, w, n);
        factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial *= i;
            h[i] = new ProductFunction(new InverseFunction(new ConstantFunction((1 << n) * factorial)), h[i]);
        }
        for (int i = 0; i <= n; i++)
            out.println("P[" + i + "]=" + h[i]);
        intervals = NonlinearEqSolver.Separate(-1, 1, 0.001, h[n]);
        x = new double[intervals.size()];
        for (int i = 0; i < intervals.size(); i++)
            x[i] = NonlinearEqSolver.BisectionMethod(intervals.get(i), h[n], 0.0000001f, new PrintStream(new OutputStream() {
                @Override
                public void write(int b) throws IOException {

                }
            }));
        for (int i = 0; i < intervals.size(); i++) {
            out.println("t[" + i + "]=" + x[i]);
        }
        double[] A3 = new double[intervals.size()];
        for (int i = 0; i < intervals.size(); i++) {
            A3[i] = 2 / Math.pow(h[n].DerivativeFunction().Value(x[i]), 2) / (1 - x[i] * x[i]);
            out.println("A[" + i + "]=" + A3[i]);
        }
        for (int i = 0; i < intervals.size(); i++)
            x[i] = (r - l) / 2f * x[i] + (r + l) / 2f;
        for (int i = 0; i < intervals.size(); i++)
            out.println("x[" + i + "]=" + x[i]);
        s = 0;
        for (int i = 0; i < intervals.size(); i++)
            s += A3[i] * func.Value(x[i]);
        s *= (r - l) / 2f;
        out.println("J~=" + s);
        /*******/
        w = new ChebyshevWeight();
        out.println("IV. КФ Мелера x ∈(-1;1) w(x)=" + w);
        x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = Math.cos((2 * i + 1) * Math.PI / (2 * n));
            out.println("x[" + i + "]=" + x[i]);
        }
        s = 0;
        for (int i = 0; i < n; i++) {
            s += func.Value(x[i]);
        }
        s *= Math.PI / n;
        out.println("J~=" + s);
    }

    public static AFunction[] GetOrthogonalPolynomials(AFunction b, AFunction w, int n) {
        AFunction[] h = new AFunction[n + 1];
        for (int i = 0; i <= n; i++) {
            double c = (i % 2 == 0) ? 1f : -1f;
            AFunction bn = new ConstantFunction(1);
            for (int j = 0; j < i; j++) {
                bn = new ProductFunction(bn, b);
            }
            h[i] = new ProductFunction(w, bn);
            for (int j = 0; j < i; j++) {
                h[i] = h[i].DerivativeFunction();
            }
            h[i] = new ProductFunction(new ProductFunction(new ConstantFunction(c), new InverseFunction(w)), h[i]);
        }
        return h;
    }
}
