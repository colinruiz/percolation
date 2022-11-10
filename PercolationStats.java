import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private Percolation p;//delcares a percolation object which is named p
    private double[] mean;//declares a double array which holds the percolation threshold
    private int trial;//declares a trial vairable

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "Input a valid number for trials or the size of your grid");
        }

        trial = trials;
        mean = new double[trials];


        for (int i = 0; i < trials; i++) {
            //instantiates a new percolation object of size n
            p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(n), StdRandom.uniform(n));
            }
            //puts the percolation threshold of each trial into the array
            mean[i] = ((double) p.numberOfOpenSites()) / (n * n);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(mean);

    }
    //failed method
    // private double variance() {
    //     return StdStats.var(mean);
    // }

    //sample confidence interval
    private double ci() {
        return (1.96 * stddev()) / Math.sqrt(trial);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(mean);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - ci();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + ci();
    }

    // test client (see below)
    public static void main(String[] args) {
        Stopwatch timer = new Stopwatch();
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats per = new PercolationStats(size, trials);
        System.out.println("mean() = " + per.mean());
        System.out.println("stddev() = " + per.stddev());
        System.out.println("confidenceLow() = " + per.confidenceLow());
        System.out.println("confidenceHigh() = " + per.confidenceHigh());
        System.out.println("elapsed time = " + timer.elapsedTime());


    }

}
