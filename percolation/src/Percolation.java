public class Percolation {
    private final int[][] sites;

    private final WeightedQuickUnionUnionFind dataStructure;

    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // +2 for top and bottom virtual sites
        this.dataStructure = new WeightedQuickUnionUnionFind((n*n)+2);
        this.sites = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.sites[i][j] = 0;
                // if on top row, connect to top virtual site
                if (i ==0)
                    this.dataStructure.union(0, j+1);
                // if on bottom row, connect to bottom virtual site
                if (i == n - 1)
                    this.dataStructure.union(this.dataStructure.getNumberOfTrees() - 1, this.getSiteId(i+1, j+1));
            }
        }
    }

    // row and col are indices between 1 and n, inclusive
    private int getSiteId(int row, int col) {
        return (this.sites.length * (row -1)) + col;
    }

    // opens the site (row, col) if it is not open already
    // row and col are numbers between 1 and n, inclusive
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.sites.length || col > this.sites.length)
            throw new IllegalArgumentException("row and/or col is out of bounds");
        if (this.isOpen(row, col))
            return;
        this.sites[row -1][col - 1] = 1;
        this.numberOfOpenSites += 1;
        // check if top site is open, if so then call union
        if (row - 1 > 0 && this.isOpen(row -1, col))
            this.dataStructure.union(this.getSiteId(row, col), this.getSiteId(row -1, col));

        // check if bottom site is open, if so then call union
        if (row + 1 <= this.sites.length && this.isOpen(row + 1, col)) {
            this.dataStructure.union(this.getSiteId(row, col), this.getSiteId(row + 1, col));
        }
        // check if right site is open, if so then call union
        if (col + 1 <= this.sites.length && this.isOpen(row, col + 1))
            this.dataStructure.union(this.getSiteId(row, col), this.getSiteId(row, col + 1));

        // check if left site is open, if so then call union
        if (col -1 > 0 && this.isOpen(row, col - 1))
            this.dataStructure.union(this.getSiteId(row, col), this.getSiteId(row, col - 1));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.sites.length || col > this.sites.length)
            throw new IllegalArgumentException("row and/or col is out of bounds");
        return this.sites[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.sites.length || col > this.sites.length)
            throw new IllegalArgumentException("row and/or col is out of bounds");
        return this.dataStructure.find(this.getSiteId(row, col)) == 0;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // check if top and bottom virtual sites are connected
        return this.dataStructure.find(0) == this.dataStructure.find(this.dataStructure.getNumberOfTrees() - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation test = new Percolation(3);
        test.open(2,1);
        test.open(1,1);
        test.open(3,1);
        System.out.println(test.percolates());
    }
}
