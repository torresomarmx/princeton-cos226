public class WeightedQuickUnionUnionFind {

    private int[] trees;

    private int[] treeSize;

    public WeightedQuickUnionUnionFind(int numberOfTrees) {
        this.trees = new int[numberOfTrees];
        this.treeSize = new int[numberOfTrees];
        for (int i = 0; i < numberOfTrees; i++) {
            this.trees[i] = i;
            this.treeSize[i] = 1;
        }
    }

    public int getNumberOfTrees() {
        return this.trees.length;
    }

    public void union(int treeId1, int treeId2) {
        int rootForTree1 = this.find(treeId1);
        int rootForTree2 = this.find(treeId2);
        if (rootForTree1 != rootForTree2) {
            if (this.treeSize[rootForTree1] >= this.treeSize[rootForTree2]) {
                this.trees[rootForTree2] = rootForTree1;
                this.treeSize[rootForTree1] += this.treeSize[rootForTree2];
            } else {
                this.trees[rootForTree1] = rootForTree2;
                this.treeSize[rootForTree2] += this.treeSize[rootForTree1];
            }
        }
    }

    public int find(int treeId) {
        // a root will be pointing to itself in this.trees
        int currentId = treeId;

        while (this.trees[currentId] != currentId) {
            currentId = this.trees[currentId];
        }

        int root = currentId;
        // path compression
        currentId = treeId;
        while (this.trees[currentId] != root) {
            int nextId = this.trees[currentId];
            this.trees[currentId] = root;
            currentId = nextId;
        }

        return root;
    }
}
