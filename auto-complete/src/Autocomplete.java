import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Autocomplete {

    private Term[] terms;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        this.terms = terms;
        Arrays.sort(terms);
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix) {
        int firstIdx = BinarySearchDeluxe.firstIndexOf(this.terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        int lastIdx = BinarySearchDeluxe.lastIndexOf(this.terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        System.out.println(firstIdx);
        System.out.println(lastIdx);
        Term[] matchingTerms =  Arrays.copyOfRange(this.terms, firstIdx, lastIdx +1);
        Arrays.sort(matchingTerms, Term.byReverseWeightOrder());
        return matchingTerms;
    }

    // unit testing (required)
    public static void main(String[] args) throws Exception {
        int numberOfRows;
        Scanner fileReader = new Scanner(new BufferedInputStream(new FileInputStream(args[0])));
        numberOfRows = fileReader.nextInt();
        Term[] terms = new Term[numberOfRows];
        for (int i = 0; i < numberOfRows; i++) {
            long weight = fileReader.nextLong();
            String term = fileReader.nextLine();
            terms[i] = new Term(term, weight);
        }
        System.out.println(numberOfRows);
        for (int i = 0; i < terms.length; i++) {
            System.out.println(terms[i].query);
        }
        Autocomplete autocomplete = new Autocomplete(terms);
        InputStreamReader in = new InputStreamReader(System.in);
        StringBuilder query = new StringBuilder();
        int charRead;
        while ((charRead = in.read()) != -1) {
            if (Character.isWhitespace((char) charRead)) {
                System.out.println(query.toString());
                System.out.println(Arrays.toString(autocomplete.allMatches(query.toString())));
                query = new StringBuilder();
            } else
                query.append((char) charRead);
        }
    }
}