package model.data_structures;

public interface StackI<T> {
    public boolean isEmpty();
    public int size();
    public void push(T item);
    public T pop();
    public T peek();
}
