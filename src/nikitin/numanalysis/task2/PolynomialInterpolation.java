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
        String rootsType = properties.getProperty("RootsType");
        double l = Double.valueOf(properties.getProperty("left"));
        double r = Double.valueOf(properties.getProperty("right"));
        int m = Integer.valueOf(properties.getProperty("NumberOfRoots"));
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

        out.println("Задача алгебраического интерполирования.");
        out.println("Интерполяционный многочлен в форме Ньютона и в форме Лагранжа.");
        out.println("f(x)=" + func);
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
        }
        out.print("x=");
        double x = in.nextDouble();
        int n;
        do {
            out.print("n=");
            n = in.nextInt();
            if (n > m) out.println("n не может быть больше m!");
        } while (n > m);
        Collections.sort(table, new DistanceComparator(x));
        for (int i = 0; i < n + 1; i++) {
            out.printf("x%d=%g f(x%d)=%g\n", i, table.get(i).x, i, table.get(i).y);
        }
        double result = NewtonPolynomial(table, n, x);
        double efn = Math.abs(func.Value(x) - result);
        out.printf("Pn(x)=%g, f(x)=%g, фактическая погрешность eFn=%g", result, func.Value(x), efn);

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
        for (int i = 0; i < n + 1; i++)
            out.printf("PP[0,%d]=%g\n", i, diffs[i][0]);
        double result = 0;
        double product = 1;
        for (int i = 0; i < n + 1; i++) {
            result += diffs[i][0] * product;
            product *= x - table.get(i).x;
        }
        return result;
    }
}
