import java.util.Random;

public class PercolationStats {

    private double[] trialResults;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Invalid args");
        trialResults = new double[trials];
        double numberOfSites = n * n;
        Random random = new Random();
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(random.nextInt(n) + 1,  random.nextInt(n) + 1);
            }
            trialResults[i] = (percolation.numberOfOpenSites()) / numberOfSites;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sumOfTrailResults = 0.0;
        for (int i = 0; i < this.trialResults.length; i++) {
            sumOfTrailResults += this.trialResults[i];
        }
        return sumOfTrailResults / this.trialResults.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double mean = this.mean();
        double sumOfSquaredDifferences = 0;
        for (int i = 0; i < this.trialResults.length; i++) {
            sumOfSquaredDifferences += Math.pow(mean - this.trialResults[i], 2);
        }

        return Math.sqrt(sumOfSquaredDifferences / (this.trialResults.length - 1));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - ((1.96 * this.stddev()) / Math.sqrt(this.trialResults.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + ((1.96 * this.stddev()) / Math.sqrt(this.trialResults.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1])
        );
        System.out.println(String.format("mean = %s", stats.mean()));
        System.out.println(String.format("stddev = %s", stats.stddev()));
        System.out.println(String.format("95%% confidence interval = [%s, %s]",
                stats.confidenceLo(),
                stats.confidenceHi()));
    }
}
