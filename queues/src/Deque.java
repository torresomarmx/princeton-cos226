import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<T> implements Iterable<T> {

    private class Node<Tn> {
        Tn value;

        Node<Tn> next;

        Node<Tn> prev;
    }

    private int numberOfElements;

    private Node<T> head;

    private Node<T> tail;

    // construct an empty deque
    public Deque() {
        this.numberOfElements = 0;
        this.tail = new Node<>();
        this.head = new Node<>();
        this.tail.next = head;
        this.head.prev = tail;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.numberOfElements == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.numberOfElements;
    }

    // add the item to the front
    public void addFirst(T item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node<T> newNode = new Node<>();
        newNode.value = item;
        Node<T> currentFront = this.head.prev;
        currentFront.next = newNode;
        newNode.prev = currentFront;
        newNode.next = this.head;
        this.head.prev = newNode;
        this.numberOfElements += 1;
    }

    // add the item to the back
    public void addLast(T item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node<T> newNode = new Node<>();
        newNode.value = item;
        Node<T> currentBack = this.tail.next;
        currentBack.prev = newNode;
        newNode.next = currentBack;
        newNode.prev = this.tail;
        this.tail.next = newNode;
        this.numberOfElements += 1;
    }

    // remove and return the item from the front
    public T removeFirst() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        Node<T> itemFromFront = this.head.prev;
        Node<T> newFront = itemFromFront.prev;
        newFront.next = this.head;
        this.head.prev = newFront;
        this.numberOfElements -= 1;
        return itemFromFront.value;
    }

    // remove and return the item from the back
    public T removeLast() {
        if (this.isEmpty())
            throw new NoSuchElementException();
        Node<T> itemFromBack = this.tail.next;
        Node<T> newBack = itemFromBack.next;
        this.tail.next = newBack;
        newBack.prev = this.tail;
        this.numberOfElements -= 1;
        return itemFromBack.value;
    }

    private class DequeIterator implements Iterator<T> {
        private Node<T> current = Deque.this.tail.next;

        public boolean hasNext() {
            // checks if current is pointing at head sentinel node
            return this.current.next != null;
        }

        public T next() {
            if (!this.hasNext())
                throw new NoSuchElementException();
            T item = this.current.value;
            current = this.current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> test = new Deque<>();
        test.addFirst("Hello");
        test.addFirst("Hey");
        test.addFirst("Hola");
        test.addLast("Hi");
        test.removeFirst();
        test.removeLast();
        for (String item : test) {
            System.out.print(item);
        }
    }
}