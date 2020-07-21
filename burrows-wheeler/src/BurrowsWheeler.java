import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.HashMap;
import java.util.LinkedList;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        StringBuilder stringBuilder = new StringBuilder();
        while (!BinaryStdIn.isEmpty())
            stringBuilder.append(BinaryStdIn.readChar());

        String originalString = stringBuilder.toString();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(originalString);
        char[] t = new char[circularSuffixArray.length()];
        int first = -1;
        for (int i = 0; i < circularSuffixArray.length(); i++) {
            int idxFromOriginalString = (circularSuffixArray.index(i) - 1) % originalString.length();
            if (idxFromOriginalString < 0) idxFromOriginalString += originalString.length();
            t[i] = originalString.charAt(idxFromOriginalString);

            if (circularSuffixArray.index(i) == 0) first = i;
        }

        BinaryStdOut.write(first);
        for (int i = 0; i < t.length; i++) BinaryStdOut.write(t[i], 8);

        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        StringBuilder t = new StringBuilder();
        while (!BinaryStdIn.isEmpty())
            t.append(BinaryStdIn.readChar(8));

        char[] tChars = t.toString().toCharArray();
        char[] tCharSorted = new char[tChars.length];
        int R = 256;
        int[] count = new int[R+1];
        for (int i = 0; i < tChars.length; i++)
            count[tChars[i] + 1] += 1;

        for (int i = 0; i < R; i++)
            count[i+1] += count[i];

        for (int i = 0; i < tChars.length; i++)
            tCharSorted[count[tChars[i]]++] = tChars[i];

        HashMap<Character, LinkedList<Integer>> charToTIndices = new HashMap<>();
        for (int i = 0; i < tChars.length; i++)
            charToTIndices.getOrDefault(tChars[i], new LinkedList<>()).add(i);

        int[] next = new int[tChars.length];
        for (int i = 0; i < tCharSorted.length; i++)
            next[i] = charToTIndices.get(tCharSorted[i]).removeFirst();

        int current = first;
        for (int i = 0; i < next.length; i++){
            BinaryStdOut.write(tCharSorted[current]);
            current = next[current];
        }
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) BurrowsWheeler.transform();
        else if (args[0].equals("+")) BurrowsWheeler.inverseTransform();
    }

}