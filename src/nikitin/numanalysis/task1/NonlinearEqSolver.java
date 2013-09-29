package nikitin.numanalysis.task1;

import nikitin.numanalysis.common.AFunction;
import nikitin.numanalysis.common.Interval;
import nikitin.numanalysis.common.SquareFunction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;


public class NonlinearEqSolver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintStream out = System.out;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/task1.ini"));
        } catch (FileNotFoundException e) {
            out.println("Config file not found! Stack trace:");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        properties.get("left");
        double l = Double.valueOf(properties.getProperty("left"));
        double r = Double.valueOf(properties.getProperty("right"));
        double eps = Double.valueOf(properties.getProperty("epsilon"));
        double h = Double.valueOf(properties.getProperty("separationStep"));
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

        out.println("Численные методы решения нелинейных алгебраических и трансцендентных уравнений");
        out.println("A=" + l + ", B=" + r + ", Epsilon=" + eps + ", h=" + h);
        out.println("f(x)=" + func);
        ArrayList<Interval> intervals = Separate(l, r, h, func);
        out.println("Результат отделения корней:");
        for (Interval i : intervals) {
            out.printf("[%f,%f]; ", i.l, i.r);
        }
        out.println();
        out.println("# Метод бисекции");
        for (Interval i : intervals) {
            out.printf("Рассматриваем интервал [%f,%f]:\n", i.l, i.r);
            BisectionMethod(i, func, eps);
        }
        out.println("# Метод Ньютона(касательных)");
        for (Interval i : intervals) {
            out.printf("Рассматриваем интервал [%f,%f]:\n", i.l, i.r);
            NewtonMethod(i, func, eps);
        }
        out.println("# Модифицированный метод Ньютона");
        for (Interval i : intervals) {
            out.printf("Рассматриваем интервал [%f,%f]:\n", i.l, i.r);
            NewtonModifiedMethod(i, func, eps);
        }
        out.println("# Метод хорд(секущих)");
        for (Interval i : intervals) {
            out.printf("Рассматриваем интервал [%f,%f]:\n", i.l, i.r);
            FalsePositionMethod(i, func, eps);
        }
    }

    private static ArrayList<Interval> Separate(double a, double b, double h, AFunction f) {
        ArrayList<Interval> result = new ArrayList<Interval>();
        Interval interval = new Interval(a, b);
        double leftValue = f.Value(a);
        for (double i = a + h; i < b; i += h) {
            if (leftValue * f.Value(i) <= 0) {
                interval.r = i;
                result.add(interval);
                interval = new Interval(i, b);
                //leftValue = f.Value(i);
            }
            interval.l = i;
            leftValue = f.Value(i);
        }
        return result;
    }

    private static void BisectionMethod(Interval interval, AFunction func, double epsilon) {
        double l = interval.l;
        double r = interval.r;
        double px;
        double x = (interval.l + interval.r) / 2;
        int n = 0;
        if (func.Value(l) == 0) {
            System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=0, Модуль невязки: %3$f)\n", n, l, 0f);
            return;
        }
        if (func.Value(r) == 0) {
            System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=0, Модуль невязки: %3$f)\n", n, r, 0f);
            return;
        }
        do {
            px = x;
            x = (l + r) / 2;
            double value = func.Value(x);
            if (value == 0) {
                System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=0, Модуль невязки: %3$f)\n", n, x, 0f);
                return;
            } else if (value * func.Value(l) < 0) {
                r = x;
            } else {
                l = x;
            }
            n++;
        } while (r - l > 2 * epsilon);
        System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=%3$f, Модуль невязки: %4$f)\n", n, x, (r - l) / 2, Math.abs(func.Value(x)));
    }

    private static void NewtonMethod(Interval interval, AFunction func, double epsilon) {
        double l = interval.l;
        double r = interval.r;
        double px;
        double x = (interval.l + interval.r) / 2;
        int n = 0;
        System.out.printf("Начальное приближение x0=%g, ", x);
        if (func.Value(l) == 0) {
            System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=0, Модуль невязки: %3$f)\n", n, l, 0f);
            return;
        }
        if (func.Value(r) == 0) {
            System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=0, Модуль невязки: %3$f)\n", n, r, 0f);
            return;
        }
        do {
            px = x;
            x = px - func.Value(px) / func.DerivativeValue(px);
            double value = func.Value(x);
            if (value == 0) {
                System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=0, Модуль невязки: %3$f)\n", n, x, 0f);
                return;
            } else if (value * func.Value(l) < 0) {
                r = x;
            } else {
                l = x;
            }
            n++;
        } while (r - l > 2 * epsilon);
        System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=%3$f, Модуль невязки: %4$f)\n", n, x, (r - l) / 2, Math.abs(func.Value(x)));
    }

    private static void NewtonModifiedMethod(Interval interval, AFunction func, double epsilon) {
        double l = interval.l;
        double r = interval.r;
        double px;
        double x = (interval.l + interval.r) / 2;
        double x0 = x;
        int n = 0;
        System.out.printf("Начальное приближение x0=%g, ", x0);
        if (func.Value(l) == 0) {
            System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=0, Модуль невязки: %3$f)\n", n, l, 0f);
            return;
        }
        if (func.Value(r) == 0) {
            System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=0, Модуль невязки: %3$f)\n", n, r, 0f);
            return;
        }
        do {
            px = x;
            x = px - func.Value(px) / func.DerivativeValue(x0);
            double value = func.Value(x);
            if (value == 0) {
                System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=0, Модуль невязки: %3$f)\n", n, x, 0f);
                return;
            } else if (value * func.Value(l) < 0) {
                r = x;
            } else {
                l = x;
            }
            n++;
        } while (r - l > 2 * epsilon);
        System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=%3$f, Модуль невязки: %4$f)\n", n, x, (r - l) / 2, Math.abs(func.Value(x)));
    }

    private static void FalsePositionMethod(Interval interval, AFunction func, double epsilon) {
        double l = interval.l;
        double r = interval.r;
        double ppx;
        double px = l;
        double x = r;
        int n = 0;
        System.out.printf("Начальное приближение x0=%g, x1=%g ", px, x);
        if (func.Value(l) == 0) {
            System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=0, Модуль невязки: %3$f)\n", n, l, 0f);
            return;
        }
        if (func.Value(r) == 0) {
            System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=0, Модуль невязки: %3$f)\n", n, r, 0f);
            return;
        }
        do {
            ppx = px;
            px = x;
            x = px - (func.Value(px) * (px - ppx)) / (func.Value(px) - func.Value(ppx));
            double value = func.Value(x);
            if (value == 0) {
                System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=0, Модуль невязки: %3$f)\n", n, x, 0f);
                return;
            } else if (value * func.Value(l) < 0) {
                r = x;
            } else {
                l = x;
            }
            n++;
        } while (r - l > 2 * epsilon);
        System.out.printf("Число шагов: %1$d, Xn= %2$f, Δ=%3$f, Модуль невязки: %4$f)\n", n, x, (r - l) / 2, Math.abs(func.Value(x)));
    }
}
