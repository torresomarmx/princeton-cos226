import java.util.Arrays;
import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) throw new IllegalArgumentException();
        int leftIdx = 0;
        int rightIdx = a.length - 1;
        while (leftIdx <= rightIdx) {
            int midIdx = leftIdx + ((rightIdx - leftIdx) / 2);
            if (comparator.compare(a[midIdx], key) == 0) {
                if (midIdx - 1 < 0 || comparator.compare(a[midIdx - 1], key) != 0)
                    return midIdx;
                else
                    rightIdx = midIdx - 1;
            } else if (comparator.compare(a[midIdx], key) > 0) {
                rightIdx = midIdx - 1;
            } else {
                leftIdx = midIdx + 1;
            }
        }

        return -1;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) throw new IllegalArgumentException();
        int leftIdx = 0;
        int rightIdx = a.length - 1;
        while (leftIdx <= rightIdx) {
            int midIdx = leftIdx + ((rightIdx - leftIdx) / 2);
            if (comparator.compare(a[midIdx], key) == 0) {
                if (midIdx + 1 >= a.length || comparator.compare(a[midIdx+1], key) != 0)
                    return midIdx;
                else
                    leftIdx = midIdx + 1;
            } else if (comparator.compare(a[midIdx], key) > 0) {
                rightIdx = midIdx - 1;
            } else{
                leftIdx = midIdx + 1;
            }
        }

        return -1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term[] test = new Term[]{new Term("hey", 1),
        new Term("yiuu", 2), new Term("wellington", 2),
        new Term("zuii", 2), new Term("helm", 2),
        new Term("hii", 2)};
        Arrays.sort(test);
        System.out.println(Arrays.toString(test));
        int idx = firstIndexOf(test, new Term("welli", 2), Term.byPrefixOrder(5));
        int idx2 = lastIndexOf(test, new Term("welli", 2), Term.byPrefixOrder(5));
        System.out.println(idx);
        System.out.println(idx2);
    }
}
