import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<T> implements Iterable<T> {

    private T[] list;

    private int numberOfElements;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.list = (T[]) new Object[1];
        this.numberOfElements = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.numberOfElements == 0;
    }


    // return the number of items on the randomized queue
    public int size() {
        return this.numberOfElements;
    }

    // add the item
    public void enqueue(T item) {
        if (this.numberOfElements == this.list.length)
            this.resize(this.list.length * 2);
        this.numberOfElements += 1;
        this.list[this.numberOfElements - 1] = item;
    }

    private void resize(int size) {
        T[] newList = (T[]) new Object[size];
        for (int i = 0; i < this.numberOfElements; i++)
            newList[i] = this.list[i];
        this.list = newList;
    }

    // remove and return a random item
    public T dequeue() {
        Random random = new Random();
        int randomIdx = random.nextInt(this.numberOfElements);
        T randomElement = this.list[randomIdx];
        this.list[randomIdx] = this.list[this.numberOfElements -1];
        this.list[this.numberOfElements -1] = null;
        this.numberOfElements -= 1;
        if (this.numberOfElements == (this.list.length / 4))
            this.resize(this.list.length / 2);
        return randomElement;
    }

    // return a random item (but do not remove it)
    public T sample() {
        Random random = new Random();
        int randomIdx = random.nextInt(this.numberOfElements);
        return this.list[randomIdx];
    }

    // return an independent iterator over items in random order
    public Iterator<T> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<T> {
        private int currentIdx = RandomizedQueue.this.numberOfElements - 1;

        public boolean hasNext() {
            return currentIdx >= 0;
        }

        public T next() {
            if (!this.hasNext())
                throw new NoSuchElementException();
            Random random = new Random();
            int randomIdx = random.nextInt(this.currentIdx + 1); // inclusive
            T nextElement = RandomizedQueue.this.list[randomIdx];
            // move nextElement to end of list of elements left to be seen
            RandomizedQueue.this.list[randomIdx] = RandomizedQueue.this.list[this.currentIdx];
            RandomizedQueue.this.list[this.currentIdx] = nextElement;

            currentIdx -= 1;
            return nextElement;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> test = new RandomizedQueue<>();
        test.enqueue("hello");
        test.enqueue("hey");
        test.enqueue("hi");
        test.enqueue("hola");

//        System.out.println(test.dequeue());
//        System.out.println(test.dequeue());
//        System.out.println(test.dequeue());
//        System.out.println(test.dequeue());

        for (String item : test) {
            System.out.println(item);
        }
        System.out.println("-------");
        for (String item : test) {
            System.out.println(item);
        }
    }
}