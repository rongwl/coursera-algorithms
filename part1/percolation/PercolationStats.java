import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double mean;
	private double stddev;
	private double confidenceLo;
	private double confidenceHi;
	
	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException("Construct PercolationStats class error");
		
	 	double[] threshold = new double[trials];
		for (int i = 0; i < trials; i++) {
			threshold[i] = getThreshold(n);
		}

		mean = StdStats.mean(threshold);
		stddev = StdStats.stddev(threshold); 
		confidenceLo = mean - 1.96*stddev/Math.sqrt(trials);
		confidenceHi = mean + 1.96*stddev/Math.sqrt(trials);
	}
	
	// sample mean of percolation threshold
	public double mean() {
		return 	mean;
	}
	
	// sample standard deviation of percolation threshold
	public double stddev() {
		return stddev;
	}
	
	// low  endpoint of 95% confidence interval
	public double confidenceLo() {
		return confidenceLo;
	}
	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return confidenceHi;
	}
	

	private double getThreshold(int n) {
		int i, j;
		Percolation p = new Percolation(n);

		do {
			i = StdRandom.uniform(1, n+1);
			j = StdRandom.uniform(1, n+1);
			//System.out.println("open " + i + " " + j);
			p.open(i, j);
		} 
		while (!p.percolates());
		//System.out.println("open sites: " + p.numberOfOpenSites());	
		return (double)p.numberOfOpenSites()/(double)(n*n);
	}

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(n, t);
		System.out.println("mean                    = " + ps.mean());
		System.out.println("stddev                  = " + ps.stddev());
		System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
	}
}
