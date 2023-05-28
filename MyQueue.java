/**
 * Implement a MyQueue class which implements a queue using two stacks.
 *
 * (Problem definition seen in Cracking the coding interview)
 *
 * For this challenge, we'll assume the following Stack structure:
 *
 * public class Stack<T> {
 *
 *   private Node<T> top;
 *
 *   private static class Node<T> {
 *     private final T data;
 *     private Node<T> next;
 *
 *     public Node(T data) {
 *       this.data = data;
 *     }
 *   }
 *
 *   public void push(T data) {
 *     Node<T> node = new Node<>(data);
 *     node.next = top;
 *     top = node;
 *   }
 *
 *   public T peek() {
 *     if (top == null) {
 *       throw new EmptyStackException();
 *     }
 *
 *     return top.data;
 *   }
 *
 *   public boolean isEmpty() {
 *     return top == null;
 *   }
 *
 *   public T pop() {
 *     if (top == null) {
 *       throw new EmptyStackException();
 *     }
 *
 *     T data = top.data;
 *     top = top.next;
 *
 *     return data;
 *   }
 * }
 *
 * By definition, the Queue follows FIFO - First-In from one end, First-Out from the other. To get the right direction for this challenge,
 * let's think of the actions that we need to support:
 *
 * - enqueue(item) - Pushes an item to the last position of the queue
 * - dequeue() - Get the item which was longest in the queue
 * - peek() - Print the oldest item
 *
 * Since we cannot achieve this behavior with 1 Stack (using the given data structure) due to the LIFO nature
 * (The last in is the first out - just like in stack of dishes), one possible solution is to use the second stack as a temporary buffer
 * to hold all the elements during the process of enqueue() and get them back when dequeue() is called to have the oldest element on top.
 *
 * An optimization could be achieved by transferring the elements from <b>primary</b> to <b>buffer</b> only when the opposite action is called.
 * This way, if we call enqueue() 5 times on a Queue with initial size of N elements, we'll transfer all N elements first and add the new element.
 * The following 4 calls will transfer the previously added element only. When dequeue() is called, that's when they'll be added back to <b>primary</b>.
 *
 * The transferring process for example takes the elements A,B,C,D from <b>primary</b> and pushes them in a reverse order D,C,B,A
 * in <b>buffer</b> because of the Stack nature. When requested back, they will be placed in the exact same initial order.
 *
 * To estimate the Time Complexity let's group the operations as <b>ToPrimary</b> & <b>ToBuffer</b>
 * <b>ToPrimary</b> includes <b>dequeue()</b> & <b>peek()</b> as they both operate in primary
 * <b>ToBuffer</b> includes <b>enqueue()</b> as it pushes everything to buffer
 *
 * If <b>ToPrimary</b> OR <b>ToBuffer</b> operations are called consecutively - the Time Complexity is O(1). If they are called
 * in an alternating manner - O(n)
 */
public class MyQueue {
  private final Stack<Integer> primary = new Stack<>();
  private final Stack<Integer> buffer = new Stack<>();

  public void enqueue(Integer value) {
    if (value == null) {
      throw new IllegalArgumentException("Value cannot be null");
    }

    transferItems(primary, buffer);

    primary.push(value);
  }

  public Integer dequeue() {
    if (isEmpty()) {
      throw new RuntimeException("Queue is empty");
    }

    transferItems(buffer, primary);

    return primary.pop();
  }

  public Integer peek() {
    if (isEmpty()) {
      throw new RuntimeException("Queue is empty");
    }

    transferItems(buffer, primary);

    return primary.peek();
  }

  public boolean isEmpty() {
    // both stacks should be empty
    return primary.isEmpty() && buffer.isEmpty();
  }

  // Transfer all the items from a to b
  private void transferItems(Stack<Integer> a, Stack<Integer> b) {
    while (!a.isEmpty()) {
      b.push(a.pop());
    }
  }
}
