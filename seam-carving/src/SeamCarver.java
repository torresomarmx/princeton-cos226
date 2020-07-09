import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    private Picture picture;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    // current picture
    public Picture picture() {
        return this.picture;
    }

    // width of current picture
    public int width() {
        return this.picture.width();
    }

    // height of current picture
    public int height() {
        return this.picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        // dual gradiant energy function
        int xLeft = x - 1 >= 0 ? x -1 : this.width() - 1;
        int xRight = x + 1 <= this.width() - 1 ? x + 1 : 0;
        Color xLeftColor = this.picture.get(xLeft, y);
        Color xRightColor = this.picture.get(xRight, y);
        double xGradientSquared = Math.pow(xRightColor.getRed() - xLeftColor.getRed(), 2) +
                Math.pow(xRightColor.getGreen() - xLeftColor.getGreen(), 2) +
                Math.pow(xRightColor.getBlue() - xLeftColor.getBlue(), 2);

        int yTop = y - 1 >= 0 ? y - 1 : this.height() - 1;
        int yBottom = y + 1 <= this.height() - 1 ? y + 1 : 0;
        Color yTopColor = this.picture.get(x, yTop);
        Color yBottomColor = this.picture.get(x, yBottom);
        double yGradientSquared = Math.pow(yBottomColor.getRed() - yTopColor.getRed(), 2) +
                Math.pow(yBottomColor.getGreen() - yTopColor.getGreen(), 2) +
                Math.pow(yBottomColor.getBlue() - yBottomColor.getBlue(), 2);

        return Math.sqrt(xGradientSquared + yGradientSquared);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam()

    // sequence of indices for vertical seam
    public int[] findVerticalSeam()

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam)

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam)

    //  unit testing (required)
    public static void main(String[] args)

}