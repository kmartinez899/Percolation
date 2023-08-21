package algs15.perc;

//import stdlib.*;
import algs15.*;

// Uncomment the import statements above.

// You can test this using InteractivePercolationVisualizer and PercolationVisualizer
// All methods should make at most a constant number of calls to a UF data structure.
// You can use more than one UF data structure.
// You can assume that N>1
//
// Note that you can print out a UF structure using its toString method.
// This may be useful for debugging.
public class Percolation {
	int N;
	boolean[] open;
	WeightedUF full;
	WeightedUF perc;
	public Percolation(int N) {
		if (N < 2) throw new IllegalArgumentException();
		this.N = N;
		this.open = new boolean[N*N];
		this.full = new WeightedUF(N*N);
		this.perc = new WeightedUF(N*N);
		for (int i = 0; i < N; i++) {
			this.full.union(0, i);
			this.perc.union(0, i);
			this.perc.union(N*N-1, N*N-1-i);
		}
	}
	
	// returns position of (row i, column j)
	public int posOf(int i, int j) {
		return i*N + j;
	}
	
	// open site (row i, column j) if it is not already
	public void open(int i, int j) {
		int x = i*N+j;
		if (!open[x]) {
			open[x] = true;
			if(posOf(i-1,j) >= 0 && isOpen(i-1,j)) { // is up open
				full.union(x,  posOf(i-1,j));
				perc.union(x,  posOf(i-1,j));
			}
			if(posOf(i,j-1) >= posOf(i,0) && isOpen(i,j-1)) { // is left open
				full.union(x,  posOf(i,j-1));
				perc.union(x,  posOf(i,j-1));
			}
			if(posOf(i,j+1) <= posOf(i, N-1) && isOpen(i,j+1)) { // is right open
				full.union(x,  posOf(i,j+1));
				perc.union(x,  posOf(i,j+1));
			}
			if(posOf(i+1,j) <= N*N-1 && isOpen(i+1,j)) { // is down open
				full.union(x,  posOf(i+1,j));
				perc.union(x,  posOf(i+1,j));
			}
			
		}
	}
	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		return open[i*N+j];
	}
	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		return (isOpen(i, j) && full.connected(posOf(i,j), 0));
	}
	// does the system percolate?
	public boolean percolates() {
		return perc.connected(0, N*N-1);
	}
}