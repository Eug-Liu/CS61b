package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int[] thresholds;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException("N and T must be positive numbers!");
        thresholds = new int[T];

        for (int i = 0; i < T; i++) {
            thresholds[i] = model(N, pf);
        }
    }
    public double mean() {
        return StdStats.mean(thresholds);
    }
    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    public double confidenceLow() {
        return StdStats.min(thresholds);
    }
    public double confidenceHigh() {
        return StdStats.max(thresholds);
    }

    protected int model(int N, PercolationFactory pf) {
        Percolation p = pf.make(N);
        int threshold = 0;

        while (!p.percolates()) {
            int x, y;
            do {
                x = StdRandom.uniform(N);
                y = StdRandom.uniform(N);
            } while(p.isOpen(x, y));
            p.open(x, y);
            threshold++;
        }

        return threshold;
    }
}
