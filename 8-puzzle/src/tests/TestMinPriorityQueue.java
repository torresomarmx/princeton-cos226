import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestMinPriorityQueue {

    private MinPriorityQueue<Integer> pq;

    @Before
    public void setUp() {
        this.pq = new MinPriorityQueue<Integer>();
    }

    @After
    public void tearDown() {
        this.pq = null;
    }

    @Test
    public void testDeleteMin() {
        this.pq.insert(4);
        this.pq.insert(10);
        this.pq.insert(2);
        this.pq.insert(100);
        assertEquals(new Integer(2), this.pq.deleteMin());
    }

    @Test
    public void testGetMinWithoutDeleting() {
        this.pq.insert(4);
        this.pq.insert(10);
        this.pq.insert(2);
        this.pq.insert(12);
        int min = this.pq.getMin();
        assertEquals(4, this.pq.numberOfElements());
    }
}
