package nikitin.numanalysis.task13;

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

public class FiniteDifference {
    private static PrintStream out;
    private static Scanner in;
    private static double eps = 0.000001;

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        out = System.out;
        try {
            in = new Scanner(new FileInputStream("config/FiniteDifference.txt"));
        } catch (FileNotFoundException e) {
            out.println("File not found!");
            e.printStackTrace();
        }
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/task13.ini"));
        } catch (FileNotFoundException e) {
            out.println("Config file not found! Stack trace:");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("Метод сеток для решения ОДУ 2 порядка. Метод прогонки.");
        int n = in.nextInt();
        double l = in.nextDouble();
        double r = in.nextDouble();
        double alpha1 = in.nextDouble();
        double alpha2 = in.nextDouble();
        double A = in.nextDouble();
        double beta1 = in.nextDouble();
        double beta2 = in.nextDouble();
        double B = in.nextDouble();

        String pClassName = properties.getProperty("pClassName");
        AFunction p;
        try {
            p = (AFunction) Class.forName(pClassName).newInstance();
        } catch (ClassNotFoundException e) {
            out.println("No such function class!");
            p = new SquareFunction();
        } catch (Exception e) {
            e.printStackTrace();
            p = new SquareFunction();
        }
        String qClassName = properties.getProperty("qClassName");
        AFunction q;
        try {
            q = (AFunction) Class.forName(qClassName).newInstance();
        } catch (ClassNotFoundException e) {
            out.println("No such function class!");
            q = new SquareFunction();
        } catch (Exception e) {
            e.printStackTrace();
            q = new SquareFunction();
        }
        String fClassName = properties.getProperty("fClassName");
        AFunction f;
        try {
            f = (AFunction) Class.forName(fClassName).newInstance();
        } catch (ClassNotFoundException e) {
            out.println("No such function class!");
            f = new SquareFunction();
        } catch (Exception e) {
            e.printStackTrace();
            f = new SquareFunction();
        }
        TableBuilder tb = new TableBuilder(out);
        ArrayList<Point> pTable = tb.CreateEquallySpaced(l, r, n, p);
        ArrayList<Point> qTable = tb.CreateEquallySpaced(l, r, n, q);
        ArrayList<Point> fTable = tb.CreateEquallySpaced(l, r, n, f);
        double h = (r - l) / n;
        //double kappa1=alpha2/(alpha2-alpha1*h);
        //double kappa2=beta2/(beta2-beta1*h);
        //double v1=A*h/(alpha1*h-alpha2);
        //double v2=B*h/(beta1*h-beta2);
        double kappa1, kappa2, v1, v2;
        double[] a = new double[n];
        double[] b = new double[n];
        double[] c = new double[n];
        double[] g = new double[n + 1];
        for (int i = 1; i < n; i++) {
            a[i] = 1 + h / 2 * pTable.get(i).y;
            b[i] = 2 - h * h * qTable.get(i).y;
            c[i] = 1 - h / 2 * pTable.get(i).y;
            g[i] = h * h * fTable.get(i).y;
        }
        double t1, t2, t3;
        t1 = alpha2 * b[1] - 4 * a[1] * alpha2;
        t2 = 2 * h * a[1] * A + g[1] * alpha2;
        t3 = 2 * h * a[1] * alpha1 - 3 * alpha2 * a[1] + c[1] * alpha2;
        v1 = t2 / t3;
        kappa1 = t1 / t3;

        t1 = -b[n - 1] * beta2 + 4 * c[n - 1] * beta2;
        t2 = 2 * h * c[n - 1] * B - beta2 * g[n - 1];
        t3 = 2 * h * c[n - 1] * beta1 + 3 * beta2 * c[n - 1] - a[n - 1] * beta2;
        v2 = t2 / t3;
        kappa2 = t1 / t3;
        g[0] = v1;
        g[n] = v2;
        double[][] tdm = new double[n + 1][n + 1];
        for (int i = 1; i < n; i++) {
            tdm[i][i - 1] = c[i];
            tdm[i][i] = -b[i];
            tdm[i][i + 1] = a[i];
        }
        tdm[0][0] = 1;
        tdm[0][1] = -kappa1;
        tdm[n][n - 1] = -kappa2;
        tdm[n][n] = 1;
        out.println("Трехдиагональная матрица:");
        PrintMatrix(tdm);


        double[] u = new double[n];
        double[] v = new double[n];
        u[0] = kappa1;
        v[0] = v1;
        double[] y = new double[n + 1];
        for (int i = 1; i < n; i++) {
            u[i] = a[i] / (b[i] - c[i] * u[i - 1]);
            v[i] = (c[i] * v[i - 1] - g[i]) / (b[i] - c[i] * u[i - 1]);
        }

        y[n] = (v2 + kappa2 * v[n - 1]) / (1 - kappa2 * u[n - 1]);
        for (int i = n - 1; i > -1; i--) {
            y[i] = u[i] * y[i + 1] + v[i];
        }
        out.println("Коэффициэнты u[i]:");
        for (int i = 0; i < n; i++)
            out.printf("u[%d]=%f\n", i, u[i]);
        out.println("Коэффициэнты v[i]:");
        for (int i = 0; i < n; i++)
            out.printf("v[%d]=%f\n", i, v[i]);
        out.println("Ответ:");
        for (int i = 0; i < n; i++)
            out.printf("y[%d]=%f\n", i, y[i]);
        out.println("Проверка:");
        for (int i = 0; i < n + 1; i++) {
            double sum = 0;
            for (int j = 0; j < n + 1; j++)
                sum += tdm[i][j] * y[j];
            out.printf("%f?=0\n", sum - g[i]);
        }
        out.println();
    }

    public static void PrintMatrix(double[][] A) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++)
                out.printf("%10.6f ", A[i][j]);
            out.printf("%10.6f \n", A[i][n - 1]);
        }
    }
}
