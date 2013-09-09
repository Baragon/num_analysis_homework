package nikitin.numanalysis.task1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 09.09.13
 * Time: 19:27
 * To change this template use File | Settings | File Templates.
 */
public class NonlinearEqSolver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintStream out = System.out;
        Properties properties = new Properties();
        /*
        properties.setProperty("left","-5");
        properties.setProperty("right","5");
        properties.setProperty("epsilon","0.0001");
        properties.setProperty("separationStep","0.0001");
        properties.setProperty("functionClassName","nikitin.numanalysis.task1.SquareFunction");
        try {
            properties.store(new FileOutputStream("config.txt"), "Nonlinear Equation Solver configuration file");
        } catch (IOException e) {
            e.printStackTrace();
        }      */
        try {
            properties.load(new FileInputStream("config.txt"));
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
            out.printf("[%1$.6f,%2$.6f]; ", i.l, i.r);
        }
        out.println();
        /*
        for(Interval i : intervals)
        {
            out.printf("[%1$.6f,%2$.6f]; ", func.Value(i.l),func.Value(i.r));
        }
        */
    }

    private static ArrayList<Interval> Separate(double a, double b, double h, AFunction f) {
        ArrayList<Interval> result = new ArrayList<Interval>();
        Interval interval = new Interval(a, b);
        double leftValue = f.Value(a);
        for (double i = a; i < b; i += h) {
            if (leftValue * f.Value(i) <= 0) {
                interval.r = i;
                result.add(interval);
                interval = new Interval(i, b);
                leftValue = f.Value(i);
            }
        }
        result.add(interval);
        return result;
    }
}
