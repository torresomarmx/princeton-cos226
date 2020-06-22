import edu.princeton.cs.algs4.Digraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WordNet {

    private Digraph digraph;

    private HashSet<String> wordNetNouns;

    private HashMap<Integer, HashSet<String>> synsets;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) throws FileNotFoundException, IOException  {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader(synsets))).useDelimiter(",");
        this.wordNetNouns = new HashSet<>();
        this.synsets = new HashMap<>();
        int numberOfSynsets = 0;
        while (scanner.hasNextLine()) {
            int id = scanner.nextInt();
            List<String> synset = Arrays.asList(scanner.next().split(" "));
            this.synsets.put(id, new HashSet<>(synset));
            this.wordNetNouns.addAll(synset);
            numberOfSynsets += 1;
            scanner.nextLine();
        }
        this.digraph = new Digraph(numberOfSynsets);
        BufferedReader hypernymScanner = new BufferedReader(new FileReader(hypernyms));
        String lineRead;
        while ((lineRead = hypernymScanner.readLine()) != null) {
            String[] lineElements = lineRead.split(",");
            int synsetId = Integer.parseInt(lineElements[0]);
            for (String hypernym : Arrays.asList(lineElements).subList(1, lineElements.length))
                this.digraph.addEdge(synsetId, Integer.parseInt(hypernym));
        }
    }

    // all WordNet nouns
    public Iterable<String> nouns() {
        return new ArrayList<>(this.wordNetNouns);
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return this.wordNetNouns.contains(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        int[] synsetIdsForNouns = this.getSynsetIdsForNouns(noun1, noun2);
        int scaSynsetId = new ShortestCommonAncestor(this.digraph).ancestor(synsetIdsForNouns[0], synsetIdsForNouns[1]);
        return this.synsets.get(scaSynsetId).toString();
    }

    private int[] getSynsetIdsForNouns(String noun1, String noun2) {
        if (!this.isNoun(noun1) || !this.isNoun(noun2))
            throw new IllegalArgumentException("Not a wordnet noun");
        int synsetIdForNoun1 = -1;
        int synsetIdForNoun2 = -1;
        for (Map.Entry<Integer, HashSet<String>> synset : this.synsets.entrySet()) {
            if (synset.getValue().contains(noun1))
                synsetIdForNoun1 = synset.getKey();
            if (synset.getValue().contains(noun2))
                synsetIdForNoun2 = synset.getKey();
        }
        if (synsetIdForNoun1 == synsetIdForNoun2)
            throw new IllegalArgumentException("noun1 and noun2 are part of the same synset");
        return new int[]{synsetIdForNoun1, synsetIdForNoun2};
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        int[] synsetIdsForNouns = this.getSynsetIdsForNouns(noun1, noun2);
        return new ShortestCommonAncestor(this.digraph).length(synsetIdsForNouns[0], synsetIdsForNouns[1]);
    }

    // unit testing (required)
    public static void main(String[] args) throws Exception {
        WordNet wn = new WordNet(args[0], args[1]);
    }

}