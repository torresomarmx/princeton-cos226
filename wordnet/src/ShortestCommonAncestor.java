import edu.princeton.cs.algs4.Digraph;

import java.util.Collections;
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
        return this.getShortestCommonAncestor(Collections.singletonList(v), Collections.singletonList(w))[1];
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

    private int[] getShortestCommonAncestor(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        HashMap<Integer, Integer> subsetAVertexInRootPathToMinPathLength = new HashMap<>();
        HashMap<Integer, Integer> subsetBVertexInRootPathToMinPathLength = new HashMap<>();
        for (Integer subsetAVertex : subsetA) this.getVerticesInPathToRoot(subsetAVertex, 0, subsetAVertexInRootPathToMinPathLength);
        for (Integer subsetBVertex : subsetB) this.getVerticesInPathToRoot(subsetBVertex, 0, subsetBVertexInRootPathToMinPathLength);
        HashSet<Integer> sharedVertices = new HashSet<>();
        for (Integer vertex: subsetAVertexInRootPathToMinPathLength.keySet())
            if (subsetBVertexInRootPathToMinPathLength.containsKey(vertex)) sharedVertices.add(vertex);
        int shortestCommonAncestor = -1;
        int lengthOfShortestAncestralPath= Integer.MAX_VALUE;
        for (Integer sharedVertex : sharedVertices) {
            if (subsetAVertexInRootPathToMinPathLength.get(sharedVertex) + subsetBVertexInRootPathToMinPathLength.get(sharedVertex) < lengthOfShortestAncestralPath) {
                lengthOfShortestAncestralPath = subsetAVertexInRootPathToMinPathLength.get(sharedVertex) + subsetBVertexInRootPathToMinPathLength.get(sharedVertex);
                shortestCommonAncestor = sharedVertex;
            }
        }
        return new int[]{shortestCommonAncestor, lengthOfShortestAncestralPath};
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        return this.getShortestCommonAncestor(Collections.singletonList(v), Collections.singletonList(w))[0];
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        return this.getShortestCommonAncestor(subsetA, subsetB)[1];
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        return this.getShortestCommonAncestor(subsetA, subsetB)[0];
    }

    // unit testing (required)
    public static void main(String[] args)

}