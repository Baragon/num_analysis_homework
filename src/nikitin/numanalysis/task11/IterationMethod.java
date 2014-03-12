package nikitin.numanalysis.task11;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

public class IterationMethod {
    private static PrintStream out;
    private static Scanner in;
    private static double eps = 0.000001;
    public static void main(String[] args)
    {
        Locale.setDefault(Locale.ENGLISH);
        out = System.out;
        try {
            in = new Scanner(new FileInputStream("config/IterationMethod.txt"));
        } catch (FileNotFoundException e) {
            out.println("File not found!");
            e.printStackTrace();
        }
        out.println("Метод простой итерации решения систем линейных алгебраических уравнений");
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
        Transform(A,B);
        out.println("Матрица A~:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++)
                out.printf("%8.4f ", A[i][j]);
            out.printf("%8.4f \n", A[i][n - 1]);
        }
        out.println("B~:");
        for (int i = 0; i < n; i++) out.printf("%8.4f ", B[i]);
        out.println();
        double[] x;
        x=SimpleIteration(A,B,eps);
        out.println("X:");
        for (int i = 0; i < n; i++) out.printf("%8.4f ", x[i]);
        out.println();
        out.println("Проверка:");
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) sum += A[i][j] * x[j];
            out.printf("%12.8f ?= %12.8f , delta=%12.8f\n", sum, B[i],Math.abs(sum-B[i]));
        }
        out.println("\n\nМетод Некрасова");
        x=NekrasovMethod(A,B,eps);
        out.println("X:");
        for (int i = 0; i < n; i++) out.printf("%8.4f ", x[i]);
        out.println();
        out.println("Проверка:");
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) sum += A[i][j] * x[j];
            out.printf("%12.8f ?= %12.8f , delta=%12.8f\n", sum, B[i],Math.abs(sum-B[i]));
        }
    }
    public static void Transform(double[][] a, double[] b){
        int n=b.length;
        for(int i=0;i<n;i++)
        {
            b[i]/=a[i][i];
            for(int j=0;j<n;j++)
                if(i!=j)
                    a[i][j]=a[i][j]/a[i][i];
            a[i][i]=1;
        }
    }
    public static double[] SimpleIteration(double[][] a, double[] b, double eps){
        int n=b.length;
        double[] x = new double[n];
        for(int i=0;i<n;i++) x[i]=1;
        double[] tx = new double[n];
        double[] t;
        int k=0;
        do{
            for(int i=0;i<n;i++){
                tx[i]=b[i];
                for(int j=0;j<n;j++){
                    if(i!=j)
                        tx[i]-=a[i][j]*x[j];
                }
            }
            t=x;x=tx;tx=t;
            k++;
        } while(GetError(x,tx)>eps);
        out.println(k+" итераций");
        return x;
    }
    public static double[] NekrasovMethod(double[][] a, double[] b, double eps){
        int n=b.length;
        double[] x = new double[n];
        for(int i=0;i<n;i++) x[i]=1;
        double[] tx = new double[n];
        double[] t;
        int k=0;
        do{
            for(int i=0;i<n;i++){
                tx[i]=b[i];
                for(int j=0;j<i;j++){
                    if(i!=j)
                        tx[i]-=a[i][j]*tx[j];
                }
                for(int j=i+1;j<n;j++){
                    if(i!=j)
                        tx[i]-=a[i][j]*x[j];
                }

            }
            t=x;x=tx;tx=t;
            k++;
        } while(GetError(x,tx)>eps);
        out.println(k+" итераций");
        return x;
    }

    private static double GetError(double[] x,double[] y){
        double max=0;
        double t=0;
        int n=x.length;
        for(int i=0;i<n;i++)
        {
            t=Math.abs(x[i]-y[i]);
            if(t>max) max=t;
        }
        return max;
    }
}
