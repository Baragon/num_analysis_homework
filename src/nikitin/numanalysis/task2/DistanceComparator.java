package nikitin.numanalysis.task2;

import nikitin.numanalysis.common.Point;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Point> {
    double x;

    @Override
    public int compare(Point o1, Point o2) {
        double dist1 = Math.abs(o1.x - x);
        double dist2 = Math.abs(o2.x - x);
        if (dist2 > dist1) return -1;
        if (dist1 > dist2) return 1;
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public DistanceComparator(double x) {
        this.x = x;
    }
}
