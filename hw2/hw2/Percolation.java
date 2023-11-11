package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    protected WeightedQuickUnionUF grid;
    protected WeightedQuickUnionUF bottomGrid;
    private int numberOfOpenSites;
    private int lengthOfGrid;
    private boolean openArray[];
    private boolean percolates = false;
    private int bottom;

    public Percolation(int N) {
        validate(N);
        grid = new WeightedQuickUnionUF(N * N);
        bottomGrid = new WeightedQuickUnionUF(N * N);
        numberOfOpenSites = 0;
        lengthOfGrid = N;
        bottom = (N - 1) * N;
        openArray = new boolean[N * N];

        for (int i = 0; i < N * N; ++i)
            openArray[i] = false;

        for (int i = 1; i < N; i++) {
            grid.union(0, i);
            bottomGrid.union(bottom, i + bottom);
        }
    }

    public void open(int row, int col) {
        validate(row, col);
        int index = xyTo1D(row, col);
        if(!isOpen(row, col))
            numberOfOpenSites++;
        openArray[index] = true;

        if (col > 0 && isOpen(row, col - 1)) {
            grid.union(index, xyTo1D(row, col - 1));
            bottomGrid.union(index, xyTo1D(row, col - 1));
        }
        if (col < lengthOfGrid - 1 && isOpen(row, col + 1)) {
            grid.union(index, xyTo1D(row, col + 1));
            bottomGrid.union(index, xyTo1D(row, col + 1));
        }
        if (row > 0 && isOpen(row - 1, col)) {
            grid.union(index, xyTo1D(row - 1, col));
            bottomGrid.union(index, xyTo1D(row - 1, col));
        }
        if (row < lengthOfGrid - 1 && isOpen(row + 1, col)) {
            grid.union(index, xyTo1D(row + 1, col));
            bottomGrid.union(index, xyTo1D(row + 1, col));;
        }

        if (!percolates && grid.connected(0, index) && bottomGrid.connected(bottom, index))
            percolates =true;
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        int index = xyTo1D(row, col);
        return openArray[index];
    }

    public boolean isFull (int row, int col) {
        int index = xyTo1D(row, col);
        if (isOpen(row, col) && grid.connected(index, 0))
            return true;
        return false;
    }

    public boolean percolates() {
        return percolates;
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    protected int xyTo1D(int row, int col) {
        return row * lengthOfGrid + col;
    }

    private void validate(int N) {
        if (N <= 0) throw new IllegalArgumentException("N must be larger than 0");
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= lengthOfGrid || col < 0 || col >= lengthOfGrid)
            throw new IndexOutOfBoundsException("Index out of bound!");
    }

}
