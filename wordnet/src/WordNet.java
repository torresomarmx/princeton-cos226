import edu.princeton.cs.algs4.Digraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class WordNet {

    private Digraph digraph;

    private HashSet<String> synsets;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) throws FileNotFoundException, IOException  {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader(synsets))).useDelimiter(",");
        this.synsets = new HashSet<>();
        int numberOfSynsets = 0;
        while (scanner.hasNextLine()) {
            int id = scanner.nextInt();
            this.synsets.addAll(Arrays.asList(scanner.next().split(" ")));
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
        return new ArrayList<>(this.synsets);
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return this.synsets.contains(word);
    }

//    // a synset (second field of synsets.txt) that is a shortest common ancestor
//    // of noun1 and noun2 (defined below)
//    public String sca(String noun1, String noun2)
//
//    // distance between noun1 and noun2 (defined below)
//    public int distance(String noun1, String noun2)

    // unit testing (required)
    public static void main(String[] args) throws Exception {
        WordNet wn = new WordNet(args[0], args[1]);
    }

}