import edu.princeton.cs.algs4.Digraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class WordNet {

    private Digraph digraph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader(synsets))).useDelimiter(",");
        while (scanner.hasNextLine()) {
            int id = scanner.nextInt();
            String nouns = scanner.next();
            scanner.nextLine();
        }
    }

    // all WordNet nouns
//    public Iterable<String> nouns()
//
//    // is the word a WordNet noun?
//    public boolean isNoun(String word)
//
//    // a synset (second field of synsets.txt) that is a shortest common ancestor
//    // of noun1 and noun2 (defined below)
//    public String sca(String noun1, String noun2)
//
//    // distance between noun1 and noun2 (defined below)
//    public int distance(String noun1, String noun2)

    // unit testing (required)
    public static void main(String[] args) throws Exception {
        WordNet wn = new WordNet(args[0], "gf");
    }

}