import java.io.InputStreamReader;

public class Permutation {
    public static void main(String[] args) {
        InputStreamReader in = new InputStreamReader(System.in);
        StringBuilder currentString = new StringBuilder();
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        int charRead;
        try {
            while ((charRead = in.read()) != -1) {
                if (Character.isWhitespace((char) charRead)) {
                     randomizedQueue.enqueue(currentString.toString());
                     currentString = new StringBuilder();
                } else {
                    currentString.append((char) charRead);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            System.out.println(randomizedQueue.dequeue());
        }
    }
}
