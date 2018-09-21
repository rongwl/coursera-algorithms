import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private WeightedQuickUnionUF fullness;
	private WeightedQuickUnionUF percolation;
	private boolean[][] opened;
	private int openSites;
	private int sideLen;
	private int rootTop;
	private int rootBottom;
	
	// create n-by-n grid, with all sites blocked
	public Percolation(int n)  { 
		if (n <= 0)
			throw new IllegalArgumentException("Constructor Percolation class error");	
		fullness = new WeightedQuickUnionUF(n*n);	
		percolation = new WeightedQuickUnionUF(n*n);
		for (int i = 1; i < n; i++) {
			fullness.union(0, i);
			percolation.union(0, i);
			percolation.union(n*n-1, n*n-1-i);
		}
		rootTop = fullness.find(0);
		rootBottom = percolation.find(n*n-1);
		opened = new boolean[n][n];
		sideLen = n;
	}

	// open site (row, col) if it is not open already
  	public void open(int row, int col) {
		if (isOpen(row, col))
			return;
		opened[row-1][col-1] = true;
		openSites++;
		union(row, col);
	}
	
	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		validate(row, col);
		return opened[row-1][col-1];
	}
	
	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		if (!isOpen(row, col))
			return false;
	
		return fullness.connected(rootTop, index(row, col));
	}
	
	// number of open sites
	public int numberOfOpenSites() {
		return openSites;
	}

	// does the system percolate?
	public boolean percolates() {
		if (1 == sideLen)
			return opened[0][0];
		return percolation.connected(rootTop, rootBottom);
	}
	

	private void union(int row, int col) {
		int upRow = row-1, downRow = row+1, leftCol = col-1, rightCol = col+1;

		if (upRow > 0 && opened[upRow-1][col-1]) {
			fullness.union(index(upRow, col), index(row, col));
			percolation.union(index(upRow, col), index(row, col));
		}
		if (downRow <= sideLen && opened[downRow-1][col-1]) {
			fullness.union(index(downRow, col), index(row, col));
			percolation.union(index(downRow, col), index(row, col));
		}
		if (leftCol > 0 && opened[row-1][leftCol-1]) { 
			fullness.union(index(row, leftCol), index(row, col));
			percolation.union(index(row, leftCol), index(row, col));	
		}
		if (rightCol <= sideLen && opened[row-1][rightCol-1]) { 
			fullness.union(index(row, rightCol), index(row, col));
			percolation.union(index(row, rightCol), index(row, col));
		}
	}

	private void validate(int row, int col) {
		if (row < 1 || row > sideLen || col < 1 || col > sideLen)
			throw new IllegalArgumentException("row " + row + " or col " + col + " is out of range");
	}

	private int index(int row, int col) {
		return (row-1)*sideLen+(col-1);
	}

	public static void main(String[] args) {
	}

}
