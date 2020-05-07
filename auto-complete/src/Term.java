import java.util.Comparator;

public class Term implements Comparable<Term> {

    public String query;

    private long weight;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null || weight < 0)
            throw new IllegalArgumentException();
        this.query = query.trim();
        this.weight = weight;
    }

    private static class ByReverseWeightOrder implements Comparator<Term> {
        public int compare(Term v, Term w) {
            if (v.weight < w.weight)
                return 1;
            else if (v.weight == w.weight)
                return 0;

            return -1;
        }
    }

    private static class ByPrefixOrder implements Comparator<Term> {
        private int prefixLength;

        public ByPrefixOrder(int prefixLength) {
            this.prefixLength = prefixLength;
        }

        public int compare(Term v, Term w) {
            return v.query.substring(0, Math.min(this.prefixLength, v.query.length()))
                    .compareTo(w.query.substring(0, Math.min(this.prefixLength, w.query.length())));
        }
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ByReverseWeightOrder();
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0)
            throw new IllegalArgumentException();
        return new ByPrefixOrder(r);
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return String.format("%d    %s", this.weight, this.query);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term query = new Term("welli", 0);
        Term term1 = new Term("wellington", 1);
        Term term2 = new Term("her", 1);
        Comparator<Term> comparator = Term.byPrefixOrder(5);
        System.out.println(comparator.compare(term1, query));
    }
}