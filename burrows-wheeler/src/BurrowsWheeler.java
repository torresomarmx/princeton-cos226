import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

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
//    public static void inverseTransform()

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) BurrowsWheeler.transform();
    }

}