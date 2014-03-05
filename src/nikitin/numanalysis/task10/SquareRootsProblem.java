package nikitin.numanalysis.task10;

import nikitin.numanalysis.task9.GaussianElimination;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by kilok_000 on 05.03.14.
 */
public class SquareRootsProblem {
    private static PrintStream out;
    private static Scanner in;
    public static void main(String args[]){
        Locale.setDefault(Locale.ENGLISH);
        out = System.out;
        try {
            in = new Scanner(new FileInputStream("config/SquareRoots.txt"));
        } catch (FileNotFoundException e) {
            out.println("File not found!");
            e.printStackTrace();
        }
        out.println("Метод квадратных корней решения систем линейных алгебраических уравнений");
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

        out.println("A=S'S");
        out.println("Найдем S и S'");
        double[][] s = new double[n][n];
        for(int i=0;i<n;i++)
        {
            for(int j=i;j<n;j++)
            {
                if(i==j)
                {
                    double sum=0;
                    for(int k=0;k<n-1;k++)
                    {
                        sum+=s[k][i]*s[k][i];
                    }
                    s[i][j]=Math.sqrt(A[i][i]-sum);
                }else
                {
                    double sum=0;
                    for(int k=0;k<i;k++)
                    {
                        sum+=s[k][i]*s[k][j];
                    }
                    s[i][j]=(A[i][j]-sum)/s[i][i];
                }
            }
        }
        out.println("Матрица S:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++)
                out.printf("%8.4f ", s[i][j]);
            out.printf("%8.4f \n", s[i][n - 1]);
        }
        out.println("Проверка: S'*S?=A");
        double[][] tA=new double[n][n];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                double sum=0;
                for(int k=0;k<n;k++)
                {
                    sum+=s[k][i]*s[k][j];
                }
                tA[i][j]=sum;
            }
        }
        out.println("S'*S:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++)
                out.printf("%8.4f ", tA[i][j]);
            out.printf("%8.4f \n", tA[i][n - 1]);
        }
        double[][] st = new double[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                st[i][j]=s[j][i];
        double[] y = new double[n];
        double[] Bt = new double[n];
        for(int i=0;i<n;i++) Bt[i]=B[i];
        y=GaussianElimination.GaussianEliminate(st,Bt);
        out.println("y:");
        for (int i = 0; i < n; i++) out.printf("%8.4f ", y[i]);
        out.println();
        double[] x = new double[n];
        x=GaussianElimination.GaussianEliminate(s,y);
        out.println("x:");
        for (int i = 0; i < n; i++) out.printf("%8.4f ", x[i]);
        out.println();

        out.println("Проверка:");
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) sum += A[i][j] * x[j];
            out.printf("%8.4f ?= %8.4f\n", sum, B[i]);
        }

    }
}
