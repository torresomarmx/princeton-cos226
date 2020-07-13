import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.util.Arrays;
import java.util.Stack;

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
//    public int[] findHorizontalSeam()

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] edgeTo = new int[this.width()*this.height()];
        double[] distanceTo = new double[this.width()*this.height()];

        for (int i = 0; i < distanceTo.length; i++) {
            // initialize top-most row
            if (i < this.width()) {
                distanceTo[i] = this.energy(i, 0);
                edgeTo[i] = -1;
            }
            else distanceTo[i] = Double.POSITIVE_INFINITY;
        }

        // topological sort
        boolean[] marked = new boolean[this.width()*this.height()]; // mark visited vertex ids
        Stack<Integer> vertexIdsInStack = new Stack<>();
        Stack<Integer> reversedPostOrder = new Stack<>();
        for (int i = 0; i < this.width(); i++) {
            vertexIdsInStack.add(i);
            marked[i] = true;
            while (vertexIdsInStack.size() > 0) {
                int initialStackSize = vertexIdsInStack.size();
                int currentVertexId = vertexIdsInStack.peek();
                int[] currentVertexIndices = this.getIndicesForVertexId(currentVertexId);

                for (int adjacentId : this.getBottomAdjacentVertexIds(currentVertexIndices)) {
                    if (adjacentId != -1 && !marked[adjacentId]) {
                        vertexIdsInStack.add(adjacentId);
                        marked[adjacentId] = true;
                    }
                }

                // if all bottom neighbors are marked
                if (vertexIdsInStack.size() == initialStackSize) reversedPostOrder.add(vertexIdsInStack.pop());
            }
        }

        // build shortest paths trees
        while (reversedPostOrder.size() > 0) {
            int currentVertexId = reversedPostOrder.pop();
            int[] currentVertexIndices = this.getIndicesForVertexId(currentVertexId);

            // relax adjacent vertices (bottom 3)
            for (int adjacentId : this.getBottomAdjacentVertexIds(currentVertexIndices)) {
                if (adjacentId == -1) continue;
                int[] indicesForAdjacentVertex = this.getIndicesForVertexId(adjacentId);
                double energyForAdjacentVertex = this.energy(indicesForAdjacentVertex[0], indicesForAdjacentVertex[1]);
                if (distanceTo[adjacentId] > distanceTo[currentVertexId] + energyForAdjacentVertex) {
                    distanceTo[adjacentId] = distanceTo[currentVertexId] + energyForAdjacentVertex;
                    edgeTo[adjacentId] = currentVertexId;
                }
            }
        }

        // find bottom-most vertex with smallest distance to any of the top-most vertices
        int idWithSmallestDistanceTo = this.getIdForVertexIndices(new int[]{0, this.height() - 1}); // bottom left
        for (int i = 1; i < this.width(); i++) {
            int vertexId = this.getIdForVertexIndices(new int[]{i, this.height() -1});
            if (distanceTo[vertexId] < distanceTo[idWithSmallestDistanceTo]) idWithSmallestDistanceTo = vertexId;
        }

        Stack<Integer> vertexIdsInShortestPath = new Stack<>();
        vertexIdsInShortestPath.add(idWithSmallestDistanceTo);
        for (int vertexId = edgeTo[idWithSmallestDistanceTo]; vertexId != -1; vertexId = edgeTo[vertexId])
            vertexIdsInShortestPath.add(vertexId);

        int[] indicesToReturn = new int[vertexIdsInShortestPath.size()];
        for (int i = 0; i < indicesToReturn.length; i++)
            indicesToReturn[i] = this.getIndicesForVertexId(vertexIdsInShortestPath.pop())[0];

        return indicesToReturn;
    }

    private int[] getBottomAdjacentVertexIds(int[] vertexIndices) {
        // check bottom 3 neighbors: (x-1, y+1), (x, y+1), (x+1, y+1)
        int bottomLeft = this.getIdForVertexIndices(new int[]{vertexIndices[0] - 1, vertexIndices[1] + 1});
        int bottomCenter = this.getIdForVertexIndices(new int[]{vertexIndices[0], vertexIndices[1] + 1});
        int bottomRight = this.getIdForVertexIndices(new int[]{vertexIndices[0] + 1, vertexIndices[1] + 1});

        return new int[]{bottomLeft, bottomCenter, bottomRight};
    }

    private int[] getIndicesForVertexId(int vertexId) {
        return new int[]{vertexId % this.width(), Math.floorDiv(vertexId, this.width())};
    }

    private int getIdForVertexIndices(int[] vertexIndices) {
        if (vertexIndices[0] < 0 || vertexIndices[0] >= this.width() || vertexIndices[1] < 0 || vertexIndices[1] >= this.height())
            return -1;
        return vertexIndices[1] * this.width() + vertexIndices[0];
    }

    // remove horizontal seam from current picture
 //   public void removeHorizontalSeam(int[] seam)

 //   // remove vertical seam from current picture
 //   public void removeVerticalSeam(int[] seam) {
 //
 //   }

    //  unit testing (required)
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        SeamCarver carver = new SeamCarver(picture);
        System.out.println(carver.width());
        System.out.println(carver.height());

        System.out.println("----");

        for (int i = 0; i < carver.height(); i++) {
            for (int j = 0; j < carver.width(); j++) {
                System.out.println(carver.getIdForVertexIndices(new int[]{j,i}));
                System.out.println(Arrays.toString(carver.getIndicesForVertexId(carver.getIdForVertexIndices(new int[]{j,i}))));
            }
        }
    }
}