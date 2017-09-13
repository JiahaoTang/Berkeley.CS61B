package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.ArrayList;

public class PercolationStats {
    private int T;
    private Percolation table;
    private int[] percolationNums;

    //Perform T independent experiment on an N-by-N grid.
    public PercolationStats(int N, int T){
        table = new Percolation(N);
        percolationNums = new int[T];
        for(int i = 0; i < T; i ++){
            while(true) {
                int x = StdRandom.uniform(N);
                int y = StdRandom.uniform(N);
                if (!table.isOpen(x, y)) {
                    table.open(x, y);
                    if (table.percolates()) {
                        percolationNums[i] = table.numberOfOpenSites();
                        break;
                    }
                }
            }
        }
    }

    //Sample mean of percolation threshold.
    public double mean(){
        return StdStats.mean(percolationNums);
    }

    //Sample standard deviation of percolation threshold.
    public double stddev(){
        return StdStats.stddev(percolationNums);
    }

    //Low endpoint of 95% confidence interval.
    public double confidenceLow(){
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    //High endpoint of 95% confidence interval.
    public double confidenceHigh(){
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

}                       
