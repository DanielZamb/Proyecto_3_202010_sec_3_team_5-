package model.data_structures;

public class Queue<T> implements QueueI {
    private Nodo<T> first; // link to least recently added node
    private Nodo<T> last;  // link to most recently added node
    private int N;      // number of items on the queue
    public boolean isEmpty() {  return first == null;  }  // Or: N == 0.
    public int size() {  return N;  }
    public void enqueue(Object item)   {
        T itemT = (T)item; // Add item to the end of the list.
        Nodo<T> oldlast = last;
        last = new Nodo<T>();
        last.setInfo(itemT);
        last.setSiguiente(null);
        if (isEmpty()) first = last;
        else oldlast.setSiguiente(last);
        N++;
    }
    public T dequeue()   {  // Remove item from the beginning of the list.
        T item = first.getInfo();
        first = first.getSiguiente();
        if (isEmpty()) last = null;
        N--;
        return item;
    }   // See page 155 for iterator() implementation.   // See page 150 for test client main().
    public T peek() {
        T item  = first.getInfo();
        return item;
    }
}
