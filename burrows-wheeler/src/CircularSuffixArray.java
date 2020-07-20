public class CircularSuffixArray {

    private class CircularSuffix {
        private String referenceString;

        private int firstIdx;

        public CircularSuffix(String referenceString, int firstIdx) {
            this.referenceString = referenceString;
            this.firstIdx = firstIdx;
        }

        public String getReferenceString() {
            return this.referenceString;
        }

        public int getFirstIdxFromReferenceString() {
            return this.firstIdx;
        }
    }

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        CircularSuffix[] circularSuffixes = new CircularSuffix[s.length()];
        for (int i = 0; i < s.length(); i++)
            circularSuffixes[i] = new CircularSuffix(s, i);

        // LSD sort
        CircularSuffix[] aux = new CircularSuffix[s.length()];
        for (int i = s.length() - 1; i >= 0; i--) {
            int[] count = new int[256+1];
            for (int j = 0; j < circularSuffixes.length; j++) {
                CircularSuffix current = circularSuffixes[j];
                int idxInReferenceString = i + current.getFirstIdxFromReferenceString() % current.getReferenceString().length();
                char charInReferenceString = current.getReferenceString().charAt(idxInReferenceString);
                count[charInReferenceString+ 1] += 1;
            }

            for (int j = 0; j < count.length; j++)
                count[j+1] += count[j];

            for (int j = 0; j < circularSuffixes.length; j++) {
                CircularSuffix current = circularSuffixes[j];
                int idxInReferenceString = i + current.getFirstIdxFromReferenceString() % current.getReferenceString().length();
                char charInReferenceString = current.getReferenceString().charAt(idxInReferenceString);
                aux[count[charInReferenceString]++] = current;
            }

        }

    }

    // length of s
    public int length()

    // returns index of ith sorted suffix
    public int index(int i)

    // unit testing (required)
    public static void main(String[] args)

}