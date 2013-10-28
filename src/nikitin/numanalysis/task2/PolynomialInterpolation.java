package nikitin.numanalysis.task2;

import nikitin.numanalysis.common.AFunction;
import nikitin.numanalysis.common.Point;
import nikitin.numanalysis.common.SquareFunction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;


public class PolynomialInterpolation {
    private static PrintStream out;
    private static Scanner in;

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        in = new Scanner(System.in);
        out = System.out;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/task2.ini"));
        } catch (FileNotFoundException e) {
            out.println("Config file not found! Stack trace:");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(Math.sin(0.9) * (0.05) * (0.05) * (0.15) * (0.15) * 0.25 * 0.35 / 720.0);
        out.println("Задача алгебраического интерполирования.");
        out.println("Интерполяционный многочлен в форме Ньютона и в форме Лагранжа.");
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
        String rootsType = properties.getProperty("RootsType");
        //double l = Double.valueOf(properties.getProperty("left"));
        //double r = Double.valueOf(properties.getProperty("right"));
        //int m = Integer.valueOf(properties.getProperty("NumberOfRoots"));
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
        ArrayList<Point> table = new ArrayList<Point>(m);


        out.println("f(x)=" + func);
        out.printf("a=%g , b=%g , m=%d\n", l, r, m);
        out.print("Построение таблицы значений ");
        if (rootsType.equalsIgnoreCase("Random")) {
            out.println("случайным образом");
            Random rng = new Random();
            rng.setSeed(123);
            for (int i = 0; i < m + 1; i++) {
                double x = (r - l) * rng.nextDouble() + l;
                double y = func.Value(x);
                out.printf("x%d=%g f(x%d)=%g\n", i, x, i, y);
                table.add(new Point(x, y));
            }
        } else if (rootsType.equalsIgnoreCase("EquallySpaced")) {
            out.println("(равноотстоящих)");
            int i = 0;
            for (double j = l; i <= m; j += (r - l) / m) {
                double x = j;
                double y = func.Value(x);
                out.printf("x%d=%g f(x%d)=%g\n", i, x, i, y);
                table.add(new Point(x, y));
                i++;
            }
        } else if (rootsType.equalsIgnoreCase("Chebychev")) {
            out.println("(корни многочлена Чебышёва)");
            for (int i = 0; i <= m; i++) {
                double x = 0.5 * (l + r) + 0.5 * (r - l) * Math.cos((2 * i - 1) / (2.0 * (m + 1)) * Math.PI);
                double y = func.Value(x);
                out.printf("x%d=%g f(x%d)=%g\n", i, x, i, y);
                table.add(new Point(x, y));
            }

        }

        out.print("x=");
        double x = in.nextDouble();
        int n;
        do {
            out.println("Введите n(n<=m):");
            out.print("n=");
            n = in.nextInt();
            if (n > m) out.println("n не может быть больше m!");
        } while (n > m);
        Collections.sort(table, new DistanceComparator(x));
        for (int i = 0; i < n + 1; i++) {
            out.printf("x%d=%g f(x%d)=%g\n", i, table.get(i).x, i, table.get(i).y);
        }
        out.println("В форме Ньютона:");
        double resultN = NewtonPolynomial(table, n, x);
        double efnN = Math.abs(func.Value(x) - resultN);
        out.printf("Pn(x)=%.12f, f(x)=%.12f, фактическая погрешность eFn=%g\n", resultN, func.Value(x), efnN);
        out.println("В форме Лагранжа:");
        double resultL = LagrangePolynomial(table, n, x);
        double efnL = Math.abs(func.Value(x) - resultN);
        out.printf("Pn(x)=%.12f, f(x)=%.12f, фактическая погрешность eFn=%g\n", resultL, func.Value(x), efnL);
        //
    }

    public static double NewtonPolynomial(ArrayList<Point> table, int n, double x) {
        double[] diffs[] = new double[n + 1][]; //РР порядка 0,..,n
        for (int i = 0; i <= n; i++) {
            diffs[i] = new double[n + 1 - i];
        }
        for (int i = 0; i <= n; i++) {
            diffs[0][i] = table.get(i).y;
        }
        for (int i = 1; i < n + 1; i++)
            for (int j = 0; j < n + 1 - i; j++) {
                diffs[i][j] = (diffs[i - 1][j + 1] - diffs[i - 1][j]) / (table.get(i + j).x - table.get(j).x);
            }
      /*  for (int i = 0; i < n + 1; i++)
            out.printf("PP[0,%d]=%g\n", i, diffs[i][0]);
            */
        double result = 0;
        double product = 1;
        for (int i = 0; i < n + 1; i++) {
            result += diffs[i][0] * product;
            product *= x - table.get(i).x;
        }
        return result;
    }

    public static double LagrangePolynomial(ArrayList<Point> table, int n, double x) {
        double result = 0;
        for (int i = 0; i < n + 1; i++) {
            double a = 1, b = 1;
            for (int j = 0; j < n + 1; j++) {
                if (i != j) {
                    a *= x - table.get(j).x;
                    b *= table.get(i).x - table.get(j).x;
                }
            }
            result += a * table.get(i).y / b;
        }
        return result;
    }
}
