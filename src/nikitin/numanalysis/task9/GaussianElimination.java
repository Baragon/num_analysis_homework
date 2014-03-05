package nikitin.numanalysis.task9;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

public class GaussianElimination {
    private static PrintStream out;
    private static Scanner in;

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        out = System.out;
        try {
            in = new Scanner(new FileInputStream("config/GaussianElimination.txt"));
        } catch (FileNotFoundException e) {
            out.println("File not found!");
            e.printStackTrace();
        }
        out.println("Классический метод Гаусса решения систем линейных алгебраических уравнений");
        out.println("Ax=b");
        String line = in.nextLine();
        String[] words = line.split(" ");
        int n = words.length;

        double[][] A = new double[n][n];
        for (int j = 0; j < n; j++) A[0][j] = Double.valueOf(words[j]);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = in.nextDouble();
            }
        }
        double[] B = new double[n];
        for (int i = 0; i < n; i++) B[i] = in.nextDouble();

        out.println("Матрица A:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++)
                out.printf("%8.4f ", A[i][j]);
            out.printf("%8.4f \n", A[i][n - 1]);
        }
        out.println("B:");
        for (int i = 0; i < n; i++) out.printf("%8.4f ", B[i]);
        out.println();
        double[] Bhat = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                Bhat[i] += A[i][j];
            Bhat[i] += B[i];
        }
        out.println("B^:");
        for (int i = 0; i < n; i++) out.printf("%8.4f ", Bhat[i]);
        out.println();

        double[] X = new double[n];
        X = GaussianEliminate(A, B, Bhat);

        double[] trueBtilde = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                trueBtilde[i] += A[i][j];
            trueBtilde[i] += B[i];
        }

        out.println("A~:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++)
                out.printf("%8.4f ", A[i][j]);
            out.printf("%8.4f \n", A[i][n - 1]);
        }
        out.println("B~:");
        for (int i = 0; i < n; i++) out.printf("%8.4f ", B[i]);
        out.println();
        out.println("B^~:");
        for (int i = 0; i < n; i++) out.printf("%8.4f ", Bhat[i]);
        out.println();
        out.println("true B^~:");
        for (int i = 0; i < n; i++) out.printf("%8.4f ", trueBtilde[i]);
        out.println();
        out.println("X:");
        for (int i = 0; i < n; i++) out.printf("%8.4f ", X[i]);
        out.println();
        out.println("Проверка:");
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) sum += A[i][j] * X[j];
            out.printf("%8.4f ?= %8.4f\n", sum, B[i]);
        }
    }

    public static double[] GaussianEliminate(double[][] A, double B[], double Bt[]) {
        int n = A.length;
        double[] X = new double[n];
        //first step
        for (int i = 0; i < n; i++) {
            B[i] /= A[i][i];
            Bt[i] /= A[i][i];
            for (int j = n - 1; j >= i; j--)
                A[i][j] = A[i][j] / A[i][i];
            for (int k = i + 1; k < n; k++) {
                B[k] -= B[i] * A[k][i];
                Bt[k] -= Bt[i] * A[k][i];
                for (int j = n - 1; j >= i; j--)
                    A[k][j] -= A[i][j] * A[k][i];
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            X[i] = B[i];
            for (int j = n - 1; j > i; j--) {
                X[i] -= X[j] * A[i][j];
            }
        }
        return X;
    }

    public static double[] GaussianEliminate(double[][] A, double B[]) {
        int n = A.length;
        double[] X = new double[n];
        //first step
        for (int i = 0; i < n; i++) {
            B[i] /= A[i][i];
            for (int j = n - 1; j >= i; j--)
                A[i][j] = A[i][j] / A[i][i];
            for (int k = i + 1; k < n; k++) {
                B[k] -= B[i] * A[k][i];
                for (int j = n - 1; j >= i; j--)
                    A[k][j] -= A[i][j] * A[k][i];
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            X[i] = B[i];
            for (int j = n - 1; j > i; j--) {
                X[i] -= X[j] * A[i][j];
            }
        }
        return X;
    }
}
