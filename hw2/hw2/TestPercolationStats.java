package hw2;

import org.junit.Test;

public class TestPercolationStats {

    @Test
    public void TestModel() {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(1, 1, pf);
        for (int N = 50; N < 60; N++){
            int threshold = ps.model(N, pf);
            System.out.println("N: " + N + "; Threshold: " + threshold);
        }

    }

    @Test
    public void TestStats() {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(200, 200, pf);
        System.out.println("Mean: " + ps.mean());
        System.out.println("Dev: " + ps.stddev());
        System.out.println("Low: " + ps.confidenceLow());
        System.out.println("High: " + ps.confidenceHigh());
    }

}
