package nikitin.numanalysis.task12;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by kilok_000 on 26.03.14.
 */
public class EigenValues {
    private static PrintStream out;
    private static Scanner in;
    private static double eps = 0.000001;
    private static boolean matrix=true;
    public static void main(String[] args)
    {
        Locale.setDefault(Locale.ENGLISH);
        out = System.out;
        try {
            in = new Scanner(new FileInputStream("config/EigenValues.txt"));
        } catch (FileNotFoundException e) {
            out.println("File not found!");
            e.printStackTrace();
        }
        //out.println("Метод простой итерации решения систем линейных алгебраических уравнений");
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
        out.println("Матрица A:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++)
                out.printf("%8.4f ", A[i][j]);
            out.printf("%8.4f \n", A[i][n - 1]);
        }
        double lambda[] = getEigenValues(A);
        for(int i=0;i<n;i++)
            out.printf("λ[%d]=%8.6f \n",i,lambda[i]);

    }

    public static void PrintMatrix(double[][] A)
    {
        int n=A.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++)
                out.printf("%8.4f ", A[i][j]);
            out.printf("%8.4f \n", A[i][n - 1]);
        }
    }

    public static double[] getEigenValues(double[][] A)
    {
        int n = A.length;
        double[] l = new double[n];
        double[][] c = new double[n][n];
        double[] lambda = new double[n];
        double delta = 0;
        double tdelta = 0;
        int k=0;
        do{
            double max = A[0][1];
            int maxi, maxj;
            maxi = 0;
            maxj = 1;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (i != j) {
                        if (Math.abs(A[i][j]) > max) {
                            max = Math.abs(A[i][j]);
                            maxi = i;
                            maxj = j;
                        }
                    }
            if (!matrix) {
                double cos, sin, d;
                double aii, aij, ajj;
                aii = A[maxi][maxi];
                ajj = A[maxj][maxj];
                aij = A[maxi][maxj];
                d = Math.sqrt((aii - ajj) * (aii - ajj) + 4 * aij * aij);
                cos = Math.sqrt(0.5 * (1 + Math.abs(aii - ajj) / d));
                sin = Math.signum(aij * (aii - ajj)) * Math.sqrt(0.5 * (1 - Math.abs(aii - ajj) / d));

                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++) {
                        if ((i != maxi) && (i != maxj) && (j != maxi) && (j != maxj)) {
                            c[i][j] = A[i][j];
                        }
                        if (j == maxi) c[i][j] = cos * A[i][maxi] + sin * A[i][maxj];
                        if (i == maxi) c[i][j] = cos * A[j][maxi] + sin * A[j][maxj];
                        if (j == maxj) c[i][j] = -sin * A[i][maxi] + cos * A[i][maxj];
                        if (i == maxj) c[i][j] = -sin * A[j][maxi] + cos * A[j][maxj];

                    }
                c[maxi][maxi] = cos * cos * aii + 2 * cos * sin * aij + sin * sin * ajj;
                c[maxj][maxj] = sin * sin * aii - 2 * cos * sin * aij + cos * cos * ajj;
                c[maxi][maxj] = 0;
                c[maxj][maxi] = 0;
                A = c;
            }
            else{
                double t[][] = new double[n][n];
                double aii = A[maxi][maxi];
                double ajj = A[maxj][maxj];
                double aij = A[maxi][maxj];
                double d = Math.sqrt((aii - ajj) * (aii - ajj) + 4 * aij * aij);
                double cos = Math.sqrt(0.5 * (1 + Math.abs(aii - ajj) / d));
                double sin = Math.signum(aij * (aii - ajj)) * Math.sqrt(0.5 * (1 - Math.abs(aii - ajj) / d));

                for(int i=0;i<n;i++) t[i][i]=1;
                t[maxi][maxi]=cos;
                t[maxi][maxj]=-sin;
                t[maxj][maxi]=sin;
                t[maxj][maxj]=cos;
                double tt[][] = new double[n][n];
                for(int i=0;i<n;i++)
                    for(int j=0;j<n;j++)
                        tt[i][j]=t[j][i];
                c=MatrixMult(MatrixMult(tt,A),t);
                A=c;
            }
            out.println("C=");
            PrintMatrix(A);
            tdelta = delta;
            delta=0;
            k++;
            for (int i = 0; i < n; i++) {
                lambda[i] = A[i][i];
                delta += A[i][i] * A[i][i];
            }
            for(int i=0;i<n;i++)
                out.printf("λ[%d]=%8.4f ",i,lambda[i]);
            out.println();
        } while (Math.abs(delta-tdelta) > eps);
        out.println(k+" итераций");
        return lambda;
    }
    private static double[][] MatrixMult(double[][] a,double[][] b)
    {
        int n=a.length;
        double[][] c=new double[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                for(int k=0;k<n;k++)
                    c[i][j]+=a[i][k]*b[k][j];
        return c;
    }
}
