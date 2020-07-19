import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;

public class MoveToFront {

    // apply move-to-front encoding, reading from stdin and writing to stdout
    public static void encode() {
        LinkedList<Character> alphabet = new LinkedList<>();
        for (int i = 0; i < 255; i++)
            alphabet.add((char) i);

        while (!BinaryStdIn.isEmpty()) {
            char inChar = BinaryStdIn.readChar();
            int index = alphabet.indexOf(inChar);
            BinaryStdOut.write(index, 8);
            alphabet.remove(index);
            alphabet.addFirst(inChar);
        }
        BinaryStdOut.close();
    }
//    // apply move-to-front decoding, reading from stdin and writing to stdout
    public static void decode() {
        LinkedList<Character> alphabet = new LinkedList<>();
        for (int i = 0; i < 255; i++)
            alphabet.add((char) i);

        while (!BinaryStdIn.isEmpty()) {
            int inInt = BinaryStdIn.readInt(8);
            char charAtIdx = alphabet.get(inInt);
            BinaryStdOut.write(charAtIdx);
            alphabet.remove(inInt);
            alphabet.addFirst(charAtIdx);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-"))
            MoveToFront.encode();
        else if (args[0].equals("+"))
            MoveToFront.decode();
    }

}