package nikitin.numanalysis.common;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Baragon
 * Date: 28.10.13
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
public class FiniteDifference {
    private double[][] fDiff;
    private int m;
    private ArrayList<Point> table;
    private int n;

    public FiniteDifference(int m, int n, ArrayList<Point> table) {
        this.m = m;
        this.n = n;
        this.table = table;
        fDiff = new double[m + 1][];
        for (int i = 0; i < m + 1; i++) {
            fDiff[i] = new double[m - i + 2];
        }
        Compute();
    }

    private void Compute() {
        for (int i = 0; i < m + 1; i++)
            fDiff[i][0] = table.get(i).y;
        for (int k = 1; k <= n; k++) {
            for (int i = 0; i <= m - k; i++)
                fDiff[i][k] = fDiff[i + 1][k - 1] - fDiff[i][k - 1];
        }
    }

    public double GetDiff(int k, int order) {
        if ((order > n) || (order < 0)) return 0;
        if ((k > m) || (k < 0)) return 0;
        return fDiff[k][order];
    }
}
