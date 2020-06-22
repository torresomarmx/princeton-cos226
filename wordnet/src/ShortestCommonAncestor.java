import edu.princeton.cs.algs4.Digraph;

import java.util.HashMap;
import java.util.HashSet;

public class ShortestCommonAncestor {

    private Digraph digraph;
    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        this.digraph = G;
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        return this.getShortestCommonAncestor(v,w)[1];
    }

    private void getVerticesInPathToRoot(int v, int currentPathLength, HashMap<Integer, Integer> vertexInPathToLengthOfPath) {
        for (Integer adjacentVertex : this.digraph.adj(v)) {
            if (vertexInPathToLengthOfPath.containsKey(adjacentVertex))
                vertexInPathToLengthOfPath.put(adjacentVertex, Math.min(currentPathLength + 1, vertexInPathToLengthOfPath.get(adjacentVertex)));
            else
                vertexInPathToLengthOfPath.put(adjacentVertex, currentPathLength + 1);
            this.getVerticesInPathToRoot(adjacentVertex, currentPathLength + 1, vertexInPathToLengthOfPath);
        }
    }

    private int[] getShortestCommonAncestor(int v, int w) {
        HashMap<Integer, Integer> vVertexInRootPathToMinPathLength = new HashMap<>();
        HashMap<Integer, Integer> wVertexInRootPathToMinPathLength = new HashMap<>();
        this.getVerticesInPathToRoot(v, 0, vVertexInRootPathToMinPathLength);
        this.getVerticesInPathToRoot(w, 0, wVertexInRootPathToMinPathLength);
        HashSet<Integer> sharedVertices = new HashSet<>();
        for (Integer vertex : vVertexInRootPathToMinPathLength.keySet())
            if (wVertexInRootPathToMinPathLength.containsKey(vertex)) sharedVertices.add(vertex);
        int shortestCommonAncestor = -1;
        int lengthOfShortestAncestralPath= Integer.MAX_VALUE;
        for (Integer sharedVertex : sharedVertices) {
            if (vVertexInRootPathToMinPathLength.get(sharedVertex) + wVertexInRootPathToMinPathLength.get(sharedVertex) < lengthOfShortestAncestralPath) {
                lengthOfShortestAncestralPath = vVertexInRootPathToMinPathLength.get(sharedVertex) + wVertexInRootPathToMinPathLength.get(sharedVertex);
                shortestCommonAncestor = sharedVertex;
            }
        }
        return new int[]{shortestCommonAncestor, lengthOfShortestAncestralPath};
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        return this.getShortestCommonAncestor(v,w)[0];
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB)

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB)

    // unit testing (required)
    public static void main(String[] args)

}