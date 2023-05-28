import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyQueueTest {

  @Test
  public void test() {
    MyQueue queue = new MyQueue();

    Throwable t = Assertions.assertThrows(RuntimeException.class, queue::peek);
    Assertions.assertEquals("Queue is empty", t.getMessage());

    t = Assertions.assertThrows(RuntimeException.class, queue::dequeue);
    Assertions.assertEquals("Queue is empty", t.getMessage());

    Assertions.assertTrue(queue.isEmpty());

    queue.enqueue(10);

    Assertions.assertFalse(queue.isEmpty());

    Assertions.assertEquals(10, queue.peek());

    Assertions.assertEquals(10, queue.dequeue());

    Assertions.assertTrue(queue.isEmpty());

    queue.enqueue(1);
    queue.enqueue(2);
    queue.enqueue(3);
    queue.enqueue(4);
    queue.enqueue(5);

    Assertions.assertEquals(1, queue.dequeue());

    queue.enqueue(1);
    queue.enqueue(2);

    Assertions.assertEquals(2, queue.peek());

    Assertions.assertEquals(2, queue.dequeue());
    Assertions.assertEquals(3, queue.dequeue());
    Assertions.assertEquals(4, queue.dequeue());
    Assertions.assertEquals(5, queue.dequeue());
    Assertions.assertEquals(1, queue.dequeue());
    Assertions.assertEquals(2, queue.dequeue());
    Assertions.assertTrue(queue.isEmpty());
  }
}
