package model.data_structures;

public interface QueueI<T> {
    public boolean isEmpty();
    public int size();
    public void enqueue(T item);
    public T dequeue();
    public T peek();

}
